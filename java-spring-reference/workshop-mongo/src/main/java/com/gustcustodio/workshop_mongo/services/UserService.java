package com.gustcustodio.workshop_mongo.services;

import com.gustcustodio.workshop_mongo.models.dtos.UserDTO;
import com.gustcustodio.workshop_mongo.models.entities.User;
import com.gustcustodio.workshop_mongo.repositories.UserRepository;
import com.gustcustodio.workshop_mongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream().map(UserDTO::new).toList();
    }

    public UserDTO findById(String id) {
        User entity = getEntityById(id);
        return new UserDTO(entity);
    }

    public UserDTO insert(UserDTO dto) {
        User entity = new User();
        copyDtoToEntity(entity, dto);
        entity = userRepository.insert(entity);
        return new UserDTO(entity);
    }

    public UserDTO update(String id, UserDTO dto) {
        User entity = getEntityById(id);
        copyDtoToEntity(entity, dto);
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    public void delete(String id) {
        getEntityById(id);
        userRepository.deleteById(id);
    }

    private User getEntityById(String id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    private void copyDtoToEntity(User entity, UserDTO dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
    }

}
