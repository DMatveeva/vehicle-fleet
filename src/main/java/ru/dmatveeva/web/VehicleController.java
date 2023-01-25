package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.model.VehicleReport;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleModel;
import ru.dmatveeva.service.EnterpriseService;
import ru.dmatveeva.service.TrackService;
import ru.dmatveeva.service.VehicleModelService;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private VehicleService vehicleService;
    private VehicleModelService vehicleModelService;

    private EnterpriseService enterpriseService;

    private TrackService trackService;

    public VehicleController(VehicleService vehicleService, VehicleModelService vehicleModelService, EnterpriseService enterpriseService, TrackService trackService) {
        this.vehicleService = vehicleService;
        this.vehicleModelService = vehicleModelService;
        this.enterpriseService = enterpriseService;
        this.trackService = trackService;
    }


    @GetMapping("/report/{id}")
    public String report(@PathVariable int id, Model model){
        model.addAttribute("report", new VehicleReport());
        Vehicle vehicle =  vehicleService.get(id);
        model.addAttribute("vehicleId", vehicle.getId());
        return "reportForm.html";
    }


    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("vehicle", new Vehicle());
        List<VehicleModel> models = vehicleModelService.getAll();
        model.addAttribute("models", models);
        return "vehicleForm.html";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable int id, Model model) {
        Vehicle vehicle =  vehicleService.get(id);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("tracks", trackService.getTracksByVehicle(vehicle));
        return "vehicle.html";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        vehicleService.delete(id);
        return "redirect:/vehicles/all";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id,  HttpServletRequest request, Model model){
        Vehicle vehicle = vehicleService.get(id);
        model.addAttribute("vehicle", vehicle);
        List<VehicleModel> models = vehicleModelService.getAll();
        VehicleModel vehicleModel = vehicle.getVehicleModel();
        models.remove(vehicleModel);
        models.add(0, vehicleModel);
        model.addAttribute("models", models);

        Manager manager = SecurityUtil.getAuthManager();
        List<Enterprise> enterprises = manager.getEnterprise();
        model.addAttribute("enterprises", enterprises);


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
                                 @RequestParam("productionYear") Integer productionYear,
                                 @RequestParam("purchaseDate") String purchaseDateStr,
                                 @RequestParam("enterprise") Integer enterpriseId
                                 ) {
        VehicleModel vehicleModel = vehicleModelService.get(vehicleModelId);
        System.out.println(purchaseDateStr);

        //  01/15/2020 11:00 PM

        LocalDateTime purchaseDate = getLdtFromString(purchaseDateStr);
        Enterprise enterprise = enterpriseService.get(enterpriseId);
        if (id.isEmpty()) {
            Vehicle vehicle = new Vehicle(vehicleModel,
                    vin, costUsd, color, mileage, productionYear,
                    purchaseDate, null, enterprise);
            vehicleService.create(vehicle);
        } else {
            Vehicle vehicle = new Vehicle(Integer.parseInt(id), vehicleModel,
                    vin, costUsd, color, mileage, productionYear,
                    purchaseDate,null, enterprise);
            vehicleService.update(vehicle);
        }
        return "redirect:/vehicles?enterpriseId=" + enterprise.getId();
    }

    LocalDateTime getLdtFromString(String ldtStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        return LocalDateTime.parse(ldtStr, formatter);
    }

    @GetMapping
    public String getVehiclesByEnterprise(HttpServletRequest request, Model model){
        String paramId = Objects.requireNonNull(request.getParameter("enterpriseId"));

        Enterprise enterprise = enterpriseService.get(Integer.parseInt(paramId));
        List<Vehicle> vehicles = vehicleService.getByEnterprisePaginated(enterprise, 0, 20);

        for (Vehicle vehicle: vehicles) {
            LocalDateTime oldDateTime = vehicle.getPurchaseDate();

            LocalDateTime newDateTime = oldDateTime.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of(enterprise.getLocalTimeZone()))
                    .toLocalDateTime();
            vehicle.setPurchaseDate(newDateTime);
        }
        model.addAttribute("vehicles", vehicles);


        return "vehicles.html";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }


}
