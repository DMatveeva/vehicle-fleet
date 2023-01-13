package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.repository.CoordinateRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CoordinateService {

    CoordinateRepository coordinateRepository;

    public CoordinateService(CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    public List<VehicleCoordinate> getCoordinatesByVehicleAndPeriod(Vehicle vehicle, LocalDateTime startUTC, LocalDateTime endUTC) {
        return coordinateRepository.getCoordinatesByVehicleAndPeriod(vehicle, startUTC, endUTC);
    }

    public List<VehicleCoordinate> getByTrack(Track track) {
        return coordinateRepository.getByTrack(track);
    }

}
