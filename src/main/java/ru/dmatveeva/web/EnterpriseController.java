package ru.dmatveeva.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.util.SecurityUtil;

import java.util.List;

@Controller
public class EnterpriseController {

    @GetMapping("/enterprises")
    public String getEnterprises(Model model){
        Manager manager = SecurityUtil.getAuthManager();
        List<Enterprise> enterprises = manager.getEnterprise();
        model.addAttribute("enterprises", enterprises);
        return "enterprises.html";
    }
}
