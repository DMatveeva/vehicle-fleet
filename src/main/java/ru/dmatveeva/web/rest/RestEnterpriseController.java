package ru.dmatveeva.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.service.EnterpriseService;
import ru.dmatveeva.util.SecurityUtil;

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
        Manager manager = SecurityUtil.getAuthManager();
        return manager.getEnterprise();
    }
}
