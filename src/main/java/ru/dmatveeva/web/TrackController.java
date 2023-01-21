package ru.dmatveeva.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.service.CoordinateService;
import ru.dmatveeva.service.TrackGeneratorService;
import ru.dmatveeva.service.TrackService;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.util.CoordinateUtils;

import java.util.List;

@Controller
@RequestMapping("/trips")
public class TrackController {
    CoordinateService coordinateService;

    TrackService trackService;

    TrackGeneratorService trackGeneratorService;

    VehicleService vehicleService;



    public TrackController(CoordinateService coordinateService,
                           TrackService trackService,
                           TrackGeneratorService trackGeneratorService,
                           VehicleService vehicleService) {
        this.coordinateService = coordinateService;
        this.trackService = trackService;
        this.trackGeneratorService = trackGeneratorService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable int id) {
        Track track = trackService.get(id);
        List<VehicleCoordinate>  coordinates = coordinateService.getByTrack(track);
        double[][] points = CoordinateUtils.getPoints(coordinates);
        double[] center = CoordinateUtils.getCenter(coordinates);

        model.addAttribute("points", points);
        model.addAttribute("center",center);
        return "trip.html";
    }

    @GetMapping("/{id}/route")
    public String getRoute(Model model, @PathVariable int id) {
        Track track = trackService.get(id);
        List<VehicleCoordinate>  coordinates = coordinateService.getByTrack(track);
        double[][] points = CoordinateUtils.getPoints(coordinates);
        double[] center = CoordinateUtils.getCenter(coordinates);

        model.addAttribute("points", points);
        model.addAttribute("center",center);
        return "route.html";
    }

}