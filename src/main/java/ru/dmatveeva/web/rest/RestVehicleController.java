package ru.dmatveeva.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.service.VehicleGeneratorService;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.to.VehicleGeneratorParamsTo;
import ru.dmatveeva.to.VehicleTo;
import ru.dmatveeva.util.SecurityUtil;
import ru.dmatveeva.util.VehicleUtils;

import java.net.URI;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping(value = RestVehicleController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestVehicleController {

    static final String REST_URL = "/rest/vehicles";

    private VehicleService vehicleService;
    private VehicleGeneratorService vehicleGeneratorService;


    public RestVehicleController(VehicleService vehicleService, VehicleGeneratorService vehicleGeneratorService) {
        this.vehicleService = vehicleService;
        this.vehicleGeneratorService = vehicleGeneratorService;
    }

    @GetMapping()
    public List<VehicleTo> getAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        Manager manager = SecurityUtil.getAuthManager();
        List<Enterprise> enterprises = manager.getEnterprise();
        for (Enterprise enterprise: enterprises) {
            vehicles.addAll(vehicleService.getByEnterprise(enterprise));
        }
        return VehicleUtils.getVehicleTos(vehicles);
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public List<VehicleTo> getAllPaginated(TimeZone timezone,
                                           @PathVariable int offset, @PathVariable int pageSize) {
        List<Vehicle> vehicles = new ArrayList<>();
        List<VehicleTo> vehicleTos = new ArrayList<>();
        Manager manager = SecurityUtil.getAuthManager();
        List<Enterprise> enterprises = manager.getEnterprise();
        for (Enterprise enterprise: enterprises) {
            List<Vehicle> vehiclesByEnterprise = vehicleService.getByEnterprisePaginated(enterprise, offset, pageSize);
            vehicles.addAll(vehicleService.getByEnterprisePaginated(enterprise, offset, pageSize));

            String enterpriseTimeZoneStr = enterprise.getLocalTimeZone();
            ZoneId enterpriseZoneId = ZoneId.of(enterpriseTimeZoneStr);
            TimeZone enterpriseTimeZone = TimeZone.getTimeZone(enterpriseZoneId);
            long enterpriseOffset = enterpriseTimeZone.getRawOffset();
            long clientOffset = timezone.getRawOffset() * 2L;
            long finalOffset = enterpriseOffset + clientOffset;

            vehicleTos.addAll(VehicleUtils.getVehicleTosWithLocalTime(vehicles, finalOffset));
            if (vehiclesByEnterprise.size() == pageSize) {
                break;
            }
        }

        return vehicleTos;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleTo> create(@RequestBody VehicleTo vehicleTo) {
        Manager manager = SecurityUtil.getAuthManager();

       // checkNew(newVehicle);
        VehicleUtils.checkEnterpriseIsConsistent(manager, vehicleTo.getEnterpriseId());
        Vehicle newVehicle = VehicleUtils.getVehicleFromTo(vehicleTo);
        Vehicle created = vehicleService.create(newVehicle, vehicleTo.getModelId(), vehicleTo.getEnterpriseId());
        VehicleTo createdTo = VehicleUtils.getVehicleTo(created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(createdTo);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleTo> update(@RequestBody VehicleTo vehicleTo) {
        Manager manager = SecurityUtil.getAuthManager();
        VehicleUtils.checkEnterpriseIsConsistent(manager, vehicleTo.getEnterpriseId());

        Vehicle newVehicle = VehicleUtils.getVehicleFromTo(vehicleTo);
        Vehicle created = vehicleService.update(newVehicle, vehicleTo.getModelId(), vehicleTo.getEnterpriseId());
        VehicleTo createdTo = VehicleUtils.getVehicleTo(created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(createdTo);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        Manager manager = SecurityUtil.getAuthManager();
        Vehicle vehicle = vehicleService.get(id);
        VehicleUtils.checkEnterpriseIsConsistent(manager, vehicle.getEnterprise().getId());
        vehicleService.delete(id);
    }


    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void generate(@RequestBody VehicleGeneratorParamsTo vehicleGeneratorParams) {
        vehicleGeneratorService.generateVehiclesForEnterprises(vehicleGeneratorParams.getEntepriseIds(),
                vehicleGeneratorParams.getNumVehicles());
    }

}
