package ru.dmatveeva.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleModel;
import ru.dmatveeva.repository.VehicleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VehicleRepositoryImpl implements VehicleRepository {

    @PersistenceContext
    EntityManager em;

    public List<Vehicle> getAll(){
        return em.createNamedQuery(Vehicle.ALL, Vehicle.class)
                .getResultList();
    }

    public List<Vehicle> getAllPaginated(int offset, int pageSize){
        Query query = em.createNamedQuery(Vehicle.ALL, Vehicle.class);
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
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

    @Override
    public Vehicle get(int id) {
        return em.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> getByEnterprise(Enterprise enterprise) {

        List<Vehicle> vehicles = em.createNamedQuery(Vehicle.BY_ENTERPRISE_ID, Vehicle.class)
                .setParameter(1, enterprise)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return vehicles;
    }

    @Override
    public List<Vehicle> getByEnterprisePaginated(Enterprise enterprise, int offset, int pageSize) {
        Query query = em.createNamedQuery(Vehicle.BY_ENTERPRISE_ID_SORTED, Vehicle.class)
                .setParameter(1, enterprise)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false);
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle, Integer modelId, Integer enterpriseId) {
        vehicle.setVehicleModel(em.getReference(VehicleModel.class, modelId));
        vehicle.setEnterprise(em.getReference(Enterprise.class, enterpriseId));
        if (vehicle.isNew()) {
            em.persist(vehicle);
            return vehicle;
        }
        return em.merge(vehicle);
    }
}
