package ru.dmatveeva.util;

import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.to.DriverTo;
import ru.dmatveeva.to.VehicleTo;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleUtils {

    public static List<VehicleTo> getVehicleTos(List<Vehicle> drivers) {
        return drivers.stream()
                .map(VehicleUtils::getVehicleTo)
                .collect(Collectors.toList());
    }

    private static VehicleTo getVehicleTo(Vehicle vehicle) {
        return new VehicleTo(vehicle.getId(),
                vehicle.getVehicleModel().getId(),
                vehicle.getVin(),
                vehicle.getCostUsd(),
                vehicle.getColor(),
                vehicle.getMileage(),
                vehicle.getProductionYear(),
                vehicle.getEnterprise().getId());
    }
}
