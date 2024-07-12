package org.example.DAO;

import org.example.Models.Clients;
import java.util.List;

public interface ClientDAO {
    void createClient(Clients client);
    List<Clients> getAllClients();
}
