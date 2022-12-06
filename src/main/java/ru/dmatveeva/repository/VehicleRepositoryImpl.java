package ru.dmatveeva.repository;

import org.springframework.stereotype.Repository;
import ru.dmatveeva.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository{

    @PersistenceContext
    EntityManager em;

    public List<Vehicle> getAll(){

        return em.createNamedQuery(Vehicle.ALL, Vehicle.class)
                .getResultList();
    }
}
