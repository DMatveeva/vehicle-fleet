package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dmatveeva.service.VehicleModelService;

@Controller
public class VehicleModelController {

    private VehicleModelService vehicleModelService;

    public VehicleModelController(VehicleModelService vehicleModelService) {
        this.vehicleModelService = vehicleModelService;
    }

    @GetMapping("/models")
    private String getAll(Model model) {
        model.addAttribute("models", vehicleModelService.getAll());
        return "vehicleModels";
    }
}
