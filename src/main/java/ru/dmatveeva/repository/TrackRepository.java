package ru.dmatveeva.repository;

import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface TrackRepository {

    Track get(int id);

    List<Track> getTracksByVehicleAndPeriod(Vehicle vehicle, LocalDateTime startUTC, LocalDateTime endUTC);

    List<Track> getTracksByVehicle(Vehicle vehicle);

    Track save(Track track);

}


