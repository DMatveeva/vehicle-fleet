package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/vehicles")
    public String getAll(Model model) {
        model.addAttribute("vehicles", vehicleService.getAll());
        return "vehicles";
    }

}
