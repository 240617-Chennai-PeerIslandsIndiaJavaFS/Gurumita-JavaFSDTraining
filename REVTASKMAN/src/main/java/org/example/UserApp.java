package org.example;

import org.example.Models.Role;
import org.example.Models.Users;
import org.example.Service.UserService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserApp {
    private static UserService userService;

    public UserApp(UserService userService) {
        UserApp.userService = userService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome");

        boolean loggedIn = false;
        Users loggedInUser = null;

        while (!loggedIn) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            loggedInUser = authenticateUser(email, password);
            if (loggedInUser != null) {
                loggedIn = true;
                System.out.println("Login successful!");
                displayMenu(scanner, loggedInUser);
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        }

        scanner.close();
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

    private static void displayMenu(Scanner scanner, Users loggedInUser) {
        Users.Role role = loggedInUser.getRole();

        switch (role) {
            case ADMIN:
                displayAdminMenu(scanner);
                break;
            case PROJECT_MANAGER:
                displayProjectManagerMenu(scanner);
                break;
            case TEAM_MEMBER:
                displayTeamMemberMenu(scanner);
                break;
            default:
                System.out.println("Unknown role.");
        }
    }

    private static void displayAdminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Menu - Choose an option:");
            System.out.println("1. Create a new user");
            System.out.println("2. Update user");
            System.out.println("3. Deactivate a user account");
            System.out.println("4. Assign/Adjust access levels");
            System.out.println("5. View user activity reports");
            System.out.println("6. Exit");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createUser(scanner);
                        break;
                    case 2:
                        updateUser(scanner);
                        break;
                    case 3:
                        deactivateUser(scanner);
                        break;
                    case 4:
                        assignAccessLevels(scanner);
                        break;
                    case 5:
                        viewUserActivityReports();
                        break;
                    case 6:
                        exit = true;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private static void createUser(Scanner scanner) {
        System.out.println("\nEnter details for new user:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Role (ADMIN, PROJECT_MANAGER, TEAM_MEMBER): ");
        String roleStr = scanner.nextLine();
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

    private static void updateUser(Scanner scanner) {
        System.out.print("\nEnter user ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left-over

        Users existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            System.out.println("Current details: " + existingUser.getName() + ", " + existingUser.getEmail_id() + ", " + existingUser.getRole() + " ");

            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            System.out.print("Email: ");
            String newEmail = scanner.nextLine();
            System.out.print("Role (ADMIN, PROJECT_MANAGER, TEAM_MEMBER): ");
            String roleInput = scanner.nextLine().toUpperCase().replace(" ", "_");
            Users.Role newRole = Users.Role.valueOf(roleInput);
            System.out.print("Enter new status (ACTIVE, INACTIVE): ");
            String statusInput = scanner.nextLine().toUpperCase().replace(" ", "_");
            Users.Status newStatus = Users.Status.valueOf(statusInput);

            existingUser.setName(newName);
            existingUser.setPassword(newPassword);
            existingUser.setEmail_id(newEmail);

            if (newRole != null) {
                existingUser.setRole(newRole);
            } else {
                System.out.println("Invalid role. Role not updated.");
            }

            if (newStatus != null) {
                existingUser.setStatus(newStatus);
            } else {
                System.out.println("Invalid status. Status not updated.");
            }

            userService.updateUser(existingUser);
            System.out.println("User updated: " + existingUser.getName());
        } else {
            System.out.println("User not found with ID: " + userId);
        }
    }

    private static void deactivateUser(Scanner scanner) {
        System.out.print("\nEnter user ID to deactivate: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Users existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            existingUser.setStatus(Users.Status.INACTIVE);
            userService.updateUser(existingUser);
            System.out.println("User deactivated: " + existingUser.getName());
        } else {
            System.out.println("User not found with ID: " + userId);
        }
    }

    private static void assignAccessLevels(Scanner scanner) {
        System.out.print("\nEnter user ID to adjust access levels: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Users existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            System.out.print("Enter new role (ADMIN, PROJECT_MANAGER, TEAM_MEMBER): ");
            String roleInput = scanner.nextLine().toUpperCase().replace(" ", "_");
            Users.Role newRole = Users.Role.valueOf(roleInput);

            if (newRole != null) {
                existingUser.setRole(newRole);
                userService.updateUser(existingUser);
                System.out.println("User role updated to: " + newRole);
            } else {
                System.out.println("Invalid role. Role not updated.");
            }
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

    private static void displayProjectManagerMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nProject Manager Menu - Choose an option:");
            System.out.println("1. View user activity reports");
            System.out.println("2. Exit");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        viewUserActivityReports();
                        break;
                    case 2:
                        exit = true;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private static void displayTeamMemberMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nTeam Member Menu - Choose an option:");
            System.out.println("1. View user activity reports");
            System.out.println("2. Exit");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        viewUserActivityReports();
                        break;
                    case 2:
                        exit = true;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        UserApp userApp = new UserApp(userService);
        userApp.start();
    }
}
