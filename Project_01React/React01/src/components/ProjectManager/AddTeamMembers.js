import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import '../../styles/ProjectManager/AddTeamMembers.css';

const AddTeamMembers = () => {
    const location = useLocation();
    const { username } = location.state || {};
    const [teamMembers, setTeamMembers] = useState([]);
    const [projects, setProjects] = useState([]);
    const [selectedTeamMember, setSelectedTeamMember] = useState('');
    const [selectedProject, setSelectedProject] = useState('');

    useEffect(() => {
        const fetchTeamMembers = async () => {
            try {
                const response = await fetch(`http://localhost:4001/getall_teammembers?name=${username}`);
                const data = await response.json();
                setTeamMembers(data);
            } catch (error) {
                console.error('Error fetching team members:', error);
            }
        };

        const fetchProjects = async () => {
            try {
                const response = await fetch(`http://localhost:4001/get_projectsbyname?name=${username}`);
                const data = await response.json();
                setProjects(data);
            } catch (error) {
                console.error('Error fetching projects:', error);
            }
        };

        fetchTeamMembers();
        fetchProjects();
    }, [username]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const selectedMember = teamMembers.find(member => member.userid === parseInt(selectedTeamMember));
        const selectedProjectObj = projects.find(project => project.project_id === parseInt(selectedProject));

        console.log('Selected Team Member ID:', selectedTeamMember);
        console.log('Selected Project ID:', selectedProject);
        console.log('Selected Team Member Name:', selectedMember?.name);
        console.log('Selected Project Name:', selectedProjectObj?.project_name);

        if (selectedMember && selectedProjectObj) {
            alert(`Team Member ${selectedMember.name} has been added to ${selectedProjectObj.project_name}!`);
            try {
                const response = await fetch('http://localhost:4001/add_team_members_to_project/submit', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        project_name: selectedProjectObj.project_name,
                        team_member_name: selectedMember.name
                    }),
                });
                const result = await response.json();
                console.log('Response data:', result);
            } catch (error) {
                console.error('Error adding team member to project:', error);
            }
        } else {
            alert('Please select both a team member and a project.');
        }
    };

    return (
        <div className="atm-container">
            <h1 className="atm-title">Add Team Members to Project</h1>
            <form id="addTeamMemberForm" className="atm-form" onSubmit={handleSubmit}>
                <div className="atm-form-group">
                    <label className="atm-label" htmlFor="project">Select Project</label>
                    <select
                        id="project"
                        name="project"
                        className="atm-select"
                        value={selectedProject}
                        onChange={(e) => setSelectedProject(e.target.value)}
                        required
                    >
                        <option value="">Select a project</option>
                        {projects.map(project => (
                            <option key={project.project_id} value={project.project_id}>
                                {project.project_name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="atm-form-group">
                    <label className="atm-label" htmlFor="teamMember">Select Team Member</label>
                    <select
                        id="teamMember"
                        name="teamMember"
                        className="atm-select"
                        value={selectedTeamMember}
                        onChange={(e) => setSelectedTeamMember(e.target.value)}
                        required
                    >
                        <option value="">Select a team member</option>
                        {teamMembers.map(member => (
                            <option key={member.userid} value={member.userid}>
                                {member.name}
                            </option>
                        ))}
                    </select>
                </div>
                <button type="submit" className="atm-submit-button">Add Team Member</button>
            </form>
        </div>
    );
};

export default AddTeamMembers;
