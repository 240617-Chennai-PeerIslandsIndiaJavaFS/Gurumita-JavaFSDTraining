package org.example.DAO;

import org.example.Models.Users;

import java.util.List;

public interface UserDAO {
    Users createUser(Users user);
    Users updateUser(Users user);
    void deleteUser(int UserId);
    Users getUserById(int UserId);
    List<Users> getAllUsers();
    Users getUserByEmail(String email);

}
