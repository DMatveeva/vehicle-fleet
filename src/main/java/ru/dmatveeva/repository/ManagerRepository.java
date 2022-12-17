package ru.dmatveeva.repository;

import ru.dmatveeva.model.Manager;

public interface ManagerRepository {

    Manager getByLogin(String login);

}
