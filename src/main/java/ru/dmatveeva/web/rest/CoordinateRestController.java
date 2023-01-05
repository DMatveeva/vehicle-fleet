package ru.dmatveeva.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.service.CoordinateService;
import ru.dmatveeva.to.VehicleCoordinateTo;
import ru.dmatveeva.util.CoordinateUtils;

import java.util.List;

@RestController
@RequestMapping(value = CoordinateRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CoordinateRestController {

    static final String REST_URL = "/rest";

    CoordinateService coordinateService;

    public CoordinateRestController(CoordinateService coordinateService) {
        this.coordinateService = coordinateService;
    }

    @GetMapping("/coordinates/track/{id}")
    public List<VehicleCoordinateTo> getByTrack(@PathVariable int id) {
        Track track = coordinateService.getTrack(id);
        List<VehicleCoordinate> coordinates = coordinateService.getCoordinatesByTrack(track);
        return CoordinateUtils.getCoordinatesTos(coordinates);
    }



}
