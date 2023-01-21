package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.repository.TrackRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrackService {

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    TrackRepository trackRepository;

    public List<Track> getTracksByVehicleAndPeriod(Vehicle vehicle, LocalDateTime startUTC, LocalDateTime endUTC) {
        return trackRepository.getTracksByVehicleAndPeriod(vehicle, startUTC, endUTC);

    }

    public Track get(int id) {
        return trackRepository.get(id);
    }

    public List<Track> getTracksByVehicle(Vehicle vehicle) {
        return trackRepository.getTracksByVehicle(vehicle);
    }
}