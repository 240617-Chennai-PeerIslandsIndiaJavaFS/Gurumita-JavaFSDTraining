package org.example.Service;

import org.example.DAO.TaskDao;
import org.example.DAO.TaskDaoImpl;
import org.example.Models.Tasks;

import java.util.List;

public class TaskService {
    private TaskDao taskDao;

    public TaskService() {
        this.taskDao = new TaskDaoImpl();
    }

    public List<Tasks> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public List<Tasks> getTasksByUserId(int userId) {
        return taskDao.getTasksByUserId(userId);
    }

    public void updateTask(Tasks task) {
        taskDao.updateTask(task);
    }

    // Other methods as needed
}
