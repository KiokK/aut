package by.kiok.motorshow.services;

import by.kiok.motorshow.models.Client;

import java.util.List;

public interface ClientService {

    Client createClient(Client newClient);

    void updateClient(Client clientUpdate, Long id);

    void deleteClient(Long id);

    boolean buyCar(Long clientId, Long carId);

    List<Client> findAllClients(int pageNumber, int pageSize);
}
