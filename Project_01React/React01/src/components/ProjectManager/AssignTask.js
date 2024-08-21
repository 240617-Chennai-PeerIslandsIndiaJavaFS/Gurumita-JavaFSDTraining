import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import '../../styles/ProjectManager/AssignTask.css';

const AssignTask = () => {
    const location = useLocation();
    const { username } = location.state || {};

    const [projects, setProjects] = useState([]);
    const [teamMembers, setTeamMembers] = useState([]);
    const [selectedProject, setSelectedProject] = useState('');
    const [formData, setFormData] = useState({
        project: '',
        teamMember: '',
        taskName: '',
        taskDescription: '',
        taskStatus: 'To Do',
        startDate: '',
        dueDate: ''
    });

    useEffect(() => {
        axios.get(`http://localhost:4001/get_projectsbyname?name=${username}`)
            .then(response => {
                setProjects(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the projects!', error);
            });
    }, [username]);

    const handleProjectChange = (e) => {
        const project = e.target.value;
        setSelectedProject(project);
        setFormData({ ...formData, project });
        
        axios.get(`http://localhost:4001/assigntask_get%20temmembers/project_name?project_name=${project}`)
            .then(response => {
                setTeamMembers(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the team members!', error);
            });
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:4001/assign_task/submit', formData)
            .then(response => {
                console.log('Response:', response.data);
                console.log('Submitted Data:', formData);
                alert('Task assigned successfully!');
            })
            .catch(error => {
                console.error('There was an error assigning the task!', error);
            });
    };

    return (
        <div id="assign-task-container" className="assign-task-container">
            <div id="assign-task-content" className="assign-task-content">
                <h1 id="assign-task-title" className="assign-task-title">Assign Task to Team Member</h1>
                <form id="assign-task-form" className="assign-task-form" onSubmit={handleSubmit}>
                    <div id="project-group" className="form-group">
                        <label htmlFor="project" id="project-label" className="form-label">Select Project</label>
                        <select
                            id="project"
                            name="project"
                            className="form-select"
                            value={formData.project}
                            onChange={handleProjectChange}
                            required
                        >
                            <option value="">Select a project</option>
                            {projects.map(project => (
                                <option key={project.project_id} value={project.project_name}>
                                    {project.project_name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div id="team-member-group" className="form-group">
                        <label htmlFor="teamMember" id="team-member-label" className="form-label">Select Team Member</label>
                        <select
                            id="teamMember"
                            name="teamMember"
                            className="form-select"
                            value={formData.teamMember}
                            onChange={handleInputChange}
                            required
                        >
                            <option value="">Select a team member</option>
                            {teamMembers.map(member => (
                                <option key={member.userid} value={member.name}>
                                    {member.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div id="task-name-group" className="form-group">
                        <label htmlFor="taskName" id="task-name-label" className="form-label">Task Name</label>
                        <input
                            type="text"
                            id="taskName"
                            name="taskName"
                            className="form-input"
                            value={formData.taskName}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div id="task-description-group" className="form-group">
                        <label htmlFor="taskDescription" id="task-description-label" className="form-label">Task Description</label>
                        <textarea
                            id="taskDescription"
                            name="taskDescription"
                            className="form-textarea"
                            value={formData.taskDescription}
                            onChange={handleInputChange}
                            required
                        ></textarea>
                    </div>
                    <div id="task-status-group" className="form-group">
                        <label htmlFor="taskStatus" id="task-status-label" className="form-label">Task Status</label>
                        <select
                            id="taskStatus"
                            name="taskStatus"
                            className="form-select"
                            value={formData.taskStatus}
                            onChange={handleInputChange}
                            required
                        >
                            <option value="To Do">To Do</option>
                            <option value="In Progress">In Progress</option>
                            <option value="In Review">In Review</option>
                            <option value="Done">Done</option>
                        </select>
                    </div>
                    <div id="start-date-group" className="form-group">
                        <label htmlFor="startDate" id="start-date-label" className="form-label">Start Date</label>
                        <input
                            type="date"
                            id="startDate"
                            name="startDate"
                            className="form-input"
                            value={formData.startDate}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div id="due-date-group" className="form-group">
                        <label htmlFor="dueDate" id="due-date-label" className="form-label">Due Date</label>
                        <input
                            type="date"
                            id="dueDate"
                            name="dueDate"
                            className="form-input"
                            value={formData.dueDate}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <button type="submit" id="assign-task-button" className="assign-task-button">Assign Task</button>
                </form>
            </div>
        </div>
    );
};

export default AssignTask;
