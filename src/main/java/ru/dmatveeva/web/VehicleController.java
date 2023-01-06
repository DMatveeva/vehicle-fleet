package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleModel;
import ru.dmatveeva.service.EnterpriseService;
import ru.dmatveeva.service.VehicleModelService;
import ru.dmatveeva.service.VehicleService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private VehicleService vehicleService;
    private VehicleModelService vehicleModelService;

    private EnterpriseService enterpriseService;

    public VehicleController(VehicleService vehicleService, VehicleModelService vehicleModelService, EnterpriseService enterpriseService) {
        this.vehicleService = vehicleService;
        this.vehicleModelService = vehicleModelService;
        this.enterpriseService = enterpriseService;
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("vehicle", new Vehicle());
        List<VehicleModel> models = vehicleModelService.getAll();
        model.addAttribute("models", models);
        return "vehicleForm.html";
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
        List<VehicleModel> models = vehicleModelService.getAll();
        VehicleModel vehicleModel = vehicle.getVehicleModel();
        models.remove(vehicleModel);
        models.add(0, vehicleModel);
        model.addAttribute("models", models);
        return "vehicleForm.html";
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("vehicles", vehicleService.getAll());
        return "vehicles.html";
    }

    @PostMapping("/update_or_create")
    public String updateOrCreate(@RequestParam("id") String id,
                                 @RequestParam("vin") String vin,
                                 @RequestParam("vehicleModel") Integer vehicleModelId,
                                 @RequestParam("color") String color,
                                 @RequestParam("costUsd") BigDecimal costUsd,
                                 @RequestParam("mileage") Integer mileage,
                                 @RequestParam("productionYear") Integer productionYear
                                 ) {
        /*String vin = request.getParameter("vin");
        String model = request.getParameter("model");
        BigDecimal costUsd = BigDecimal.valueOf(Double.parseDouble(request.getParameter("costUsd")));
        String color = request.getParameter("color");
        int mileage = Integer.parseInt(request.getParameter("mileage"));
        int productionYear = Integer.parseInt(request.getParameter("productionYear"));
     //   Date purchaseDate = new Date()Date(request.getParameter("purchaseDate"));

        VehicleModel vehicleModel = vehicleModelService.getByName(model);
        String id = request.getParameter("id");
        if (id.isEmpty()) {
            Vehicle vehicle = new Vehicle(vehicleModel, vin, costUsd, color, mileage, productionYear);
            vehicleService.create(vehicle);
        } else {
            Vehicle vehicle = new Vehicle(Integer.parseInt(id), vehicleModel, vin, costUsd, color, mileage, productionYear);
            vehicleService.update(vehicle);
        }*/

        VehicleModel vehicleModel = vehicleModelService.get(vehicleModelId);


        return "redirect:/vehicles?enterpriseId={}";
    }

    @GetMapping
    public String getVehiclesByEnterprise(HttpServletRequest request, Model model){
        String paramId = Objects.requireNonNull(request.getParameter("enterpriseId"));
        Enterprise enterprise = enterpriseService.get(Integer.parseInt(paramId));
        List<Vehicle> vehicles = vehicleService.getByEnterprisePaginated(enterprise, 0, 20);
        model.addAttribute("vehicles", vehicles);

        String enterpriseTimeZoneStr = enterprise.getLocalTimeZone();
        ZoneId enterpriseZoneId = ZoneId.of(enterpriseTimeZoneStr);
        TimeZone enterpriseTimeZone = TimeZone.getTimeZone(enterpriseZoneId);
        long enterpriseOffset = enterpriseTimeZone.getRawOffset();

        Locale locale = request.getLocale();
        Calendar calendar = Calendar.getInstance(locale);
        TimeZone timeZone = calendar.getTimeZone();
        long clientOffset = timeZone.getRawOffset();
        /*ZoneId zoneId = timeZone.toZoneId();
        ZoneRules rules = zoneId.getRules();*/

        long finalOffset = enterpriseOffset + clientOffset;
        model.addAttribute("offset", finalOffset);

        for (Vehicle vehicle: vehicles) {
            long localTime = vehicle.getPurchaseDate().getTime() + finalOffset;
            Date localDate = new Date(localTime);
            vehicle.setPurchaseDate(localDate);
        }

        return "vehicles.html";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }


}
