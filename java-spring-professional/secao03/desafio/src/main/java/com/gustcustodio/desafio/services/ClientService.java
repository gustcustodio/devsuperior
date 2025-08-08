package com.gustcustodio.desafio.services;

import com.gustcustodio.desafio.dto.ClientDTO;
import com.gustcustodio.desafio.entities.Client;
import com.gustcustodio.desafio.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ClientDTO findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        Client client = optionalClient.get();
        return new ClientDTO(client);
    }

    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> clientPage = clientRepository.findAll(pageable);
        return clientPage.map(ClientDTO::new);
    }

    public ClientDTO insert(ClientDTO clientDTO) {
        Client client = new Client(clientDTO);
        client = clientRepository.save(client);
        return new ClientDTO(client);
    }

}
