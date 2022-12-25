package ru.dmatveeva.web.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ThymeleafRootController {

    private VehicleService vehicleService;

    public ThymeleafRootController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("username", "D");
        model.addAttribute("password", "p");
        return "login.html";
    }

    @GetMapping("/enterprises")
    public String getEnterprises(Model model){
        Manager manager = SecurityUtil.getAuthManager();
        List<Enterprise> enterprises = manager.getEnterprise();
        model.addAttribute("enterprises", enterprises);
        return "enterprises.html";
    }
}
