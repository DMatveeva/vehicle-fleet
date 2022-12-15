package ru.dmatveeva.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.service.EnterpriseService;

import java.util.List;

@RestController
@RequestMapping(value = RestEnterpriseController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)

public class RestEnterpriseController {
    static final String REST_URL = "/rest/enterprises";

    private EnterpriseService enterpriseService;

    public RestEnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping()
    public List<Enterprise> getAll() {
        return enterpriseService.getAll();
    }
}
