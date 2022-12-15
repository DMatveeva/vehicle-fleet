package ru.dmatveeva.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.service.DriverService;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.to.DriverTo;
import ru.dmatveeva.util.DriverUtils;

import java.util.List;

@RestController
@RequestMapping(value = RestDriverController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestDriverController {

    static final String REST_URL = "/rest/drivers";

    private DriverService driverService;

    public RestDriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping()
    public List<DriverTo> getAll() {
        List<DriverTo> tos = DriverUtils.getDriverTos(driverService.getAll());
        return tos;
    }

}
