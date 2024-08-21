import React, { useState } from 'react';
import axios from 'axios';
import '../../styles/Home/ForgetPassword.css';

const ForgetPassword = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleForgetPassword = async (e) => {
        e.preventDefault();

        if (newPassword !== confirmPassword) {
            const errorMessage = 'New password and confirm password do not match.';
            setMessage(errorMessage);
            alert(errorMessage);
            console.log(errorMessage);
            return;
        }

        try {
            const response = await axios.get('http://localhost:4001/get_all_users');
            const users = response.data;

            const user = users.find(user => user.name === name && user.email === email);

            if (!user) {
                const errorMessage = 'Name or email is incorrect.';
                setMessage(errorMessage);
                alert(errorMessage);
                console.log(errorMessage);
                return;
            }

            const resetResponse = await axios.post('http://localhost:4001/add_forget_password/submit', {
                userid: user.userid,
                newPassword
            });

            const successMessage = 'Password has been successfully reset.';
            setMessage(successMessage);
            alert(successMessage);
            console.log({
                userid: user.userid,
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
        <div id="forget-password-main-container">
            <h1 id="forget-password-header">Forgot Password</h1>
            <form id="forget-password-form" onSubmit={handleForgetPassword}>
                <div className="forget-password-form-group-name" id="forget-password-group-name">
                    <label htmlFor="forget-name">Name</label>
                    <input 
                        type="text" 
                        id="forget-name" 
                        value={name} 
                        onChange={(e) => setName(e.target.value)} 
                        required 
                    />
                </div>
                <div className="forget-password-form-group-email" id="forget-password-group-email">
                    <label htmlFor="forget-email">Email</label>
                    <input 
                        type="email" 
                        id="forget-email" 
                        value={email} 
                        onChange={(e) => setEmail(e.target.value)} 
                        required 
                    />
                </div>
                <div className="forget-password-form-group-new-password" id="forget-password-group-new-password">
                    <label htmlFor="forget-new-password">New Password</label>
                    <input 
                        type="password" 
                        id="forget-new-password" 
                        value={newPassword} 
                        onChange={(e) => setNewPassword(e.target.value)} 
                        required 
                    />
                </div>
                <div className="forget-password-form-group-confirm-password" id="forget-password-group-confirm-password">
                    <label htmlFor="forget-confirm-password">Confirm Password</label>
                    <input 
                        type="password" 
                        id="forget-confirm-password" 
                        value={confirmPassword} 
                        onChange={(e) => setConfirmPassword(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit" id="forget-password-submit-button">Reset Password</button>
            </form>
            {message && <p id="forget-password-message">{message}</p>}
        </div>
    );
};

export default ForgetPassword;
