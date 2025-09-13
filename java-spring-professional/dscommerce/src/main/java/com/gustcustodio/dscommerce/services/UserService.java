package com.gustcustodio.dscommerce.services;

import com.gustcustodio.dscommerce.entities.Role;
import com.gustcustodio.dscommerce.entities.User;
import com.gustcustodio.dscommerce.projections.UserDetailsProjection;
import com.gustcustodio.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> userDetailsProjectionList = repository.searchUserAndRolesByEmail(username);

        if (userDetailsProjectionList.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(userDetailsProjectionList.getFirst().getPassword());
        for (UserDetailsProjection projection : userDetailsProjectionList) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

}
