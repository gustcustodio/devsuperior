package com.gustcustodio.dscatalog.repositories;

import com.gustcustodio.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
