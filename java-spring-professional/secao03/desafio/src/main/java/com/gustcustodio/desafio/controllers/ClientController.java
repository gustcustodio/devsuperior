package com.gustcustodio.desafio.controllers;

import com.gustcustodio.desafio.dto.ClientDTO;
import com.gustcustodio.desafio.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping(value = "/{id}")
    public ClientDTO findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @GetMapping
    public Page<ClientDTO> findAll(Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @PostMapping
    public ClientDTO insert(@RequestBody ClientDTO clientDTO) {
        return clientService.insert(clientDTO);
    }

}
