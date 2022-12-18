package ru.dmatveeva.repository;

import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Vehicle;

import java.util.List;

public interface VehicleRepository {
    List<Vehicle> getAll();

    boolean delete(int id);

    Vehicle save(Vehicle vehicle);

    Vehicle get(int id);

    List<Vehicle> getByEnterprise(Enterprise enterprise);
}
