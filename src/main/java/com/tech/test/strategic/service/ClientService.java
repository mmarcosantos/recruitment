package com.tech.test.strategic.service;

import com.tech.test.strategic.dto.Client;
import com.tech.test.strategic.exception.NotFoundException;
import com.tech.test.strategic.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long id, Client client) {
        Client clientToUpdate = clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
        clientToUpdate.setName(client.getName());
        clientToUpdate.setLastName(client.getLastName());
        clientToUpdate.setEmail(client.getEmail());
        clientToUpdate.setPhone(client.getPhone());
        return clientRepository.save(clientToUpdate);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public  Optional<Client>  findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);


        return optionalClient;
    }
}
