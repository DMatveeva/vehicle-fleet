package ru.dmatveeva.repository;

import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Enterprise;

import java.util.List;

public interface DriverRepository {

    List<Driver> getAll();

    List<Driver> getByEnterprise(Enterprise enterprise);

}
