package ru.dmatveeva.repository.impl;

import org.springframework.stereotype.Repository;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleModel;
import ru.dmatveeva.repository.VehicleModelRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VehicleModelRepositoryImpl implements VehicleModelRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<VehicleModel> getAll() {
        return em.createNamedQuery(VehicleModel.ALL, VehicleModel.class).getResultList();
    }

    @Override
    public VehicleModel getByName(String name) {
        return em.createQuery("SELECT vm FROM VehicleModel vm where vm.name = :nameValue", VehicleModel.class)
                .setParameter("nameValue", name).getSingleResult();
    }

    @Override
    public VehicleModel get(int id) {
        return em.find(VehicleModel.class, id);
    }

}
