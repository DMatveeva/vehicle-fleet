package ru.dmatveeva.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;
import ru.dmatveeva.repository.CoordinateRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CoordinateRepositoryImpl implements CoordinateRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public VehicleCoordinate getCoordinate(int id) {
        return em.find(VehicleCoordinate.class, id);
    }

    @Override
    public List<VehicleCoordinate> getCoordinatesByVehicleAndPeriod(Vehicle vehicle, LocalDateTime startUTC, LocalDateTime endUTC) {
        return em.createNamedQuery(VehicleCoordinate.BY_VEHICLE_AND_PERIOD, VehicleCoordinate.class)
                .setParameter(1, vehicle)
                .setParameter(2, startUTC)
                .setParameter(3, endUTC)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }

    @Override
    public List<VehicleCoordinate> getByTrack(Track track) {
        return em.createNamedQuery(VehicleCoordinate.BY_TRACK, VehicleCoordinate.class)
                .setParameter(1, track)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }
}
