package ru.dmatveeva.web.rest;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.to.DriverTo;
import ru.dmatveeva.to.VehicleTo;
import ru.dmatveeva.util.DriverUtils;
import ru.dmatveeva.util.SecurityUtil;
import ru.dmatveeva.util.VehicleUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = RestVehicleController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestVehicleController {

    static final String REST_URL = "/rest/vehicles";

    private VehicleService vehicleService;

    public RestVehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
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

}
