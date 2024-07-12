package org.example.DAO;

import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;
import org.example.Models.Users;

import java.sql.SQLException;
import java.util.List;

public interface ProjectManagerDAO {
    boolean login(String email, String password) throws SQLException;

    boolean resetPassword(int userId, String newPassword) throws SQLException;

    List<Clients> getAllClients() throws SQLException;

    List<Projects> getProjectsByClientId(int clientId) throws SQLException;

    List<Tasks> getTasksByProjectId(int projectId) throws SQLException;

    boolean addTeamMemberToProject(int projectId, int userId) throws SQLException;

    boolean assignTaskToTeamMember(int taskId, int userId) throws SQLException;

    void createProject(Projects project) throws SQLException;

    List<Projects> getAllProjects();

    boolean createTask(Tasks task) throws SQLException;

    Users getUserByEmailAndPassword(String email, String password) throws SQLException;

    List<Tasks> getTasksByAssignedUserId(int userId) throws SQLException;

    boolean loginByNameAndPassword(String name, String password);

    boolean resetPasswordByName(String name, String newPassword);

    List<Users> getTeamMembers() throws SQLException;

    List<Users> getAvailableTeamMembers();

    boolean clientExists(int clientId) throws SQLException;

    List<Users> getAllProjectManagers();

    Users getUserById(int userId);

    void deactivateUser(int userId);
}