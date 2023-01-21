package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.service.TrackService;
import ru.dmatveeva.service.VehicleService;

@Controller
public class RootController {
    @GetMapping("/login")
    public String login(Model model){
        return "login.html";
    }

}
