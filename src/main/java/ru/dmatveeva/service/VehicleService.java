package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.repository.VehicleRepository;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAll() {
        return vehicleRepository.getAll();
    }

    public List<Vehicle> getAllPaginated(int offset, int pageSize) {
        return vehicleRepository.getAllPaginated(offset, pageSize);
    }

    public void delete(int id) {
        vehicleRepository.delete(id);
    }

    public void update(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle create(Vehicle vehicle, Integer modelId, Integer enterpriseId) {
        return vehicleRepository.save(vehicle, modelId, enterpriseId);
    }


    public Vehicle get(int id) {
        return vehicleRepository.get(id);

    }

    public List<Vehicle> getByEnterprise(Enterprise enterprise) {
        return vehicleRepository.getByEnterprise(enterprise);
    }

    public List<Vehicle> getByEnterprisePaginated(Enterprise enterprise, int offset, int pageSize) {
        return vehicleRepository.getByEnterprisePaginated(enterprise, offset, pageSize);
    }

    public Vehicle update(Vehicle newVehicle, Integer modelId, Integer enterpriseId) {
        Assert.notNull(newVehicle, "vehicle must not be null");
        return vehicleRepository.save(newVehicle, modelId, enterpriseId);
    }
}
