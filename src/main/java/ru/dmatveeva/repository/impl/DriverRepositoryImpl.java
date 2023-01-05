package ru.dmatveeva.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.repository.DriverRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DriverRepositoryImpl implements DriverRepository {

    @PersistenceContext
    EntityManager em;

    public List<Driver> getAll(){
        return em.createNamedQuery(Driver.ALL, Driver.class)
                .getResultList();
    }

    @Override
    public List<Driver> getByEnterprise(Enterprise enterprise) {
        List<Driver> drivers = em.createNamedQuery(Driver.BY_ENTERPRISE_ID, Driver.class)
                .setParameter(1, enterprise)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return drivers;
    }
}
