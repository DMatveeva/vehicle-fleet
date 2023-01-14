package ru.dmatveeva.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.geojson.FeatureCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.service.CoordinateService;
import ru.dmatveeva.service.TrackService;
import ru.dmatveeva.service.VehicleService;
import ru.dmatveeva.to.TripTo;
import ru.dmatveeva.to.VehicleCoordinateTo;
import ru.dmatveeva.to.VehicleCoordinateWithAddressTo;
import ru.dmatveeva.util.CoordinateUtils;
import ru.dmatveeva.util.TripUtils;
import ru.dmatveeva.util.VehicleUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = CoordinateRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CoordinateRestController {

    public static Logger LOG = LoggerFactory.getLogger(CoordinateRestController.class);
    static final String REST_URL = "/rest";

    CoordinateService coordinateService;

    VehicleService vehicleService;

    public CoordinateRestController(CoordinateService coordinateService, VehicleService vehicleService, TrackService trackService) {
        this.coordinateService = coordinateService;
        this.vehicleService = vehicleService;
        this.trackService = trackService;
    }

    TrackService trackService;

    private static final String TYPE_JSON = "json";
    private static final String TYPE_GEO_JSON = "geojson";


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

        LOG.debug("utc start {}", startUTC);
        LOG.debug("utc end {}", endUTC);

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

        LOG.debug("utc start {}", utcStart);
        LOG.debug("utc end {}", utcEnd);

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

        LOG.debug("utc start {}", startUTC);
        LOG.debug("utc end {}", endUTC);

        List<Track> tracks = trackService.getTracksByVehicleAndPeriod(vehicle, startUTC.toLocalDateTime(), endUTC.toLocalDateTime());
        List<TripTo> tripTos = tracks.stream()
                .map(track -> trackToTripTo(track, enterprise.getLocalTimeZone()))
                .collect(Collectors.toList());

        return tripTos;
    }

    private TripTo trackToTripTo (Track track, String enterpriseTz) {
        ZonedDateTime startedZdt = getZdtFromLdtUtc(track.getStarted(), enterpriseTz);
        ZonedDateTime finishedZdt = getZdtFromLdtUtc(track.getFinished(), enterpriseTz);

        TripTo tripTo = new TripTo();
        tripTo.setStarted(startedZdt);
        tripTo.setFinished(finishedZdt);
        tripTo.setZone(enterpriseTz);
        tripTo.setVehicle_id(track.getVehicle().getId());

        List<VehicleCoordinate> coordinates = coordinateService.getByTrack(track);
        VehicleCoordinate start = coordinates.stream()
                .min( Comparator.comparing(VehicleCoordinate::getVisited))
                .orElse(null);

        VehicleCoordinate finish = coordinates.stream()
                .max( Comparator.comparing(VehicleCoordinate::getVisited))
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
