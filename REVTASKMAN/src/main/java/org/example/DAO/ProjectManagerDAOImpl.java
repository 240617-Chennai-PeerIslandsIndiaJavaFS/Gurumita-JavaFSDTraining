package org.example.DAO;

import org.example.Connection.DBConnection;
import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;
import org.example.Models.Users;
import org.example.Models.Tasks.Taskstatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.Connection.DBConnection.getConnection;

public class ProjectManagerDAOImpl implements ProjectManagerDAO {

    static Connection conn;

    public ProjectManagerDAOImpl() {
        conn = getConnection();
    }

    @Override
    public boolean login(String email, String password) throws SQLException {
        String query = "SELECT role FROM users WHERE email_id = ? AND password = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    return "PROJECT_MANAGER".equals(role);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean resetPassword(int userId, String newPassword) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, newPassword);
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Clients> getAllClients() throws SQLException {
        String query = "SELECT * FROM clients";
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            List<Clients> clients = new ArrayList<>();
            while (rs.next()) {
                Clients client = new Clients();
                client.setClient_id(rs.getInt("client_id"));
                client.setClient_name(rs.getString("client_name"));
                client.setClient_info(rs.getString("client_info"));
                client.setClient_email(rs.getString("client_email"));
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Projects> getProjectsByClientId(int clientId) throws SQLException {
        String query = "SELECT * FROM projects WHERE client_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, clientId);
            try (ResultSet rs = pst.executeQuery()) {
                List<Projects> projects = new ArrayList<>();
                while (rs.next()) {
                    Projects project = new Projects();
                    project.setProject_id(rs.getInt("project_id"));
                    project.setProject_name(rs.getString("project_name"));
                    project.setProject_desc(rs.getString("project_desc"));
                    project.setClient_id(rs.getInt("client_id"));
                    project.setUser_id(rs.getInt("user_id"));
                    project.setStart_date(rs.getDate("start_date"));
                    project.setDue_date(rs.getDate("due_date"));
                    projects.add(project);
                }
                return projects;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tasks> getTasksByProjectId(int projectId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE project_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, projectId);
            try (ResultSet rs = pst.executeQuery()) {
                List<Tasks> tasks = new ArrayList<>();
                while (rs.next()) {
                    Tasks task = new Tasks();
                    task.setTask_id(rs.getInt("task_id"));
                    task.setTask_name(rs.getString("task_name"));
                    task.setTask_desc(rs.getString("task_desc"));
                    try {
                        task.setTask_status(Taskstatus.valueOf(rs.getString("task_status")));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid task status: " + rs.getString("task_status"));
                        continue;
                    }
                    task.setProject_id(rs.getInt("project_id"));
                    task.setAssigned_user_id(rs.getInt("assigned_user_id"));
                    tasks.add(task);
                }
                return tasks;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addTeamMemberToProject(int projectId, int userId) throws SQLException {
        if (!isProjectExists(projectId)) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }
        String query = "INSERT INTO project_user (project_id, user_id, role_in_project, is_active) VALUES (?, ?, 'TEAM_MEMBER', TRUE)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, projectId);
            pst.setInt(2, userId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isProjectExists(int projectId) throws SQLException {
        String query = "SELECT COUNT(*) FROM projects WHERE project_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, projectId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private boolean isMilestoneExists(int milestoneId) throws SQLException {
        String query = "SELECT COUNT(*) FROM milestone WHERE milestone_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, milestoneId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean assignTaskToTeamMember(int taskId, int userId) throws SQLException {
        String query = "UPDATE tasks SET assigned_user_id = ? WHERE task_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, userId);
            pst.setInt(2, taskId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createProject(Projects project) throws SQLException {
        String sql = "INSERT INTO projects (project_name, project_desc, client_id, user_id, start_date, due_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, project.getProject_name());
            stmt.setString(2, project.getProject_desc());
            stmt.setInt(3, project.getClient_id());
            stmt.setInt(4, project.getUser_id());
            stmt.setDate(5, project.getStart_date());
            stmt.setDate(6, project.getDue_date());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean createTask(Tasks task) throws SQLException {
        String sql = "INSERT INTO tasks (task_name, task_desc, task_status, project_id, assigned_user_id, milestone_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, task.getTask_name());
            pst.setString(2, task.getTask_desc());
            pst.setString(3, task.getTask_status().name()); // Ensure task_status is not null
            pst.setInt(4, task.getProject_id());
            pst.setInt(5, task.getAssigned_user_id());
            pst.setInt(6, task.getMilestone_id());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isUserAssignedToProject(int projectId, int userId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT COUNT(*) FROM PROJECT_USER WHERE project_id = ? AND user_id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, projectId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

            return false;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
    public static boolean addUserToProject(int projectId, int userId, String role) throws SQLException {
        String query = "INSERT INTO PROJECT_USER (project_id, user_id, role_in_project) VALUES (?, ?, ?)";
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, projectId);
            statement.setInt(2, userId);
            statement.setString(3, role);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public Users getUserByEmailAndPassword(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Tasks> getTasksByAssignedUserId(int userId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE assigned_user_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                List<Tasks> tasks = new ArrayList<>();
                while (rs.next()) {
                    Tasks task = new Tasks();
                    task.setTask_id(rs.getInt("task_id"));
                    task.setTask_name(rs.getString("task_name"));
                    task.setTask_desc(rs.getString("task_desc"));
                    task.setTask_status(Taskstatus.valueOf(rs.getString("task_status")));
                    task.setProject_id(rs.getInt("project_id"));
                    task.setAssigned_user_id(rs.getInt("assigned_user_id"));
                    tasks.add(task);
                }
                return tasks;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean loginByNameAndPassword(String name, String password) {
        return false;
    }

    @Override
    public boolean resetPasswordByName(String name, String newPassword) {
        return false;
    }

    @Override
    public List<Users> getTeamMembers() throws SQLException {
        List<Users> teamMembers = new ArrayList<>();
        String query = "SELECT * FROM USERS WHERE role = 'TEAM_MEMBER'";
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail_id(rs.getString("email_id"));
                user.setRole(Users.Role.valueOf(rs.getString("role")));
                user.setStatus(Users.Status.valueOf(rs.getString("status")));
                teamMembers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teamMembers;
    }

    public boolean clientExists(int clientId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM clients WHERE client_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

//    public void createProject(Projects project) throws SQLException {
//        String sql = "INSERT INTO projects (project_name, project_desc, client_id, user_id, start_date, due_date) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, project.getProject_name());
//            stmt.setString(2, project.getProject_desc());
//            stmt.setInt(3, project.getClient_id());
//            stmt.setInt(4, project.getUser_id());
//            stmt.setDate(5, project.getStart_date());
//            stmt.setDate(6, project.getDue_date());
//            stmt.executeUpdate();
//        }
//    }

    public List<Users> getAllProjectManagers() {
        List<Users> projectManagers = new ArrayList<>();
        String sql = "SELECT user_id, name FROM users WHERE role = 'Project Manager'";
        try {
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery(); {
            while (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                projectManagers.add(user);
            }
        }
        return projectManagers;
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Users getUserById(int userId) {
        return null;
    }

    @Override
    public void deactivateUser(int userId) {

    }

    public List<Users> getAvailableTeamMembers() {
        List<Users> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'TEAM_MEMBER' AND status = 'ACTIVE'";

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Users user = new Users();
                user.setUser_id(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail_id(rs.getString("email_id"));
                user.setRole(Users.Role.valueOf(rs.getString("role")));
                user.setStatus(Users.Status.valueOf(rs.getString("status")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<Projects> getAllProjects() {
        List<Projects> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Projects project = new Projects();
                project.setProject_id(rs.getInt("project_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setProject_desc(rs.getString("project_desc"));
                project.setClient_id(rs.getInt("client_id"));
                project.setUser_id(rs.getInt("user_id"));
                project.setStart_date(rs.getDate("start_date"));
                project.setDue_date(rs.getDate("due_date"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
