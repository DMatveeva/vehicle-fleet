package ru.dmatveeva.repository;

import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface CoordinateRepository {

/*    List<VehicleCoordinate> getCoordinatesByTrack(Track t);

    Track getTrack(int id);*/

    VehicleCoordinate getCoordinate(int id);

    List<VehicleCoordinate> getCoordinatesByVehicleAndPeriod(Vehicle vehicle, LocalDateTime start, LocalDateTime end);
}

