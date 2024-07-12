package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.Models.Tasks;

import static org.example.Connection.DBConnection.getConnection;
import static org.example.Service.ProjectManagerService.taskDao;


public class TaskDaoImpl implements TaskDao {

    static Connection conn;

    public TaskDaoImpl() {
        conn = getConnection();
    }

    @Override
    public List<Tasks> getAllTasks() {
        return List.of();
    }

    @Override
    public List<Tasks> getTasksByUserId(int userId) {
        List<Tasks> tasks = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks WHERE assigned_user_id = ?")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tasks task = new Tasks();
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return tasks;
    }


    @Override
    public void updateTask(Tasks task) {

    }
}
