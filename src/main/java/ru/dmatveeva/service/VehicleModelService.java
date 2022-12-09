package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.VehicleModel;
import ru.dmatveeva.repository.VehicleModelRepository;

import java.util.List;

@Service
public class VehicleModelService {

    private VehicleModelRepository vehicleModelRepository;

    public VehicleModelService(VehicleModelRepository vehicleModelRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
    }

    public List<VehicleModel> getAll() {
        return vehicleModelRepository.getAll();
    }
    public VehicleModel getByName(String name) {
        return vehicleModelRepository.getByName(name);
    }


}
