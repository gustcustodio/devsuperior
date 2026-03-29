package com.gustcustodio.workshop_mongo.services;

import com.gustcustodio.workshop_mongo.models.dtos.UserDTO;
import com.gustcustodio.workshop_mongo.models.entities.User;
import com.gustcustodio.workshop_mongo.repositories.UserRepository;
import com.gustcustodio.workshop_mongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream().map(UserDTO::new).toList();
    }

    public UserDTO findById(String id) {
        User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
        return new UserDTO(entity);
    }

}
