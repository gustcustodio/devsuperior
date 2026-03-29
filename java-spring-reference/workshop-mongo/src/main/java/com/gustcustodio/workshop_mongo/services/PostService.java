package com.gustcustodio.workshop_mongo.services;

import com.gustcustodio.workshop_mongo.models.dtos.PostDTO;
import com.gustcustodio.workshop_mongo.models.entities.Post;
import com.gustcustodio.workshop_mongo.repositories.PostRepository;
import com.gustcustodio.workshop_mongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDTO findById(String id) {
        Post entity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
        return new PostDTO(entity);
    }

    public List<PostDTO> findByTitle(String text) {
        List<Post> list = postRepository.searchTitle(text);
        return list.stream().map(PostDTO::new).toList();
    }

    public List<PostDTO> fullSearch(String text, String start, String end) {
        Instant startMoment = convertMoment(start, Instant.ofEpochMilli(0L));
        Instant endMoment = convertMoment(end, Instant.now());
        List<Post> list = postRepository.fullSearch(text, startMoment, endMoment);
        return list.stream().map(PostDTO::new).toList();
    }

    private Instant convertMoment(String originalText, Instant alternative) {
        try {
            return Instant.parse(originalText);
        } catch (DateTimeParseException e) {
            return alternative;
        }
    }

}
