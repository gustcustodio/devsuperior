package com.gustcustodio.workshop_mongo.repositories;

import com.gustcustodio.workshop_mongo.models.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
