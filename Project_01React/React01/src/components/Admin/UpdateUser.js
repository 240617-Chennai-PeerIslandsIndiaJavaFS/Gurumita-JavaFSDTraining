import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../styles/Admin/UpdateUser.css'; 

const UpdateUser = () => {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null); 
    const [newName, setNewName] = useState('');
    const [newEmail, setNewEmail] = useState('');
    const [newPassword, setNewPassword] = useState('');

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

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:4001/update_user/submit', {
                userId: selectedUser, 
                name: newName,
                email: newEmail,
                password: newPassword,
            });
    
            console.log('User Updated:', { selectedUser, newName, newEmail, newPassword });
            console.log('API Response:', response.data);
            alert('User updated successfully!');
            setSelectedUser(null); 
            setNewName('');
            setNewEmail('');
            setNewPassword('');
        } catch (error) {
            console.error('Error updating user:', error);
        }
    };

    return (
        <div className="update-user-container">
            <h2 className="update-user-title"><b>Update User</b></h2>
            <form onSubmit={handleSubmit} className="update-user-form">
                <label htmlFor="user-selection" className="update-user-label">Select User:</label>
                <select
                    id="user-selection"
                    name="user-selection"
                    value={selectedUser || ''}
                    onChange={(e) => setSelectedUser(parseInt(e.target.value, 10))} 
                    className="update-user-select"
                    required
                >
                    <option value="" disabled>Select User</option>
                    {users.map(user => (
                        <option key={user.userid} value={user.userid}>{user.name}</option>
                    ))}
                </select>
                <label htmlFor="user-name" className="update-user-label">New Name:</label>
                <input
                    type="text"
                    id="user-name"
                    name="user-name"
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                    className="update-user-input"
                    required
                />
                <label htmlFor="user-email" className="update-user-label">New Email:</label>
                <input
                    type="email"
                    id="user-email"
                    name="user-email"
                    value={newEmail}
                    onChange={(e) => setNewEmail(e.target.value)}
                    className="update-user-input"
                    required
                />
                <label htmlFor="user-password" className="update-user-label">New Password:</label>
                <input
                    type="password"
                    id="user-password"
                    name="user-password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    className="update-user-input"
                    required
                />
                <button type="submit" className="update-user-button">Update User</button>
            </form>
        </div>
    );
};

export default UpdateUser;
