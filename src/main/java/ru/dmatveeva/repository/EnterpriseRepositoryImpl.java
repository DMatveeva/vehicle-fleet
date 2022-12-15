package ru.dmatveeva.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class EnterpriseRepositoryImpl implements EnterpriseRepository{

    @PersistenceContext
    EntityManager em;

    public List<Enterprise> getAll(){
        return em.createNamedQuery(Enterprise.ALL, Enterprise.class)
                .getResultList();
    }
}
