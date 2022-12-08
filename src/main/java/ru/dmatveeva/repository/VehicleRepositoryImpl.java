package ru.dmatveeva.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VehicleRepositoryImpl implements VehicleRepository{

    @PersistenceContext
    EntityManager em;

    public List<Vehicle> getAll(){
        return em.createNamedQuery(Vehicle.ALL, Vehicle.class)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Vehicle.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {
        if (vehicle.isNew()) {
            em.persist(vehicle);
            return vehicle;

        }
        return em.merge(vehicle);
    }
}
