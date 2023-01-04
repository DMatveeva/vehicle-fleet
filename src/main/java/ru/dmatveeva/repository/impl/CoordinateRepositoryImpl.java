package ru.dmatveeva.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Track;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.model.VehicleCoordinate;
import ru.dmatveeva.repository.CoordinateRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CoordinateRepositoryImpl implements CoordinateRepository {



    @PersistenceContext
    EntityManager em;


    @Override
    public List<VehicleCoordinate> getCoordinatesByTrack(Track t) {
        List<VehicleCoordinate> coordinates = em.createNamedQuery(VehicleCoordinate.BY_TRACK, VehicleCoordinate.class)
                .setParameter(1, t)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return coordinates;
    }

    @Override
    public Track getTrack(int id) {
        return em.find(Track.class, id);
    }

    @Override
    public VehicleCoordinate getCoordinate(int id) {
        return em.find(VehicleCoordinate.class, id);
    }
}
