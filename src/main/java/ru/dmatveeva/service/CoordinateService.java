package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.repository.CoordinateRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CoordinateService {

    CoordinateRepository coordinateRepository;

    public CoordinateService(CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

   /* public Track getTrack(int id) {
        return coordinateRepository.getTrack(id);

    }

    public List<VehicleCoordinate> getCoordinatesByTrack(Track t) {
        return coordinateRepository.getCoordinatesByTrack(t);
    }
*/
    public List<VehicleCoordinate> getCoordinatesByVehicleAndPeriod(Vehicle vehicle, LocalDateTime start, LocalDateTime end) {
        return coordinateRepository.getCoordinatesByVehicleAndPeriod(vehicle, start, end);
    }
}
