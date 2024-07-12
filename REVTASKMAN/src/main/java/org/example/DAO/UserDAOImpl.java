package org.example.DAO;

import org.example.Connection.DBConnection;
import org.example.Models.Clients;
import org.example.Models.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    static Connection conn;

    public UserDAOImpl() {
        conn = DBConnection.getConnection();
    }

    @Override
    public Users createUser(Users user) {
        String sql = "INSERT INTO users (name, password, email_id, role, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail_id());
            pst.setString(4, user.getRole().name());
            pst.setString(5, user.getStatus().name());
            pst.executeUpdate();
            return user;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public Users updateUser(Users user) {
        String sql = "UPDATE users SET name = ?, password = ?, email_id = ?, role = ?, status = ? WHERE user_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail_id());

            // Check if role is not null and set correctly
            if (user.getRole() != null) {
                pst.setString(4, user.getRole().name()); // Ensure role is set correctly
            } else {
                // Handle the case where role is null (if necessary)
                throw new IllegalArgumentException("Role cannot be null");
            }

            pst.setString(5, user.getStatus().name());
            pst.setInt(6, user.getUser_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Users getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                Users user = new Users();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail_id(resultSet.getString("email_id"));
                user.setRole(Users.Role.valueOf(resultSet.getString("role")));
                user.setStatus(Users.Status.valueOf(resultSet.getString("status")));
                return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Users> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<Users> usersList = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Users user = new Users();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail_id(resultSet.getString("email_id"));
                user.setRole(Users.Role.valueOf(resultSet.getString("role")));
                user.setStatus(Users.Status.valueOf(resultSet.getString("status")));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return usersList;
    }

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
    public Users getUserByEmail(String email) {
        return null;
    }
}
