package ru.dmatveeva.repository;

import ru.dmatveeva.model.Vehicle;

import java.util.List;

public interface VehicleRepository {
    List<Vehicle> getAll();
}
