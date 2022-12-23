package ru.dmatveeva.repository;

import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Vehicle;

import java.util.List;

public interface EnterpriseRepository {

    List<Enterprise> getAll();

    Enterprise get(int id);

}
