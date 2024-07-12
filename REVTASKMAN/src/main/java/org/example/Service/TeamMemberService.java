package org.example.Service;

import org.example.DAO.TeamMemberDAO;
import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class TeamMemberService {
    private static final Logger logger = LoggerFactory.getLogger(TeamMemberService.class);
    private final TeamMemberDAO teamMemberDAO;

    public TeamMemberService(TeamMemberDAO teamMemberDAO) {
        this.teamMemberDAO = teamMemberDAO;
    }

    public List<Tasks> getTasksByAssignedUserId(int userId) throws SQLException {
        logger.info("Fetching tasks for assigned user ID: {}", userId);
        try {
            List<Tasks> tasks = teamMemberDAO.getTasksByAssignedUserId(userId);
            logger.info("Successfully fetched tasks for assigned user ID: {}", userId);
            return tasks;
        } catch (SQLException e) {
            logger.error("Error fetching tasks for assigned user ID: {}", userId, e);
            throw e;
        }
    }

    public boolean updateTaskStatus(int taskId, String status) throws SQLException {
        logger.info("Updating task status. Task ID: {}, Status: {}", taskId, status);
        try {
            boolean result = teamMemberDAO.updateTaskStatus(taskId, status);
            if (result) {
                logger.info("Successfully updated task status. Task ID: {}, Status: {}", taskId, status);
            } else {
                logger.warn("Failed to update task status. Task ID: {}, Status: {}", taskId, status);
            }
            return result;
        } catch (SQLException e) {
            logger.error("Error updating task status. Task ID: {}, Status: {}", taskId, status, e);
            throw e;
        }
    }

    public List<Projects> getProjectsByUserId(int userId) throws SQLException {
        logger.info("Fetching projects for user ID: {}", userId);
        try {
            List<Projects> projects = teamMemberDAO.getProjectsByUserId(userId);
            logger.info("Successfully fetched projects for user ID: {}", userId);
            return projects;
        } catch (SQLException e) {
            logger.error("Error fetching projects for user ID: {}", userId, e);
            throw e;
        }
    }

    public List<Clients> getClientsByUserId(int userId) throws SQLException {
        logger.info("Fetching clients for user ID: {}", userId);
        try {
            List<Clients> clients = teamMemberDAO.getClientsByUserId(userId);
            logger.info("Successfully fetched clients for user ID: {}", userId);
            return clients;
        } catch (SQLException e) {
            logger.error("Error fetching clients for user ID: {}", userId, e);
            throw e;
        }
    }

    public List<Tasks> getTasksForTeamMember(int currentUserId) {
        logger.info("Fetching tasks for current user ID: {}", currentUserId);
        try {
            List<Tasks> tasks = teamMemberDAO.getTasksByAssignedUserId(currentUserId);
            logger.info("Successfully fetched tasks for current user ID: {}", currentUserId);
            return tasks;
        } catch (SQLException e) {
            logger.error("Error fetching tasks for current user ID: {}", currentUserId, e);
            throw new RuntimeException(e);  // Re-throwing as unchecked exception for simplicity
        }
    }
}
