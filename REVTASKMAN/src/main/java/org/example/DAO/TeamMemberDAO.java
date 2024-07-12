package org.example.DAO;

import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;

import java.sql.SQLException;
import java.util.List;

public interface TeamMemberDAO {
    List<Tasks> getTasksByAssignedUserId(int userId) throws SQLException;
    boolean updateTaskStatus(int taskId, String status) throws SQLException;
    List<Projects> getProjectsByUserId(int userId) throws SQLException;
    List<Clients> getClientsByUserId(int userId) throws SQLException;
    List<Tasks> getTasksForTeamMember(int userId) throws SQLException;
}

