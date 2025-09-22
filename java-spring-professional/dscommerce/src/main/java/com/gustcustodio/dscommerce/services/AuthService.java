package com.gustcustodio.dscommerce.services;

import com.gustcustodio.dscommerce.entities.User;
import com.gustcustodio.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(Long userId) {
        User currentUser = userService.authenticated();
        if (!currentUser.hasRole("ROLE_ADMIN") && !currentUser.getId().equals(userId)) {
            throw new ForbiddenException("Acesso negado");
        }
    }

}
