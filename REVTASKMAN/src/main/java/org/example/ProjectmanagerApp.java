package org.example;

import org.example.DAO.*;
import org.example.Models.*;
import org.example.Service.*;
import org.example.Models.Tasks.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProjectmanagerApp {
    private static ProjectManagerService projectManagerService;
    private static final ProjectManagerDAO projectManagerDAO = new ProjectManagerDAOImpl();
    private static final UserService userService = new UserService();
    private static final TeamMemberService teamMemberService = new TeamMemberService(new TeamMemberDAOImpl());
    private static final ClientService clientService = new ClientService();
    private static final TaskService taskService = new TaskService();
    private static final Scanner scanner = new Scanner(System.in);

    static {
        TaskDao taskDao = new TaskDaoImpl();

        projectManagerService = new ProjectManagerService(projectManagerDAO, taskDao);
    }
    public static void main(String[] args) {
        System.out.println("Welcome to Task Management!");

        boolean loggedIn = false;
        Users loggedInUser = null;

        while (!loggedIn) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine().trim();

            try {
                if (projectManagerDAO.login(email, password)) {
                    int userId = getUserIdByEmail(email);
                    loggedInUser = new Users();
                    loggedInUser.setUser_id(userId);
                    loggedInUser.setEmail_id(email);
                    loggedInUser.setRole(Users.Role.PROJECT_MANAGER);
                    loggedIn = true;
                    System.out.println("Login successful as Project Manager!");
                } else {
                    loggedInUser = authenticateUser(email, password);
                    if (loggedInUser != null) {
                        loggedIn = true;
                        System.out.println("Login successful as User!");
                    } else {
                        System.out.println("Invalid email or password. Please try again.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error occurred during login: " + e.getMessage());
            }
        }

        if (loggedInUser.getRole() == Users.Role.PROJECT_MANAGER) {
            showProjectManagerMenu(loggedInUser);
        } else if (loggedInUser.getRole() == Users.Role.TEAM_MEMBER) {
            showTeamMemberMenu(loggedInUser);
        } else {
            showUserMenu(loggedInUser);
        }
    }

    private static Users authenticateUser(String email, String password) {
        List<Users> usersList = userService.getAllUsers();
        for (Users user : usersList) {
            if (user.getEmail_id().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static int getUserIdByEmail(String email) {
        return 0; // Implement logic to fetch user ID by email
    }

    private static void showProjectManagerMenu(Users loggedInUser) {
        while (true) {
            System.out.println("\nProject Manager Menu - Choose an option:");
            System.out.println("1. Reset Password");
            System.out.println("2. View All Clients");
            System.out.println("3. View Projects by Client");
            System.out.println("4. View Tasks by Project");
            System.out.println("5. Create Task");
            System.out.println("6. Add Team Member to Project");
            System.out.println("7. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        resetPassword();
                        break;
                    case 2:
                        viewAllClients();
                        break;
                    case 3:
                        viewProjectsByClient();
                        break;
                    case 4:
                        viewTasksByProject();
                        break;
                    case 5:
                        createTask();
                        break;
                    case 6:
                        addTeamMemberToProject();
                        break;
                    case 7:
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (SQLException e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }

    private static void createTask() {
        try {
            System.out.println("\nEnter details for new task:");
            System.out.print("Task Name: ");
            String taskName = scanner.nextLine().trim();
            System.out.print("Task Description: ");
            String taskDesc = scanner.nextLine().trim();
            System.out.print("Task Status (STARTED, IN_PROGRESS, COMPLETED): ");
            String taskStatusInput = scanner.nextLine().trim().toUpperCase();

            Tasks.Taskstatus taskStatus = null;
            try {
                taskStatus = Tasks.Taskstatus.valueOf(taskStatusInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid task status provided. Please enter one of the following: PENDING, IN_PROGRESS, COMPLETED, CANCELLED.");
                return;
            }

            System.out.print("Project ID: ");
            int projectId = scanner.nextInt();
            scanner.nextLine();

            List<Users> teamMembers = ProjectManagerService.getTeamMembers();

            if (teamMembers.isEmpty()) {
                System.out.println("No team members found. Cannot assign task.");
                return;
            }

            System.out.println("\nAvailable Team Members:");
            for (Users member : teamMembers) {
                if (member.getRole() == Users.Role.TEAM_MEMBER) {
                    System.out.println(member.getUser_id() + ". " + member.getName());
                }
            }

            System.out.print("\nEnter User ID of the Team Member to assign the task: ");
            int assignedUserId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            boolean isValidMember = false;
            for (Users member : teamMembers) {
                if (member.getUser_id() == assignedUserId && member.getRole() == Users.Role.TEAM_MEMBER) {
                    isValidMember = true;
                    break;
                }
            }

            if (!isValidMember) {
                System.out.println("Invalid User ID or User is not a Team Member. Task assignment failed.");
                return;
            }

            System.out.print("Milestone ID: ");
            int milestoneId = scanner.nextInt();
            scanner.nextLine();

            Tasks newTask = new Tasks();
            newTask.setTask_name(taskName);
            newTask.setTask_desc(taskDesc);
            newTask.setTask_status(taskStatus);
            newTask.setAssigned_user_id(assignedUserId);
            newTask.setMilestone_id(milestoneId);

            ProjectManagerService.addTask(newTask);
            System.out.println("New task created and assigned to User ID " + assignedUserId + ": " + taskName);

        } catch (Exception e) {
            System.out.println("An error occurred while creating the task: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void resetPassword() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        try {
            Users user = projectManagerDAO.getUserByEmailAndPassword(email, password);
            if (user != null && user.getRole() == Users.Role.PROJECT_MANAGER) {
                if (!user.getEmail_id().equals(email)) {
                    System.out.println("You can only reset your own password.");
                    return;
                }

                System.out.print("Enter your new password: ");
                String newPassword = scanner.nextLine().trim();

                if (projectManagerDAO.resetPassword(user.getUser_id(), newPassword)) {
                    System.out.println("Password reset successful for user: " + user.getName());
                } else {
                    System.out.println("Failed to reset password. Please try again later.");
                }
            } else if (user == null) {
                System.out.println("User not found with email: " + email);
            } else {
                System.out.println("You are not authorized to reset passwords for this user.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private static void viewAllClients() throws SQLException {
        List<Clients> clients = projectManagerDAO.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            System.out.println("List of Clients:");
            for (Clients client : clients) {
                System.out.println(client.getClient_id() + " " + client.getClient_name() + " " + client.getClient_info());
            }
        }
    }

    private static void viewProjectsByClient() throws SQLException {
        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Consume newline after nextInt()

        List<Projects> projects = projectManagerDAO.getProjectsByClientId(clientId);
        if (projects.isEmpty()) {
            System.out.println("No projects found for client ID " + clientId);
        } else {
            System.out.println("Projects for Client ID " + clientId + ":");
            for (Projects project : projects) {
                System.out.println(project.getProject_id() + " " + project.getProject_name() + " " + project.getProject_desc());
            }
        }
    }

    private static void viewTasksByProject() throws SQLException {
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline after nextInt()

        List<Tasks> tasks = projectManagerDAO.getTasksByProjectId(projectId);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for project ID " + projectId);
        } else {
            System.out.println("Tasks for Project ID " + projectId + ":");
            for (Tasks task : tasks) {
                System.out.println(task.getTask_id() + " " + task.getTask_name() + " " + task.getTask_desc() + " " + task.getTask_status());
            }
        }
    }

    private static void addTeamMemberToProject() throws SQLException {
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline after nextInt()

        System.out.print("Enter user ID of team member to add: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline after nextInt()

        if (projectManagerDAO.addTeamMemberToProject(userId, projectId)) {
            System.out.println("User ID " + userId + " added to Project ID " + projectId + " successfully.");
        } else {
            System.out.println("Failed to add user ID " + userId + " to Project ID " + projectId);
        }
    }


    private static void showTeamMemberMenu(Users loggedInUser) {
        while (true) {
            System.out.println("\nTeam Member Menu - Choose an option:");
            System.out.println("1. View Assigned Tasks");
            System.out.println("2. Update Task Status");
            System.out.println("3. View Project Details");
            System.out.println("4. View Client Details");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline after nextInt()

            try {
                switch (choice) {
                    case 1:
                        viewAssignedTasks(loggedInUser.getUser_id());
                        break;
                    case 2:
                        updateTaskStatus();
                        break;
                    case 3:
                        viewProjectDetails(loggedInUser.getUser_id());
                        break;
                    case 4:
                        viewClientDetails(loggedInUser.getUser_id());
                        break;
                    case 5:
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (SQLException e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }

    private static void viewAssignedTasks(int userId) throws SQLException {
        List<Tasks> tasks = teamMemberService.getTasksByAssignedUserId(userId);

        if (tasks.isEmpty()) {
            System.out.println("No tasks assigned.");
        } else {
            System.out.println("Assigned Tasks:");
            for (Tasks task : tasks) {
                System.out.println(task.getTask_name() + " " + task.getTask_desc());
            }
        }
    }

    static void updateTaskStatus() throws SQLException {
        System.out.print("Enter task ID: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new status ('STARTED, IN_PROGRESS, COMPLETED): ");
        String newStatus = scanner.nextLine().trim();

        if (teamMemberService.updateTaskStatus(taskId, newStatus)) {
            System.out.println("Task status updated successfully.");
        } else {
            System.out.println("Failed to update task status.");
        }
    }
    private static int getCurrentUserId() {
        System.out.print("Enter your User ID: ");
        return scanner.nextInt();
    }
    private static void reassignTasksOnUserDeletion(int userId) {
        List<Tasks> tasks;
        tasks = projectManagerService.getTasksByUserId(userId);
        if (!tasks.isEmpty()) {
            System.out.println("Reassigning tasks...");
            List<Users> availableTeamMembers = projectManagerService.getAvailableTeamMembers();

            for (Tasks task : tasks) {
                for (Users member : availableTeamMembers) {
                    if (member.getRole() == Users.Role.TEAM_MEMBER) {
                        task.setAssigned_user_id(member.getUser_id());
                        projectManagerService.updateTask(task);
                        System.out.println("Task ID " + task.getTask_id() + " reassigned to User ID " + member.getUser_id());
                        break;
                    }
                }
            }
        }
        else {
            System.out.println("No tasks found assigned to User ID " + userId);
        }
    }


    private static void viewProjectDetails(int userId) throws SQLException {
        List<Projects> projects = teamMemberService.getProjectsByUserId(userId);
        if (projects.isEmpty()) {
            System.out.println("No projects found for this user.");
        } else {
            System.out.println("Projects:");
            for (Projects project : projects) {
                System.out.println(project.getProject_id() + " " + project.getProject_name() + " " + project.getProject_desc());
            }
        }
    }
    private static void viewClientDetails(int userId) throws SQLException {
        List<Clients> clients = teamMemberService.getClientsByUserId(userId);
        if (clients.isEmpty()) {
            System.out.println("No clients found for this user.");
        } else {
            System.out.println("Clients:");
            for (Clients client : clients) {
                System.out.println(client.getClient_id() + " " + client.getClient_name() + " " + client.getClient_info());
            }
        }
    }
    private static void showUserMenu(Users loggedInUser) {
        Users.Role role = loggedInUser.getRole();

        switch (role) {
            case ADMIN:
                displayAdminMenu();
                break;
            default:
                System.out.println("Unknown role.");
        }
    }
    private static void displayAdminMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Menu - Choose an option:");
            System.out.println("1. Create a new user");
            System.out.println("2. Update user");
            System.out.println("3. Deactivate a user account");
            System.out.println("4. Assign/Adjust access levels");
            System.out.println("5. View user activity reports");
            System.out.println("6. Create Project");
            System.out.println("7. Create Client");
            System.out.println("8. Logout");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        updateUser();
                        break;
                    case 3:
                        deactivateUser();
                        break;
                    case 4:
                        assignAccessLevels();
                        break;
                    case 5:
                        viewUserActivityReports();
                        break;
                    case 6:
                        createProject();
                        break;
                    case 7:
                        createClient();
                        break;
                    case 8:
                        exit = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void createProject() throws SQLException {
        try {
            System.out.println("\nEnter details for new project:");
            System.out.print("Project Name: ");
            String projectName = scanner.nextLine();
            System.out.print("Project Description: ");
            String projectDesc = scanner.nextLine();
            System.out.print("Client_id: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();
            ProjectManagerService projectService = new ProjectManagerService();
            List<Users> projectManagers = projectService.getAllProjectManagers();
            System.out.println("Available Project Managers:");
            for (Users pm : projectManagers) {
                System.out.println("ID: " + pm.getUser_id() + ", Name: " + pm.getName());
            }

            System.out.print("Select Project Manager User ID: ");
            int userId = scanner.nextInt();
            scanner.nextLine();  // consume newline

            System.out.print("Start Date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            System.out.print("Due Date (YYYY-MM-DD): ");
            String dueDate = scanner.nextLine();

            Date startDateSQL = validateAndParseDate(startDate);
            Date dueDateSQL = validateAndParseDate(dueDate);

            Projects newProject = new Projects();
            newProject.setProject_name(projectName);
            newProject.setProject_desc(projectDesc);
            newProject.setClient_id(clientId);
            newProject.setUser_id(userId);
            newProject.setStart_date(startDateSQL);
            newProject.setDue_date(dueDateSQL);

            projectService.addProject(newProject);
            System.out.println("New project created: " + projectName);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid date format. Please enter the date in YYYY-MM-DD format.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static Date validateAndParseDate(String dateStr) {
        try {
            return Date.valueOf(dateStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }
    private static void createClient() {
        System.out.println("\nEnter details for new client:");
        System.out.print("Client Name: ");
        String clientName = scanner.nextLine();
        System.out.print("Client Info: ");
        String clientInfo = scanner.nextLine();
        System.out.print("Client Email: ");
        String clientEmail = scanner.nextLine();

        Clients newClient = new Clients();
        newClient.setClient_name(clientName);
        newClient.setClient_info(clientInfo);
        newClient.setClient_email(clientEmail);

        clientService.addClient(newClient);
        System.out.println("New client created: " + clientName);
    }

    private static void createUser() {
        System.out.println("\nEnter details for new user:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Role (ADMIN,PROJECT_MANAGER,TEAM_MEMBER): ");
        String roleStr = scanner.nextLine().toUpperCase().replace(" ", "_");
        Users.Role role = Users.Role.valueOf(roleStr);
        Users.Status status = Users.Status.ACTIVE;

        Users newUser = new Users();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setEmail_id(email);
        newUser.setRole(role);
        newUser.setStatus(status);

        userService.addUser(newUser);
        System.out.println("New user created: " + name);
    }

    private static void updateUser() {
        System.out.print("\nEnter user ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline after nextInt()

        Users existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            System.out.println("Current details: " + existingUser.getName() + ", " + existingUser.getEmail_id());

            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter new role (ADMIN, TEAM_MEMBER): ");
            String roleStr = scanner.nextLine().toUpperCase().replace(" ", "_");
            Users.Role newRole = Users.Role.valueOf(roleStr);
            System.out.print("Enter new status (ACTIVE, INACTIVE): ");
            String statusStr = scanner.nextLine().toUpperCase().replace(" ", "_");
            Users.Status newStatus = Users.Status.valueOf(statusStr);

            existingUser.setName(newName);
            existingUser.setPassword(newPassword);
            existingUser.setEmail_id(newEmail);
            existingUser.setRole(newRole);
            existingUser.setStatus(newStatus);

            userService.updateUser(existingUser);
            System.out.println("User updated: " + existingUser.getName());
        } else {
            System.out.println("User not found with ID: " + userId);
        }
    }

    private static void deactivateUser() {
        System.out.print("\nEnter user ID to deactivate: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Users existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            existingUser.setStatus(Users.Status.INACTIVE);
            userService.updateUser(existingUser);
            System.out.println("User deactivated: " + existingUser.getName());
            reassignTasksOnUserDeletion(userId);
        } else {
            System.out.println("User not found with ID: " + userId);
        }
    }

    private static void assignAccessLevels() {
        System.out.print("\nEnter user ID to adjust access levels: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Users existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            System.out.print("Enter new role (ADMIN, TEAM_MEMBER): ");
            String roleStr = scanner.nextLine().toUpperCase().replace(" ", "_");
            Users.Role newRole = Users.Role.valueOf(roleStr);

            existingUser.setRole(newRole);
            userService.updateUser(existingUser);
            System.out.println("User access level updated successfully.");
        } else {
            System.out.println("User not found with ID: " + userId);
        }
    }
    private static void viewUserActivityReports() {
        List<Users> usersList = userService.getAllUsers();
        System.out.println("\nUser Activity Reports:");
        for (Users user : usersList) {
            System.out.println("User: " + user.getName() + ", Email: " + user.getEmail_id() + ", Role: " + user.getRole() + ", Status: " + user.getStatus());
        }

    }
}