package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.model.VehicleModel;
import ru.dmatveeva.service.VehicleModelService;
import ru.dmatveeva.service.VehicleService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Objects;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private VehicleService vehicleService;
    private VehicleModelService vehicleModelService;

    public VehicleController(VehicleService vehicleService, VehicleModelService vehicleModelService) {
        this.vehicleService = vehicleService;
        this.vehicleModelService = vehicleModelService;
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
        return "redirect:/vehicles/all";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model){
        Vehicle vehicle = vehicleService.get(getId(request));
        model.addAttribute("vehicle", vehicle);
        return "vehicleForm";
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("vehicles", vehicleService.getAll());
        return "vehicles";
    }

    @PostMapping("/update_or_create")
    public String updateOrCreate(HttpServletRequest request) {
        String vin = request.getParameter("vin");
        String model = request.getParameter("model");
        BigDecimal costUsd = BigDecimal.valueOf(Double.parseDouble(request.getParameter("costUsd")));
        String color = request.getParameter("color");
        int mileage = Integer.parseInt(request.getParameter("mileage"));
        int productionYear = Integer.parseInt(request.getParameter("productionYear"));

        VehicleModel vehicleModel = vehicleModelService.getByName(model);
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            Vehicle vehicle = new Vehicle(vehicleModel, vin, costUsd, color, mileage, productionYear);
            vehicleService.create(vehicle);
        } else {
            Vehicle vehicle = new Vehicle(Integer.parseInt(id), vehicleModel, vin, costUsd, color, mileage, productionYear);
            vehicleService.update(vehicle);
        }
        return "redirect:/vehicles/all";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
