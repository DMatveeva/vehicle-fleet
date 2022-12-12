package ru.dmatveeva.web.rest;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.service.VehicleService;

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
    public List<Vehicle> getAll() {
        return vehicleService.getAll();
    }

}
