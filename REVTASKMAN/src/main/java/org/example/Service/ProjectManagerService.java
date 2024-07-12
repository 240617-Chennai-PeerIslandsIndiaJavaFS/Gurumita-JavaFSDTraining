package org.example.Service;

import org.example.DAO.ProjectManagerDAO;
import org.example.DAO.TaskDao;
import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;
import org.example.Models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ProjectManagerService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectManagerService.class);

    private static ProjectManagerDAO projectManagerDAO;
    public static TaskDao taskDao;

    // Constructor initializing DAOs
    public ProjectManagerService() {
    }

    // Constructor with parameters
    public ProjectManagerService(ProjectManagerDAO projectManagerDAO, TaskDao taskDao) {
        this.projectManagerDAO = projectManagerDAO;
        this.taskDao = taskDao;
    }

    public ProjectManagerService(TaskDao taskDao) {
    }

    // Fetch all projects
    public List<Projects> getAllProjects() throws SQLException {
        logger.info("Fetching all projects");
        return projectManagerDAO.getAllProjects();
    }

    // Fetch available team members
    public static List<Users> getAvailableTeamMembers() {
        logger.info("Fetching available team members");
        return projectManagerDAO.getAvailableTeamMembers();
    }

    // Fetch team members
    public static List<Users> getTeamMembers() throws SQLException {
        logger.info("Fetching team members");
        return projectManagerDAO.getTeamMembers();
    }

    // Add a task
    public static void addTask(Tasks task) throws SQLException {
        logger.info("Adding a task");
        projectManagerDAO.createTask(task);
    }

    // Fetch all clients
    public List<Clients> getAllClients() throws SQLException {
        logger.info("Fetching all clients");
        return projectManagerDAO.getAllClients();
    }

    // Fetch projects by client ID
    public List<Projects> getProjectsByClientId(int clientId) throws SQLException {
        logger.info("Fetching projects for client " + clientId);
        return projectManagerDAO.getProjectsByClientId(clientId);
    }

    // Fetch user by ID
    public Users getUserById(int userId) {
        logger.info("Fetching user by ID " + userId);
        return projectManagerDAO.getUserById(userId);
    }

    // Fetch all tasks
    public List<Tasks> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskDao.getAllTasks();
    }

    // Fetch tasks by user ID
    public List<Tasks> getTasksByUserId(int userId) {
        logger.info("Fetching tasks by user ID " + userId);
        return taskDao.getTasksByUserId(userId);
    }

    // Update a task
    public static void updateTask(Tasks task) {
        logger.info("Updating a task");
        taskDao.updateTask(task);
    }

    // Reset password
    public boolean resetPassword(int userId, String newPassword) throws SQLException {
        logger.info("Resetting password for user " + userId);
        return projectManagerDAO.resetPassword(userId, newPassword);
    }

    // Assign task to team member
    public boolean assignTaskToTeamMember(int taskId, int userId) throws SQLException {
        logger.info("Assigning task " + taskId + " to team member " + userId);
        return projectManagerDAO.assignTaskToTeamMember(taskId, userId);
    }

    // Add a project
    public void addProject(Projects project) throws SQLException {
        logger.info("Adding a project");
        if (projectManagerDAO.clientExists(project.getClient_id())) {
            projectManagerDAO.createProject(project);
        } else {
            throw new SQLException("Client does not exist");
        }
    }

    // Fetch all project managers
    public List<Users> getAllProjectManagers() throws SQLException {
        logger.info("Fetching all project managers");
        return projectManagerDAO.getAllProjectManagers();
    }
}
