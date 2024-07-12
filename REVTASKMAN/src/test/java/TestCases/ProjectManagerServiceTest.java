package TestCases;

import org.example.DAO.ProjectManagerDAO;
import org.example.DAO.TaskDao;
import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;
import org.example.Models.Users;
import org.example.Service.ProjectManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectManagerServiceTest {

    @Mock
    private ProjectManagerDAO projectManagerDAO;

    @Mock
    private TaskDao taskDao;

    @InjectMocks
    private ProjectManagerService projectManagerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        projectManagerService = new ProjectManagerService(projectManagerDAO, taskDao);
    }

    @Test
    public void testGetAllProjects() throws SQLException {
        Projects project1 = new Projects();
        Projects project2 = new Projects();
        List<Projects> expectedProjects = Arrays.asList(project1, project2);

        when(projectManagerDAO.getAllProjects()).thenReturn(expectedProjects);

        List<Projects> projects = projectManagerService.getAllProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertEquals(expectedProjects, projects);

        verify(projectManagerDAO, times(1)).getAllProjects();
    }

    @Test
    public void testGetAvailableTeamMembers() {
        Users user1 = new Users();
        Users user2 = new Users();
        List<Users> expectedUsers = Arrays.asList(user1, user2);

        when(projectManagerDAO.getAvailableTeamMembers()).thenReturn(expectedUsers);

        List<Users> users = projectManagerService.getAvailableTeamMembers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(expectedUsers, users);

        verify(projectManagerDAO, times(1)).getAvailableTeamMembers();
    }

    @Test
    public void testGetTeamMembers() throws SQLException {
        Users user1 = new Users();
        Users user2 = new Users();
        List<Users> expectedUsers = Arrays.asList(user1, user2);

        when(projectManagerDAO.getTeamMembers()).thenReturn(expectedUsers);

        List<Users> users = projectManagerService.getTeamMembers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(expectedUsers, users);

        verify(projectManagerDAO, times(1)).getTeamMembers();
    }

//    @Test
//    public void testAddTask() throws SQLException {
//        Tasks task = new Tasks();
//
//        doNothing().when(projectManagerDAO).createTask(task);
//
//        projectManagerService.addTask(task);
//
//        verify(projectManagerDAO, times(1)).createTask(task);
//    }

    @Test
    public void testGetAllClients() throws SQLException {
        Clients client1 = new Clients();
        Clients client2 = new Clients();
        List<Clients> expectedClients = Arrays.asList(client1, client2);

        when(projectManagerDAO.getAllClients()).thenReturn(expectedClients);

        List<Clients> clients = projectManagerService.getAllClients();

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals(expectedClients, clients);

        verify(projectManagerDAO, times(1)).getAllClients();
    }

    @Test
    public void testGetProjectsByClientId() throws SQLException {
        int clientId = 1;
        Projects project1 = new Projects();
        Projects project2 = new Projects();
        List<Projects> expectedProjects = Arrays.asList(project1, project2);

        when(projectManagerDAO.getProjectsByClientId(clientId)).thenReturn(expectedProjects);

        List<Projects> projects = projectManagerService.getProjectsByClientId(clientId);

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertEquals(expectedProjects, projects);

        verify(projectManagerDAO, times(1)).getProjectsByClientId(clientId);
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        Users expectedUser = new Users();

        when(projectManagerDAO.getUserById(userId)).thenReturn(expectedUser);

        Users user = projectManagerService.getUserById(userId);

        assertNotNull(user);
        assertEquals(expectedUser, user);

        verify(projectManagerDAO, times(1)).getUserById(userId);
    }

    @Test
    public void testGetAllTasks() {
        Tasks task1 = new Tasks();
        Tasks task2 = new Tasks();
        List<Tasks> expectedTasks = Arrays.asList(task1, task2);

        when(taskDao.getAllTasks()).thenReturn(expectedTasks);

        List<Tasks> tasks = projectManagerService.getAllTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals(expectedTasks, tasks);

        verify(taskDao, times(1)).getAllTasks();
    }

    @Test
    public void testGetTasksByUserId() {
        int userId = 1;
        Tasks task1 = new Tasks();
        Tasks task2 = new Tasks();
        List<Tasks> expectedTasks = Arrays.asList(task1, task2);

        when(taskDao.getTasksByUserId(userId)).thenReturn(expectedTasks);

        List<Tasks> tasks = projectManagerService.getTasksByUserId(userId);

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals(expectedTasks, tasks);

        verify(taskDao, times(1)).getTasksByUserId(userId);
    }

    @Test
    public void testUpdateTask() {
        Tasks task = new Tasks();

        doNothing().when(taskDao).updateTask(task);

        projectManagerService.updateTask(task);

        verify(taskDao, times(1)).updateTask(task);
    }

    @Test
    public void testResetPassword() throws SQLException {
        int userId = 1;
        String newPassword = "newPassword";

        when(projectManagerDAO.resetPassword(userId, newPassword)).thenReturn(true);

        boolean result = projectManagerService.resetPassword(userId, newPassword);

        assertTrue(result);

        verify(projectManagerDAO, times(1)).resetPassword(userId, newPassword);
    }

    @Test
    public void testAssignTaskToTeamMember() throws SQLException {
        int taskId = 1;
        int userId = 1;

        when(projectManagerDAO.assignTaskToTeamMember(taskId, userId)).thenReturn(true);

        boolean result = projectManagerService.assignTaskToTeamMember(taskId, userId);

        assertTrue(result);

        verify(projectManagerDAO, times(1)).assignTaskToTeamMember(taskId, userId);
    }

    @Test
    public void testAddProject() throws SQLException {
        Projects project = new Projects();

        when(projectManagerDAO.clientExists(project.getClient_id())).thenReturn(true);
        doNothing().when(projectManagerDAO).createProject(project);

        projectManagerService.addProject(project);

        verify(projectManagerDAO, times(1)).clientExists(project.getClient_id());
        verify(projectManagerDAO, times(1)).createProject(project);
    }

    @Test
    public void testGetAllProjectManagers() throws SQLException {
        Users user1 = new Users();
        Users user2 = new Users();
        List<Users> expectedUsers = Arrays.asList(user1, user2);

        when(projectManagerDAO.getAllProjectManagers()).thenReturn(expectedUsers);

        List<Users> users = projectManagerService.getAllProjectManagers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(expectedUsers, users);

        verify(projectManagerDAO, times(1)).getAllProjectManagers();
    }
}
