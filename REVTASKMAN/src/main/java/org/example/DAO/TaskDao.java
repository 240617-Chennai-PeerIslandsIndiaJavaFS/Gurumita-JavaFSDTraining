package org.example.DAO;

import org.example.Models.Tasks;

import java.util.List;

public interface TaskDao {
    List<Tasks> getAllTasks();

    List<Tasks> getTasksByUserId(int userId);

    void updateTask(Tasks task);
}
