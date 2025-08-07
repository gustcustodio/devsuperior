package com.gustcustodio.desafio.repositories;

import com.gustcustodio.desafio.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
