package ru.dmatveeva.repository;

import org.springframework.stereotype.Repository;
import ru.dmatveeva.model.Vehicle;

import java.util.List;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository{

    public List<Vehicle> getAll(){
        return List.of();
    }
}
