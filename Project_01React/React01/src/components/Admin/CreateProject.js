import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../styles/Admin/CreateProject.css'; 

const CreateProject = () => {
    const [projectName, setProjectName] = useState('');
    const [clientName, setClientName] = useState('');
    const [projectDescription, setProjectDescription] = useState('');
    const [clients, setClients] = useState([]);
    const [projectManagers, setProjectManagers] = useState([]);
    const [projectManagerId, setProjectManagerId] = useState(null); // Initialize as null

    useEffect(() => {
        const fetchClients = async () => {
            try {
                const response = await axios.get('http://localhost:4001/getallClients');
                setClients(response.data);
            } catch (error) {
                console.error('Error fetching clients:', error);
            }
        };

        const fetchProjectManagers = async () => {
            try {
                const response = await axios.get('http://localhost:4001/get_all_projectmanagers');
                setProjectManagers(response.data);
            } catch (error) {
                console.error('Error fetching project managers:', error);
            }
        };

        fetchClients();
        fetchProjectManagers();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:4001/ProjectCreated', {
                projectName,
                clientName,
                projectDescription,
                projectManagerId // Submit as integer
            });

            console.log('Project Created:', { projectName, clientName, projectDescription, projectManagerId });
            console.log('API Response:', response.data);
            alert('Project created successfully!');
            setProjectName('');
            setClientName('');
            setProjectDescription('');
            setProjectManagerId(null);
        } catch (error) {
            console.error('Error creating project:', error);
        }
    };

    return (
        <div className="create-project-container">
            <h2 className="create-project-title"><b>Create Project</b></h2>
            <form onSubmit={handleSubmit} className="create-project-form">
                <label htmlFor="client-select" className="create-project-label">Select Client:</label>
                <select
                    id="client-select"
                    name="client-select"
                    value={clientName}
                    onChange={(e) => setClientName(e.target.value)}
                    className="create-project-select"
                    required
                >
                    <option value="" disabled>Select Client</option>
                    {clients.map(client => (
                        <option key={client.clientId} value={client.clientName}>{client.clientName}</option>
                    ))}
                </select>

                <label htmlFor="project-manager-select" className="create-project-label">Select Project Manager:</label>
                <select
                    id="project-manager-select"
                    name="project-manager-select"
                    value={projectManagerId ?? ''} // Default to empty string if null
                    onChange={(e) => setProjectManagerId(parseInt(e.target.value))} // Convert to integer
                    className="create-project-select"
                    required
                >
                    <option value="" disabled>Select Project Manager</option>
                    {projectManagers.map(manager => (
                        <option key={manager.userid} value={manager.userid}>{manager.name}</option>
                    ))}
                </select>

                <label htmlFor="project-name-field" className="create-project-label">Project Name:</label>
                <input
                    type="text"
                    id="project-name-field"
                    name="project-name-field"
                    value={projectName}
                    onChange={(e) => setProjectName(e.target.value)}
                    className="create-project-input"
                    required
                />

                <label htmlFor="project-description-field" className="create-project-label">Project Description:</label>
                <input
                    type="text"
                    id="project-description-field"
                    name="project-description-field"
                    value={projectDescription}
                    onChange={(e) => setProjectDescription(e.target.value)}
                    className="create-project-input"
                    required
                />

                <button type="submit" className="create-project-button">Create Project</button>
            </form>
        </div>
    );
};

export default CreateProject;
