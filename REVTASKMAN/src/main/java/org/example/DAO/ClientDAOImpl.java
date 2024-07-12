package org.example.DAO;

import org.example.Connection.DBConnection;
import org.example.Models.Clients;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    static Connection conn;

    public ClientDAOImpl() {
        conn = DBConnection.getConnection();
    }
    @Override
    public void createClient(Clients client) {
        String sql = "INSERT INTO CLIENTS (client_name, client_info, client_email) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, client.getClient_name());
            pst.setString(2, client.getClient_info());
            pst.setString(3, client.getClient_email());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Clients> getAllClients() {
        List<Clients> clients = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTS";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                Clients client = new Clients();
                client.setClient_id(rs.getInt("client_id"));
                client.setClient_name(rs.getString("client_name"));
                client.setClient_info(rs.getString("client_info"));
                client.setClient_email(rs.getString("client_email"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}

