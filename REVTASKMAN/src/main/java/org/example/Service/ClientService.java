package org.example.Service;

import org.example.DAO.ClientDAO;
import org.example.DAO.ClientDAOImpl;
import org.example.Models.Clients;
import java.util.List;

public class ClientService {
    private ClientDAO clientDAO;

    public ClientService() {
        this.clientDAO = new ClientDAOImpl();
    }

    public void addClient(Clients client) {
        clientDAO.createClient(client);
    }

    public List<Clients> getAllClients() {
        return clientDAO.getAllClients();
    }
}
