package ru.dmatveeva.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.geojson.FeatureCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.service.CoordinateService;
import ru.dmatveeva.service.EnterpriseService;
import ru.dmatveeva.service.generate.TrackGeneratorService;
import ru.dmatveeva.service.TrackService;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.to.TripTo;
import ru.dmatveeva.to.VehicleCoordinateTo;
import ru.dmatveeva.to.VehicleCoordinateWithAddressTo;
import ru.dmatveeva.util.CoordinateUtils;
import ru.dmatveeva.util.TripUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = RestCoordinateController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestCoordinateController {

    public static Logger LOG = LoggerFactory.getLogger(RestCoordinateController.class);
    static final String REST_URL = "/rest";

    CoordinateService coordinateService;
    TrackGeneratorService trackGeneratorService;
    VehicleService vehicleService;

    EnterpriseService enterpriseService;

    public RestCoordinateController(CoordinateService coordinateService,
                                    VehicleService vehicleService,
                                    TrackService trackService,
                                    TrackGeneratorService trackGeneratorService,
                                    EnterpriseService enterpriseService) {
        this.coordinateService = coordinateService;
        this.vehicleService = vehicleService;
        this.trackService = trackService;
        this.trackGeneratorService = trackGeneratorService;
        this.enterpriseService = enterpriseService;
    }

    TrackService trackService;

    @GetMapping("/coordinates/json/vehicle/{vehicleId}")
    public List<VehicleCoordinateTo> getJsonByVehicleAndPeriod(@PathVariable int vehicleId,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {

        Vehicle vehicle = vehicleService.get(vehicleId);
        Enterprise enterprise = vehicle.getEnterprise();
        List<VehicleCoordinate> coordinates = getCoordinatesByVehicleAndPeriod(vehicleId, start, end);
        return CoordinateUtils.getCoordinatesTosWithTimezone(coordinates, enterprise.getLocalTimeZone());
    }

    @GetMapping("/coordinates/geojson/vehicle/{vehicleId}")
    public FeatureCollection getGeoJsonByVehicleAndPeriod(@PathVariable int vehicleId,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {

        Vehicle vehicle = vehicleService.get(vehicleId);
        Enterprise enterprise = vehicle.getEnterprise();
        List<VehicleCoordinate> coordinates = getCoordinatesByVehicleAndPeriod(vehicleId, start, end);

        return CoordinateUtils.getFeaturesFromCoordinatesWithTimeZone(coordinates, enterprise.getLocalTimeZone());
    }

    @GetMapping("/coordinates/tracks/vehicle/{vehicleId}")
    public List<VehicleCoordinateTo> getTracksCoordinatesByVehicleAndPeriod(@PathVariable int vehicleId,
                                                                            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startEnterpriseZoned,
                                                                            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endEnterpriseZoned) {

        Vehicle vehicle = vehicleService.get(vehicleId);
        Enterprise enterprise = vehicle.getEnterprise();

        ZonedDateTime startUTC = startEnterpriseZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = endEnterpriseZoned.withZoneSameInstant(ZoneId.of("UTC"));

        List<Track> track = trackService.getTracksByVehicleAndPeriod(vehicle, startUTC.toLocalDateTime(), endUTC.toLocalDateTime());
        List<VehicleCoordinate> coordinates = track.stream()
                .map(t -> coordinateService.getByTrack(t))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<VehicleCoordinateTo> coordinateTos = CoordinateUtils.getCoordinatesTosWithTimezone(coordinates, enterprise.getLocalTimeZone());
        return coordinateTos;
    }

    public List<VehicleCoordinate> getCoordinatesByVehicleAndPeriod(int vehicleId, ZonedDateTime start, ZonedDateTime end) {
        Vehicle vehicle = vehicleService.get(vehicleId);

        ZonedDateTime utcStart = start.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = end.withZoneSameInstant(ZoneId.of("UTC"));

        return coordinateService.getCoordinatesByVehicleAndPeriod(
                vehicle, utcStart.toLocalDateTime(), utcEnd.toLocalDateTime());
    }

    @GetMapping("/coordinates/trips/vehicle/{vehicleId}")
    public List<TripTo> getTripsByVehicleAndPeriod(@PathVariable int vehicleId,
                                                   @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startEnterpriseZoned,
                                                   @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endEnterpriseZoned) throws JsonProcessingException {

        Vehicle vehicle = vehicleService.get(vehicleId);
        Enterprise enterprise = vehicle.getEnterprise();

        ZonedDateTime startUTC = startEnterpriseZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = endEnterpriseZoned.withZoneSameInstant(ZoneId.of("UTC"));


        List<Track> tracks = trackService.getTracksByVehicleAndPeriod(vehicle, startUTC.toLocalDateTime(), endUTC.toLocalDateTime());
        List<TripTo> tripTos = tracks.stream()
                .map(track -> trackToTripTo(track, enterprise.getLocalTimeZone()))
                .collect(Collectors.toList());

        return tripTos;
    }

    @PostMapping("trip/generate/vehicle/{id}")
    public ResponseEntity<Boolean> generateTrack(
            @PathVariable int id,
            @RequestParam double[] start,
            @RequestParam double[] finish,
            @RequestParam int lengthKm,
            @RequestParam int maxSpeedKmH,
            @RequestParam int maxAccelerationMSS) {

        Vehicle vehicle = vehicleService.get(id);
        trackGeneratorService.generateTrackInRealTime(vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS);

        return ResponseEntity.ok().build();
    }

    @PostMapping("trip/generate/losangeles")
    public ResponseEntity<Boolean> generateTracksForLosAngeles(@RequestParam int numTracks) {
        Enterprise enterprise = enterpriseService.get(100002);
        List<Vehicle> vehicles = vehicleService.getByEnterprise(enterprise);
        trackGeneratorService.generateTracksForVehicles(vehicles, numTracks, "losangeles");
        return ResponseEntity.ok().build();
    }

    @PostMapping("trip/generate/losangeles/vehicle/{vehicleId}")
    public ResponseEntity<Boolean> generateTracksForLosAngeles(@PathVariable int vehicleId, @RequestParam int numTracks) {
        Vehicle vehicle = vehicleService.get(vehicleId);
        trackGeneratorService.generateTracksForVehicles(List.of(vehicle), numTracks, "losangeles");
        return ResponseEntity.ok().build();
    }

    @PostMapping("trip/generate/lasvegas/")
    public ResponseEntity<Boolean> generateTracksForLasVegas(@RequestParam int numTracks) {
        Enterprise enterprise = enterpriseService.get(100003);
        List<Vehicle> vehicles = vehicleService.getByEnterprise(enterprise);
        trackGeneratorService.generateTracksForVehicles(vehicles, numTracks, "lasvegas");
        return ResponseEntity.ok().build();
    }


    private TripTo trackToTripTo(Track track, String enterpriseTz) {
        ZonedDateTime startedZdt = getZdtFromLdtUtc(track.getStarted(), enterpriseTz);
        ZonedDateTime finishedZdt = getZdtFromLdtUtc(track.getFinished(), enterpriseTz);

        TripTo tripTo = new TripTo();
        tripTo.setStarted(startedZdt);
        tripTo.setFinished(finishedZdt);
        tripTo.setZone(enterpriseTz);
        tripTo.setVehicle_id(track.getVehicle().getId());

        List<VehicleCoordinate> coordinates = coordinateService.getByTrack(track);
        VehicleCoordinate start = coordinates.stream()
                .min(Comparator.comparing(VehicleCoordinate::getVisited))
                .orElse(null);

        VehicleCoordinate finish = coordinates.stream()
                .max(Comparator.comparing(VehicleCoordinate::getVisited))
                .orElse(null);

        String startAddress = TripUtils.getAddressFromCoordinate(start.getLat(), start.getLon());
        VehicleCoordinateWithAddressTo startTo = new VehicleCoordinateWithAddressTo(start.getLat(), start.getLon(), startAddress);

        String finishAddress = TripUtils.getAddressFromCoordinate(finish.getLat(), finish.getLon());
        VehicleCoordinateWithAddressTo finishTo = new VehicleCoordinateWithAddressTo(finish.getLat(), finish.getLon(), finishAddress);

        tripTo.setStart(startTo);
        tripTo.setFinish(finishTo);
        return tripTo;

    }

    private ZonedDateTime getZdtFromLdtUtc(LocalDateTime ldtUtc, String tz) {
        ZonedDateTime zdtUtc = ldtUtc.atZone(ZoneId.of("UTC"));
        return zdtUtc.withZoneSameInstant(ZoneId.of(tz));
    }
}
