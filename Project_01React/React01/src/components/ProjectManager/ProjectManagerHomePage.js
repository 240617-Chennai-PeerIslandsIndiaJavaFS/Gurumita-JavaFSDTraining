import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../../styles/ProjectManager/ProjectManagerHomePage.css';

const ProjectManagerHomePage = () => {
    const location = useLocation();
    const { username } = location.state || {};
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await fetch('http://localhost:4001/get_all_users');
                const data = await response.json();
                
                const foundUser = data.find(user => user.name === username);
                
                console.log('Project Manager:', foundUser);
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        };

        fetchUsers();
    }, []);

    const handleLogout = () => {
        const confirmLogout = window.confirm("Are you sure you want to logout?");
        if (confirmLogout) {
            navigate('/login');
        }
    };

    return (
        <div className="pm-home-container">
            <h1 className="pm-home-title">Welcome Project Manager, {username}</h1>
            <ul className="pm-menu">
                <li className="pm-menu-item">
                    <button 
                        className="pm-menu-button" 
                        onClick={() => navigate('/reset-password', { state: { username } })}>
                        Reset Password
                    </button>
                </li>
                <li className="pm-menu-item">
                    <button 
                        className="pm-menu-button" 
                        onClick={() => navigate('/add-team-members', { state: { username } })}>
                        Add Team Members to Projects
                    </button>
                </li>
                <li className="pm-menu-item">
                    <button 
                        className="pm-menu-button" 
                        onClick={() => navigate('/assign-task', { state: { username } })}>
                        Assign Tasks to Team Members
                    </button>
                </li>
                <li className="pm-menu-item">
                    <button 
                        className="pm-menu-button" 
                        onClick={() => navigate('/track-user-activity')}>
                        Track User Activity
                    </button>
                </li>
                <li className="pm-menu-item">
                    <button 
                        id="logoutButton" 
                        className="pm-menu-button" 
                        onClick={handleLogout}>
                        Logout
                    </button>
                </li>
            </ul>
        </div>
    );
};

export default ProjectManagerHomePage;
