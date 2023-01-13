package ru.dmatveeva.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.repository.TrackRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TrackRepositoryImpl implements TrackRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Track> getTracksByVehicleAndPeriod(Vehicle vehicle, LocalDateTime startUTC, LocalDateTime endUTC) {
        return em.createNamedQuery(Track.BY_VEHICLE_AND_PERIOD, Track.class)
                .setParameter(1, vehicle)
                .setParameter(2, startUTC)
                .setParameter(3, endUTC)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }

    @Override
    public Track get(int id) {
        return em.find(Track.class, id);
    }
}
