package com.gustcustodio.workshop_mongo.controllers;

import com.gustcustodio.workshop_mongo.models.dtos.PostDTO;
import com.gustcustodio.workshop_mongo.models.entities.Post;
import com.gustcustodio.workshop_mongo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable String id) {
        PostDTO dto = postService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/titlesearch")
    public ResponseEntity<List<PostDTO>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        List<PostDTO> list = postService.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }

}
