package org.example;

import org.example.DAO.TaskDao;
import org.example.DAO.TaskDaoImpl;
import org.example.Service.ProjectManagerService;
import org.example.Models.Tasks;

import java.util.List;
import java.util.Scanner;

public class ProjectApp {

    public static void main(String[] args) {
        // Example usage
        TaskDao taskDao = new TaskDaoImpl(); // Instantiate your DAO implementation
        ProjectManagerService projectManagerService = new ProjectManagerService(taskDao);

        // Example: Getting tasks by user ID from console input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.close();

        try {
            List<Tasks> tasks = projectManagerService.getTasksByUserId(userId);
            // Process tasks as needed
            for (Tasks task : tasks) {
                System.out.println("Task: " + task.getTask_name() + ", Status: " + task.getTask_status());
            }
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
            // Handle exception appropriately
        }
    }
}
