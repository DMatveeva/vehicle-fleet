package ru.dmatveeva.util;

import ru.dmatveeva.model.AbstractBaseEntity;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.to.VehicleTo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleUtils {

    public static List<VehicleTo> getVehicleTos(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(VehicleUtils::getVehicleTo)
                .collect(Collectors.toList());
    }

    public static List<VehicleTo> getVehicleTosWithLocalTime(List<Vehicle> vehicles, long offset) {
        return vehicles.stream()
                .map(v -> VehicleUtils.getVehicleToWithLocalTime(v, offset))
                .collect(Collectors.toList());
    }

    public static VehicleTo getVehicleTo(Vehicle vehicle) {
        return new VehicleTo(vehicle.getId(),
                vehicle.getVehicleModel().getId(),
                vehicle.getVin(),
                vehicle.getCostUsd(),
                vehicle.getColor(),
                vehicle.getMileage(),
                vehicle.getProductionYear(),
                vehicle.getEnterprise().getId(),
                vehicle.getPurchaseDate());
    }

    public static VehicleTo getVehicleToWithLocalTime(Vehicle vehicle, long offset) {
        long localTimeOffset = vehicle.getPurchaseDate().getTime() + offset;
        Date localDate = new Date(localTimeOffset);
        return new VehicleTo(vehicle.getId(),
                vehicle.getVehicleModel().getId(),
                vehicle.getVin(),
                vehicle.getCostUsd(),
                vehicle.getColor(),
                vehicle.getMileage(),
                vehicle.getProductionYear(),
                vehicle.getEnterprise().getId(),
                localDate);
    }

    public static Vehicle getVehicleFromTo(VehicleTo vehicleTo) {
        return new Vehicle(
                vehicleTo.getId(),
                null,
                vehicleTo.getVin(),
                vehicleTo.getCostUsd(),
                vehicleTo.getColor(),
                vehicleTo.getMileage(),
                vehicleTo.getProductionYear(),
                vehicleTo.getPurchaseDate());
    }

    public static void checkEnterpriseIsConsistent(Manager manager, Integer enterprise_id) {
        boolean isEnterpriseConsistentWithManager = manager.getEnterprise().stream()
                .map(AbstractBaseEntity::getId)
                .anyMatch(id -> id.equals(enterprise_id));
        if (!isEnterpriseConsistentWithManager) {
            throw new IllegalArgumentException("Enterprise's not found for manager.");
        }
    }
}
