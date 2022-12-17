package ru.dmatveeva.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.dmatveeva.AuthorizedManager;
import ru.dmatveeva.model.Manager;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static AuthorizedManager safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedManager) ? (AuthorizedManager) principal : null;
    }

    public static AuthorizedManager get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static Manager getAuthManager() {
        return get().getManager();
    }

}