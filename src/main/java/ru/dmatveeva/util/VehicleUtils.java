package ru.dmatveeva.util;

import ru.dmatveeva.model.AbstractBaseEntity;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Manager;
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

    public static VehicleTo getVehicleTo(Vehicle vehicle) {
        return new VehicleTo(vehicle.getId(),
                vehicle.getVehicleModel().getId(),
                vehicle.getVin(),
                vehicle.getCostUsd(),
                vehicle.getColor(),
                vehicle.getMileage(),
                vehicle.getProductionYear(),
                vehicle.getEnterprise().getId());
    }

    public static Vehicle getVehicleFromTo(VehicleTo vehicleTo) {
        return new Vehicle(
                vehicleTo.getId(),
                null,
                vehicleTo.getVin(),
                vehicleTo.getCostUsd(),
                vehicleTo.getColor(),
                vehicleTo.getMileage(),
                vehicleTo.getProductionYear());
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
