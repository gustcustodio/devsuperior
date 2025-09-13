package com.gustcustodio.dscommerce.services;

import com.gustcustodio.dscommerce.dto.UserDTO;
import com.gustcustodio.dscommerce.entities.Role;
import com.gustcustodio.dscommerce.entities.User;
import com.gustcustodio.dscommerce.projections.UserDetailsProjection;
import com.gustcustodio.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    protected User authenticated() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return repository.findByEmail(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found!");
        }

    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        return new UserDTO(authenticated());
    }

}
