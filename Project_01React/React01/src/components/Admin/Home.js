import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import '../../styles/Admin/Home.css';

const Home = () => {
    const location = useLocation();
    const { username } = location.state || {};
    const [userDetails, setUserDetails] = useState(null);
    const [data, setData] = useState(null);
    const [dataType, setDataType] = useState('actions');
    const [searchQuery, setSearchQuery] = useState('');
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [filteredClients, setFilteredClients] = useState([]);
    const [filteredProjects, setFilteredProjects] = useState([]);

    useEffect(() => {
        const fetchUserDetails = async () => {
            try {
                const response = await fetch(`http://localhost:4001/get_adminby_name?name=${username}`);
                const data = await response.json();
                console.log('Admin Details:', data);
                setUserDetails(data[0]);
            } catch (error) {
                console.error('Error fetching user details:', error);
            }
        };

        if (username) {
            fetchUserDetails();
        }
    }, [username]);

    const fetchData = async (apiUrl, type) => {
        try {
            const response = await fetch(apiUrl);
            const data = await response.json();
            console.log(`${type} data:`, data);
            setData(data);
            setDataType(type);
            if (type === 'users') {
                setFilteredUsers(data); 
            } else if (type === 'clients') {
                setFilteredClients(data); 
            } else if (type === 'projects') {
                setFilteredProjects(data); 
            }
        } catch (error) {
            console.error(`Error fetching ${type}:`, error);
        }
    };

    const handleSearch = (e) => {
        const query = e.target.value.toLowerCase();
        setSearchQuery(query);
        if (dataType === 'users') {
            setFilteredUsers(
                data.filter(user =>
                    user.name.toLowerCase().includes(query)
                )
            );
        } else if (dataType === 'clients') {
            setFilteredClients(
                data.filter(client =>
                    client.clientName.toLowerCase().includes(query)
                )
            );
        } else if (dataType === 'projects') {
            setFilteredProjects(
                data.filter(project =>
                    project.project_name.toLowerCase().includes(query)
                )
            );
        }
    };

    const renderData = () => {
        if (dataType === 'actions') {
            return (
                <div className="default-text">
                    <p>
                        As an admin user, you will have the capability to manage user accounts comprehensively. 
                        You can create, update, and deactivate accounts for both project managers and team members. 
                        Additionally, you will be able to assign and adjust access levels to ensure appropriate 
                        permissions for different roles. You will also have access to reporting tools that enable 
                        you to track user activity and monitor task completion effectively.
                    </p>
                </div>
            );
        }

        if (!data) return null;

        if (dataType === 'users') {
            return (
                <>
                    <input
                        type="text"
                        placeholder="Search by name"
                        value={searchQuery}
                        onChange={handleSearch}
                        className="search-box"
                    />
                    <table>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredUsers.map((user) => (
                                <tr key={user.userid}>
                                    <td>{user.name}</td>
                                    <td>{user.email}</td>
                                    <td>{user.role}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </>
            );
        } else if (dataType === 'clients') {
            return (
                <>
                    <input
                        type="text"
                        placeholder="Search by client name"
                        value={searchQuery}
                        onChange={handleSearch}
                        className="search-box"
                    />
                    <table>
                        <thead>
                            <tr>
                                <th>Client Name</th>
                                <th>Client Email</th>
                                <th>Client Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredClients.map((client) => (
                                <tr key={client.clientId}>
                                    <td>{client.clientName}</td>
                                    <td>{client.clientEmail}</td>
                                    <td>{client.clientDescription}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </>
            );
        } else if (dataType === 'projects') {
            return (
                <>
                    <input
                        type="text"
                        placeholder="Search by project name"
                        value={searchQuery}
                        onChange={handleSearch}
                        className="search-box"
                    />
                    <table>
                        <thead>
                            <tr>
                                <th>Project Name</th>
                                <th>Project Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredProjects.map((project) => (
                                <tr key={project.project_id}>
                                    <td>{project.project_name}</td>
                                    <td>{project.project_description}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </>
            );
        }
    };

    return (
        <div className="home-container">
            <h2>Welcome Admin</h2>
            {userDetails ? (
                <p>Welcome, {userDetails.name}</p>
            ) : (
                <p>Loading user details...</p>
            )}
            <div className="actions-container">
                <button onClick={() => setDataType('actions')}>Actions</button>
                <button onClick={() => fetchData('http://localhost:4001/getallusers', 'users')}>View all users</button>
                <button onClick={() => fetchData('http://localhost:4001/getallClients', 'clients')}>View all clients</button>
                <button onClick={() => fetchData('http://localhost:4001/getall_projects', 'projects')}>View all projects</button>
            </div>
            <div className="data-container">
                <h3><u>{`Viewing ${dataType}`}</u></h3>
                {renderData()}
            </div>
        </div>
    );
};

export default Home;

