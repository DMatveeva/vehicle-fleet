package ru.dmatveeva.repository;

import ru.dmatveeva.model.vehicle.VehicleModel;

import java.util.List;

public interface VehicleModelRepository {
    List<VehicleModel> getAll();

    VehicleModel getByName(String name);
}
