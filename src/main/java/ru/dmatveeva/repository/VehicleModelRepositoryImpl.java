package ru.dmatveeva.repository;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Repository;
import ru.dmatveeva.model.VehicleModel;
import ru.dmatveeva.service.VehicleModelService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.parser.Entity;
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
}
