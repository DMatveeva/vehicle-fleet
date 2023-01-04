package ru.dmatveeva.repository.impl;

import org.hibernate.jpa.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.repository.ManagerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ManagerRepositoryImpl implements ManagerRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Manager getByLogin(String login) {
        List<Manager> managers = em.createNamedQuery(Manager.BY_LOGIN, Manager.class)
                .setParameter(1, login)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return DataAccessUtils.singleResult(managers);
    }
}
