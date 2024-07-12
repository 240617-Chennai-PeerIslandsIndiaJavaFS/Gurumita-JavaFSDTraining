package TestCases;

import org.example.DAO.UserDAO;
import org.example.Models.Users;
import org.example.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<Users> mockUsers = new ArrayList<>();
        mockUsers.add(new Users(1, "John Doe", "john.doe@example.com"));
        mockUsers.add(new Users(2, "Jane Smith", "jane.smith@example.com"));

        when(userDAO.getAllUsers()).thenReturn(mockUsers);
        List<Users> users = userService.getAllUsers();
        assertEquals(mockUsers.size(), users.size());
        assertEquals(mockUsers.get(0).getName(), users.get(0).getName());
        assertEquals(mockUsers.get(1).getName(), users.get(1).getName());
        verify(userDAO, times(1)).getAllUsers();
    }

    @Test
    void testAddUser() {
        Users newUser = new Users(3, "Emily Brown", "emily.brown@example.com");
        when(userDAO.createUser(any(Users.class))).thenReturn(newUser);
        userService.addUser(newUser);
        verify(userDAO, times(1)).createUser(any(Users.class));
    }

    @Test
    void testUpdateUser() {
        Users existingUser = new Users(4, "Michael Johnson", "michael.johnson@example.com");
        when(userDAO.updateUser(any(Users.class))).thenReturn(existingUser);
        userService.updateUser(existingUser);
        verify(userDAO, times(1)).updateUser(any(Users.class));
    }

    @Test
    void testDeleteUser() {
        int userId = 5;
        doNothing().when(userDAO).deleteUser(userId);
        userService.deleteUser(userId);
        verify(userDAO, times(1)).deleteUser(userId);
    }

    @Test
    void testGetUserById() {
        int userId = 6;
        Users user = new Users(userId, "William Lee", "william.lee@example.com");

        when(userDAO.getUserById(userId)).thenReturn(user);
        Users fetchedUser = userService.getUserById(userId);
        assertEquals(user.getName(), fetchedUser.getName());
        verify(userDAO, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserByEmail() {
        String email = "sarah.green@example.com";
        Users user = new Users(7, "Sarah Green", email);
        when(userDAO.getUserByEmail(email)).thenReturn(user);
        Users fetchedUser = userService.getUserByEmail(email);
        assertEquals(user.getName(), fetchedUser.getName());
        verify(userDAO, times(1)).getUserByEmail(email);
    }
}
