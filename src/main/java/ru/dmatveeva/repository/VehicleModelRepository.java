package ru.dmatveeva.repository;

import org.springframework.stereotype.Repository;
import ru.dmatveeva.model.VehicleModel;

import java.util.List;

public interface VehicleModelRepository {
    List<VehicleModel> getAll();
}
