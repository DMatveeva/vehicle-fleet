package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.service.VehicleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("vehicle", new Vehicle());
        return "vehicleForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        vehicleService.delete(id);
        return "redirect:/vehicles";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model){
        model.addAttribute("vehicle", getId(request));
        return "vehicleForm";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("vehicles", vehicleService.getAll());
        return "vehicles";
    }

    @PostMapping("/update_or_create")
    public String updateOrCreate(HttpServletRequest request) {
        Vehicle vehicle = new Vehicle();

        if (request.getParameter("id").isEmpty()) {
            vehicleService.create(vehicle);
        } else {
            vehicleService.update(vehicle);
        }
        return "redirect:/all";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
