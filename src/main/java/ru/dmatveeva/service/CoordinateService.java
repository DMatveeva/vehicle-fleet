package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.VehicleCoordinate;
import ru.dmatveeva.repository.CoordinateRepository;

import java.util.List;

@Service
public class CoordinateService {

    CoordinateRepository coordinateRepository;

    public CoordinateService(CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    public Track getTrack(int id) {
        return coordinateRepository.getTrack(id);

    }

    public List<VehicleCoordinate> getCoordinatesByTrack(Track t) {
        return coordinateRepository.getCoordinatesByTrack(t);
    }
}
