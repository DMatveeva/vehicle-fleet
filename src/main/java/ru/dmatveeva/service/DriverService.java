package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.repository.DriverRepository;
import ru.dmatveeva.repository.VehicleRepository;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAll() {
        return driverRepository.getAll();
    }

}
