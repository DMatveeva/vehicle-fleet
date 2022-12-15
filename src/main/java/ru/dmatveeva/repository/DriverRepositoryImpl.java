package ru.dmatveeva.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DriverRepositoryImpl implements DriverRepository{

    @PersistenceContext
    EntityManager em;

    public List<Driver> getAll(){
        return em.createNamedQuery(Driver.ALL, Driver.class)
                .getResultList();
    }
}
