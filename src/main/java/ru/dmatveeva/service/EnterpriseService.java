package ru.dmatveeva.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.repository.EnterpriseRepository;

import java.util.List;

@Service
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    public List<Enterprise> getAll() {
        return enterpriseRepository.getAll();
    }

}
