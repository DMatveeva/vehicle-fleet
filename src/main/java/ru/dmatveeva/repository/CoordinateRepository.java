package ru.dmatveeva.repository;

import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface CoordinateRepository {

    VehicleCoordinate getCoordinate(int id);

    List<VehicleCoordinate> getCoordinatesByVehicleAndPeriod(Vehicle vehicle, LocalDateTime startUTC, LocalDateTime endUTC);

    List<VehicleCoordinate> getByTrack(Track track);

    VehicleCoordinate save(VehicleCoordinate vehicleCoordinate);
}