import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../styles/Admin/AssignAccessLevels.css'; 

const AssignAccessLevels = () => {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState('');
    const [existingRole, setExistingRole] = useState('');
    const [newRole, setNewRole] = useState('');

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get('http://localhost:4001/getallusers');
                setUsers(response.data);
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        };

        fetchUsers();
    }, []);

    const handleUserChange = (e) => {
        const userId = e.target.value;
        setSelectedUser(userId);
        const user = users.find(user => user.userid === parseInt(userId));
        setExistingRole(user ? user.role : '');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const userIdNumber = parseInt(selectedUser); 
        try {
            const response = await axios.post('http://localhost:4001/Access_level', {
                userId: userIdNumber,
                newRole: newRole,
            });
            console.log('Access Levels Assigned:', { userId: userIdNumber, existingRole, newRole });
            console.log('API Response:', response.data);
            alert('Access level assigned successfully!');
            setSelectedUser('');
            setExistingRole('');
            setNewRole('');
        } catch (error) {
            console.error('Error assigning access levels:', error);
        }
    };

    return (
        <div className="assign-access-container">
            <h2 className="assign-access-title"><b>Assign Access Levels</b></h2>
            <form onSubmit={handleSubmit} className="assign-access-form">
                <label htmlFor="user-select" className="assign-access-label">Select User:</label>
                <select
                    id="user-select"
                    name="user-select"
                    value={selectedUser}
                    onChange={handleUserChange}
                    className="assign-access-select"
                    required
                >
                    <option value="" disabled>Select User</option>
                    {users.map(user => (
                        <option key={user.userid} value={user.userid}>{user.name}</option>
                    ))}
                </select>

                <label htmlFor="existing-role" className="assign-access-label">Existing Role:</label>
                <input
                    type="text"
                    id="existing-role"
                    name="existing-role"
                    value={existingRole}
                    className="assign-access-input"
                    readOnly
                />

                <label htmlFor="new-role" className="assign-access-label">New Role:</label>
                <select
                    id="new-role"
                    name="new-role"
                    value={newRole}
                    onChange={(e) => setNewRole(e.target.value)}
                    className="assign-access-select"
                    required
                >
                    <option value="" disabled>Select New Role</option>
                    <option value="admin">Admin</option>
                    <option value="project_manager">Project Manager</option>
                    <option value="team_member">Team Member</option>
                </select>

                <button type="submit" className="assign-access-button">Assign Role</button>
            </form>
        </div>
    );
};

export default AssignAccessLevels;
