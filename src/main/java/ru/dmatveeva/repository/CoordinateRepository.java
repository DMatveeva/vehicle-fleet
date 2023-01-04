package ru.dmatveeva.repository;

import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.VehicleCoordinate;

import java.util.List;

public interface CoordinateRepository {

    List<VehicleCoordinate> getCoordinatesByTrack(Track t);

    Track getTrack(int id);

    VehicleCoordinate getCoordinate(int id);

}

