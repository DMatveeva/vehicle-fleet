package ru.dmatveeva.service.generate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.repository.CoordinateRepository;
import ru.dmatveeva.repository.TrackRepository;
import ru.dmatveeva.util.GeometryDecoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TrackGeneratorService {

    TrackRepository trackRepository;
    CoordinateRepository coordinateRepository;

    public TrackGeneratorService(TrackRepository trackRepository, CoordinateRepository coordinateRepository) {
        this.trackRepository = trackRepository;
        this.coordinateRepository = coordinateRepository;
    }

    String coordinatesToStr(double[] arr) {
        return "[" + arr[0] + "," +arr[1] + "]";
    }

    public void generateTrackInRealTime(Vehicle vehicle, double[] start, double[] finish,
                                        int lengthKm, int maxSpeedKmH, int maxAccelerationMSS) {
        generateTrack(vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS, 10000, LocalDateTime.now());
    }

    public void generateTrackInstantly(Vehicle vehicle, double[] start, double[] finish,
                                       int lengthKm, int maxSpeedKmH, int maxAccelerationMSS, LocalDateTime startDate) {
        generateTrack(vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS, 0, startDate);

    }
    public void generateTrack(Vehicle vehicle, double[] start, double[] finish,
                              int lengthKm, int maxSpeedKmH, int maxAccelerationMSS, int delay, LocalDateTime startDate) {

        Client client = ClientBuilder.newClient();
        Entity<String> payload = Entity.json(
                "{\"coordinates\":["+ coordinatesToStr(start) +"," + coordinatesToStr(finish)+"], \"maximum_speed\":"+ maxSpeedKmH +"}");
        Response response = client.target("https://api.openrouteservice.org/v2/directions/driving-car")
                .request()
                .header("Authorization", "5b3ce3597851110001cf6248a454b30fc2664363888082aa092ef980")
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .header("Content-Type", "application/json; charset=utf-8")
                .post(payload);

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());

        String responseBody = response.readEntity(String.class);
        System.out.println("body:" + responseBody);

        JSONObject json = new JSONObject(responseBody);
        String geometryEncoded;
        try {
            geometryEncoded = json.getJSONArray("routes").getJSONObject(0).getString("geometry");
        } catch (JSONException e) {
            return;
        }
        JSONArray geometryArray = GeometryDecoder.decodeGeometry(geometryEncoded, false);

        ZoneId zoneId = ZoneId.of(vehicle.getEnterprise().getLocalTimeZone());
        Track track = new Track();
        track.setVehicle(vehicle);
        track.setStarted(startDate);
        trackRepository.save(track);
        LocalDateTime coordinateDate = startDate;
        for (int i = 0; i < geometryArray.length(); i++) {
            JSONArray latLon = geometryArray.getJSONArray(i);
            createAndSaveCoordinate(vehicle, latLon, track, coordinateDate);

            long sec = coordinateDate.toEpochSecond(ZoneOffset.UTC) + 10L;
            coordinateDate = LocalDateTime.ofEpochSecond(sec, 0, ZoneOffset.UTC);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        track.setFinished(coordinateDate);
        trackRepository.save(track);
    }


    private void createAndSaveCoordinate(Vehicle vehicle, JSONArray latLon, Track track, LocalDateTime coordinateDate) {
        double lat = latLon.getDouble(0);
        double lon = latLon.getDouble(1);
        VehicleCoordinate vehicleCoordinate = new VehicleCoordinate();
        vehicleCoordinate.setVehicle(vehicle);
        vehicleCoordinate.setLat(lat);
        vehicleCoordinate.setLon(lon);
        vehicleCoordinate.setTrack(track);
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(lon, lat);
        Point point = geometryFactory.createPoint(coordinate);

        vehicleCoordinate.setPosition(point);
        vehicleCoordinate.setVisited(coordinateDate);
        coordinateRepository.save(vehicleCoordinate);
    }

    private static final LocalDateTime MIN_DATE_START = LocalDateTime.of(2018, 1, 1, 0,0);
    private static final LocalDateTime MAX_DATE_START = LocalDateTime.of(2021, 1, 1, 0,0);;




    // lon, lat
    public void generateTracksForVehicles(List<Vehicle> vehicles, int numTracks, String city) {
        final double minLat;
        final double minLon;
        final double maxLat;
        final double maxLon;
        if (city.equals("losangeles")) {
            minLat = 33.830504;
            minLon = -118.385967;
            maxLat = 34.007444;
            maxLon = -118.070118;
        } else if(city.equals("lasvegas")) {
            minLat = 36.147515;
            minLon = -115.247185;
            maxLat = 36.253734;
            maxLon = -115.113607;
        } else {
            throw new RuntimeException();
        }
        for (int j = 0; j < vehicles.size(); j++) {
            Vehicle vehicle = vehicles.get(j);
            for(int i = 0; i <= numTracks; i++) {
                double latStart = ThreadLocalRandom.current().nextDouble(minLat, maxLat);
                double lonStart = ThreadLocalRandom.current().nextDouble(minLon, maxLon);
                double[] start = {lonStart, latStart};
                double latFinish = ThreadLocalRandom.current().nextDouble(minLat, maxLat);
                double lonFinish = ThreadLocalRandom.current().nextDouble(minLon, maxLon);
                double[] finish = {lonFinish, latFinish};
                LocalDateTime startDate = generateDate(MIN_DATE_START, MAX_DATE_START);

                generateTrackInstantly(vehicle, start, finish, 0, 90, 0, startDate);
                System.out.println("vehicle " + j + ", track " + i );
            }
        }
    }

    public LocalDateTime generateDate(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        long minDay = startInclusive.toEpochSecond(ZoneOffset.UTC);
        long maxDay = endExclusive.toEpochSecond(ZoneOffset.UTC);
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.UTC);
    }
}
