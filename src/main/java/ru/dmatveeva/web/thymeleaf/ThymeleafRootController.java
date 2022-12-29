package ru.dmatveeva.web.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.model.VehicleModel;
import ru.dmatveeva.service.EnterpriseService;
import ru.dmatveeva.service.VehicleModelService;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class ThymeleafRootController {

    private VehicleService vehicleService;
    private EnterpriseService enterpriseService;

    private VehicleModelService vehicleModelService;


    public ThymeleafRootController(VehicleService vehicleService, EnterpriseService enterpriseService, VehicleModelService vehicleModelService) {
        this.vehicleService = vehicleService;
        this.enterpriseService = enterpriseService;
        this.vehicleModelService = vehicleModelService;
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login.html";
    }

    @GetMapping("/enterprises")
    public String getEnterprises(Model model){
        Manager manager = SecurityUtil.getAuthManager();
        List<Enterprise> enterprises = manager.getEnterprise();
        model.addAttribute("enterprises", enterprises);
        return "enterprises.html";
    }

    @GetMapping("/vehicles")
    public String getVehiclesByEnterprise(HttpServletRequest request, Model model){
        String paramId = Objects.requireNonNull(request.getParameter("enterpriseId"));
        Enterprise enterprise = enterpriseService.get(Integer.parseInt(paramId));
        List<Vehicle> vehicles = vehicleService.getByEnterprisePaginated(enterprise, 0, 20);
        model.addAttribute("vehicles", vehicles);
        return "vehicles.html";
    }

    @GetMapping("/vehicles/create")
    public String create(Model model){
        model.addAttribute("vehicle", new Vehicle());
        List<VehicleModel> models = vehicleModelService.getAll();
        model.addAttribute("models", models);
        return "vehicleForm.html";
    }



    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
