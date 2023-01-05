package ru.dmatveeva.util;


import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.to.VehicleCoordinateTo;

import java.util.List;
import java.util.stream.Collectors;

public class CoordinateUtils {

    public static List<VehicleCoordinateTo> getCoordinatesTos(List<VehicleCoordinate> vehicles) {
        return vehicles.stream()
                .map(CoordinateUtils::getCoordinateTo)
                .collect(Collectors.toList());
    }
    public static VehicleCoordinateTo getCoordinateTo(VehicleCoordinate vehicleCoordinate) {
        return new VehicleCoordinateTo(vehicleCoordinate.getId(),
                vehicleCoordinate.getTrack().getId(),
                vehicleCoordinate.getLat(),
                vehicleCoordinate.getLon(),
                vehicleCoordinate.getVisited());
    }
}
