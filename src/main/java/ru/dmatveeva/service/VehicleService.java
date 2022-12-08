package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.repository.VehicleRepositoryImpl;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepositoryImpl vehicleRepository;

    public VehicleService(VehicleRepositoryImpl vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAll() {
        return vehicleRepository.getAll();
    }

    public void delete(int id) {
        vehicleRepository.delete(id);
    }


}
