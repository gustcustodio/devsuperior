package com.gustcustodio.workshop_mongo.repositories;

import com.gustcustodio.workshop_mongo.models.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
}
