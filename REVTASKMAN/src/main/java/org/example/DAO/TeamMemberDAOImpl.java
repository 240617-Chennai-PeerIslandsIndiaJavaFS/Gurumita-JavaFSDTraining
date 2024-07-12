package org.example.DAO;

import org.example.Connection.DBConnection;
import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;
import org.example.Models.Taskstatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamMemberDAOImpl implements TeamMemberDAO {
    private static Connection conn;

    public TeamMemberDAOImpl() {
        conn = DBConnection.getConnection();
    }

    @Override
    public List<Tasks> getTasksByAssignedUserId(int userId) throws SQLException {
        List<Tasks> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE assigned_user_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tasks task = new Tasks();
                task.setTask_id(resultSet.getInt("task_id"));
                task.setTask_name(resultSet.getString("task_name"));
                task.setTask_desc(resultSet.getString("task_desc"));
                task.setTask_status(Taskstatus.valueOf(resultSet.getString("task_status")));
                tasks.add(task);
            }
        }
        return tasks;
    }
    public List<Tasks> getTasksForTeamMember(int userId) throws SQLException {
        List<Tasks> tasks = new ArrayList<>();
        String sql = "SELECT * FROM TASKS WHERE assigned_user_id = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Tasks task = new Tasks();
                task.setTask_id(rs.getInt("task_id"));
                task.setTask_name(rs.getString("task_name"));
                task.setTask_desc(rs.getString("task_desc"));
                task.setTask_status(Tasks.Taskstatus.valueOf(rs.getString("task_status")));
                task.setProject_id(rs.getInt("project_id"));
                task.setAssigned_user_id(rs.getInt("assigned_user_id"));
                task.setMilestone_id(rs.getInt("milestone_id"));

                tasks.add(task);
            }
        }

        return tasks;
    }

    @Override
    public boolean updateTaskStatus(int taskId, String status) throws SQLException {
        String query = "UPDATE tasks SET task_status = ? WHERE task_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, status);
            statement.setInt(2, taskId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    @Override
    public List<Projects> getProjectsByUserId(int userId) throws SQLException {
        List<Projects> projects = new ArrayList<>();
        String query = "SELECT * FROM projects WHERE project_id IN (SELECT DISTINCT project_id FROM tasks WHERE assigned_user_id= ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Projects project = new Projects();
                project.setProject_id(resultSet.getInt("project_id"));
                project.setProject_name(resultSet.getString("project_name"));
                project.setClient_id(resultSet.getInt("client_id"));
                // Add more fields as needed
                projects.add(project);
            }
        }
        return projects;
    }

    @Override
    public List<Clients> getClientsByUserId(int userId) throws SQLException {
        List<Clients> clients = new ArrayList<>();
        String query = "SELECT * FROM clients WHERE client_id IN (SELECT DISTINCT client_id FROM projects WHERE project_id IN (SELECT DISTINCT project_id FROM tasks WHERE assigned_user_id = ?))";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Clients client = new Clients();
                client.setClient_id(resultSet.getInt("client_id"));
                client.setClient_name(resultSet.getString("client_name"));
                clients.add(client);
            }
        }
        return clients;
    }
}
