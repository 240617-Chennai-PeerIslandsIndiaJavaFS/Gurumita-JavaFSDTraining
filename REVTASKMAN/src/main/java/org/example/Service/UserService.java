package org.example.Service;

import org.example.DAO.UserDAO;
import org.example.DAO.UserDAOImpl;
import org.example.Models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDAO dao;

    public UserService() {
        dao = new UserDAOImpl();
    }

    public List<Users> getAllUsers() {
        logger.debug("Fetching all users");
        return dao.getAllUsers();
    }

    public void addUser(Users newUser) {
        dao.createUser(newUser);
        logger.info("User added: {}", newUser.getName());
    }

    public void updateUser(Users user) {
        dao.updateUser(user);
        logger.info("User updated: {}", user.getName());
    }

    public void deleteUser(int userId) {
        dao.deleteUser(userId);
        logger.info("User deleted with ID: {}", userId);
    }

    public Users getUserById(int userId) {
        logger.debug("Fetching user by ID: {}", userId);
        return dao.getUserById(userId);
    }

    public Users getUserByEmail(String email) {
        logger.debug("Fetching user by email: {}", email);
        return dao.getUserByEmail(email);
    }
}
