import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import '../../styles/ProjectManager/ResetPassword.css';

const ResetPassword = () => {
    const location = useLocation();
    const { username } = location.state || {};
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleResetPassword = async (e) => {
        e.preventDefault();

        if (newPassword !== confirmPassword) {
            const errorMessage = 'New password and confirm password do not match.';
            setMessage(errorMessage);
            alert(errorMessage);
            console.log(errorMessage);
            return;
        }

        try {
            const response = await axios.get('http://localhost:4001/get_all_projectmanagers');
            const projectManagers = response.data;

            const projectManager = projectManagers.find(pm => pm.name === username && pm.password === oldPassword);

            if (!projectManager) {
                const errorMessage = 'Old password is incorrect.';
                setMessage(errorMessage);
                alert(errorMessage);
                console.log(errorMessage);
                return;
            }

            const resetResponse = await axios.post('http://localhost:4001/add_reset_password/submit', {
                userid: projectManager.userid,
                newPassword
            });

            const successMessage = 'Password has been successfully reset.';
            setMessage(successMessage);
            alert(successMessage);
            console.log({
                userid: projectManager.userid,
                newPassword
            });
            console.log('Response Data:', resetResponse.data);
        } catch (error) {
            const errorMessage = 'An error occurred while resetting the password.';
            setMessage(errorMessage);
            alert(errorMessage);
            console.log(errorMessage, error);
        }
    };

    return (
        <div id="reset-container" className="reset-container">
            <h1 id="reset-title">Welcome to the Project, {username}</h1>
            <form id="reset-password-form" onSubmit={handleResetPassword}>
                <div className="reset-form-group">
                    <label htmlFor="reset-old-password">Old Password</label>
                    <input 
                        type="password" 
                        id="reset-old-password" 
                        className="reset-input" 
                        value={oldPassword} 
                        onChange={(e) => setOldPassword(e.target.value)} 
                        required 
                    />
                </div>
                <div className="reset-form-group">
                    <label htmlFor="reset-new-password">New Password</label>
                    <input 
                        type="password" 
                        id="reset-new-password" 
                        className="reset-input" 
                        value={newPassword} 
                        onChange={(e) => setNewPassword(e.target.value)} 
                        required 
                    />
                </div>
                <div className="reset-form-group">
                    <label htmlFor="reset-confirm-password">Confirm Password</label>
                    <input 
                        type="password" 
                        id="reset-confirm-password" 
                        className="reset-input" 
                        value={confirmPassword} 
                        onChange={(e) => setConfirmPassword(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit" id="reset-button" className="reset-button">Reset Password</button>
            </form>
            {message && <p className="reset-message">{message}</p>}
        </div>
    );
};

export default ResetPassword;
