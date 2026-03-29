package com.gustcustodio.workshop_mongo.services;

import com.gustcustodio.workshop_mongo.models.dtos.PostDTO;
import com.gustcustodio.workshop_mongo.models.entities.Post;
import com.gustcustodio.workshop_mongo.repositories.PostRepository;
import com.gustcustodio.workshop_mongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDTO findById(String id) {
        Post entity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
        return new PostDTO(entity);
    }

}
