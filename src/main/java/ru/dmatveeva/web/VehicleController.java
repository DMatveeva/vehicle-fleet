package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.service.VehicleService;

import java.util.List;

@Controller
public class VehicleController {
    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public Vehicle create(Vehicle vehicle){
        return vehicle;
    }

    public List<Vehicle> getAll() {
        //log.info("getAll");
        return vehicleService.getAll();
    }
}
