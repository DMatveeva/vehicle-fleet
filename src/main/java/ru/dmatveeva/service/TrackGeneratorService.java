package ru.dmatveeva.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.json.JSONArray;
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

    public void generateTrack(Vehicle vehicle, double[] start, double[] finish,
                              int lengthKm, int maxSpeedKmH, int maxAccelerationMSS) {


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
        String geometryEncoded = json.getJSONArray("routes").getJSONObject(0).getString("geometry");
        JSONArray geometryArray = GeometryDecoder.decodeGeometry(geometryEncoded, false);

        ZoneId zoneId = ZoneId.of(vehicle.getEnterprise().getLocalTimeZone());
        Track track = new Track();
        track.setVehicle(vehicle);
        track.setStarted(LocalDateTime.now(zoneId));
        trackRepository.save(track);
        for (int i = 0; i < geometryArray.length(); i++) {
            JSONArray list = geometryArray.getJSONArray(i);
            Double lat = list.getDouble(0);
            Double lon = (double) list.getDouble(1);
            VehicleCoordinate vehicleCoordinate = new VehicleCoordinate();
            vehicleCoordinate.setVehicle(vehicle);
            vehicleCoordinate.setLat(lat);
            vehicleCoordinate.setLon(lon);
            vehicleCoordinate.setTrack(track);
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coordinate = new Coordinate(lon, lat);
            Point point = geometryFactory.createPoint(coordinate);
           /* try {
                if (!srsName.equals(EPSG_4326)) {
                    MathTransform transform = CRS.findMathTransform(getCrs(EPSG_4326), getCrs(srsName));
                    point = (Point) JTS.transform(point, transform);
                }*/

            vehicleCoordinate.setPosition(point);
            vehicleCoordinate.setVisited(LocalDateTime.now(zoneId));
            coordinateRepository.save(vehicleCoordinate);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
