package TestCases;

import org.example.DAO.TeamMemberDAO;
import org.example.Models.Clients;
import org.example.Models.Projects;
import org.example.Models.Tasks;
import org.example.Service.TeamMemberService;
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

public class TeamMemberServiceTest {

    @Mock
    private TeamMemberDAO teamMemberDAO;

    @InjectMocks
    private TeamMemberService teamMemberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTasksByAssignedUserId() throws SQLException {
        int userId = 1;
        Tasks task1 = new Tasks();
        Tasks task2 = new Tasks();
        List<Tasks> expectedTasks = Arrays.asList(task1, task2);
        when(teamMemberDAO.getTasksByAssignedUserId(userId)).thenReturn(expectedTasks);
        List<Tasks> tasks = teamMemberService.getTasksByAssignedUserId(userId);
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals(expectedTasks, tasks);
        verify(teamMemberDAO, times(1)).getTasksByAssignedUserId(userId);
    }

    @Test
    public void testUpdateTaskStatus() throws SQLException {
        int taskId = 1;
        String status = "Completed";
        when(teamMemberDAO.updateTaskStatus(taskId, status)).thenReturn(true);
        boolean result = teamMemberService.updateTaskStatus(taskId, status);
        assertTrue(result);
        verify(teamMemberDAO, times(1)).updateTaskStatus(taskId, status);
    }

    @Test
    public void testGetProjectsByUserId() throws SQLException {
        int userId = 1;
        Projects project1 = new Projects();
        Projects project2 = new Projects();
        List<Projects> expectedProjects = Arrays.asList(project1, project2);

        when(teamMemberDAO.getProjectsByUserId(userId)).thenReturn(expectedProjects);

        List<Projects> projects = teamMemberService.getProjectsByUserId(userId);

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertEquals(expectedProjects, projects);

        verify(teamMemberDAO, times(1)).getProjectsByUserId(userId);
    }

    @Test
    public void testGetClientsByUserId() throws SQLException {
        int userId = 1;
        Clients client1 = new Clients();
        Clients client2 = new Clients();
        List<Clients> expectedClients = Arrays.asList(client1, client2);

        when(teamMemberDAO.getClientsByUserId(userId)).thenReturn(expectedClients);

        List<Clients> clients = teamMemberService.getClientsByUserId(userId);

        assertNotNull(clients);
        assertEquals(2, clients.size());
        assertEquals(expectedClients, clients);

        verify(teamMemberDAO, times(1)).getClientsByUserId(userId);
    }
}
