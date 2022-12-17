package ru.dmatveeva.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmatveeva.AuthorizedManager;
import ru.dmatveeva.model.Manager;
import ru.dmatveeva.repository.ManagerRepository;

@Service("managerService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ManagerService implements UserDetailsService {

    private ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Manager manager = managerRepository.getByLogin(login);
        if (manager == null) {
            throw new UsernameNotFoundException("Manager " + login + " is not found");
        }
        return new AuthorizedManager(manager);
    }
}
