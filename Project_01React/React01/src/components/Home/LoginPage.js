import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/Home/LoginPage.css';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();

        try {
            const response = await fetch('http://localhost:4001/get_all_users');
            const users = await response.json();

            if (!Array.isArray(users)) {
                throw new Error('API response is not an array');
            }

            const user = users.find(user => user.email === email && user.password === password);

            if (user) {
                console.log('Logged in user:', user);

                switch (user.role) {
                    case 'Admin':
                        navigate('/admin/home', { state: { username: user.name, userId: user.id } });
                        break;
                    case 'Project Manager':
                        navigate('/project-manager-home', { state: { username: user.name } });
                        break;
                    case 'Team Member':
                        navigate('/teammember', { state: { username: user.name, userId: user.id } });
                        break;
                    default:
                        alert('Role not recognized.');
                        break;
                }
            } else {
                alert('Invalid email or password.');
            }
        } catch (error) {
            console.error('Error fetching users:', error);
            alert('Error fetching users. Please try again later.');
        }
    };

    return (
        <div id="loginPageContainer">
            <div id="loginImageContainer"></div>
            <div id="loginFormContainer">
                <div id="welcomeMessage">
                    <h1>Welcome to TMS</h1>
                    <p>(Task Management System)</p>
                </div>
                <form id="loginForm" onSubmit={handleLogin}>
                    <input
                        type="email"
                        id="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        id="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit">Login</button>
                </form>
                <button onClick={() => navigate('/forgot-password')} id="forgotPasswordLink">Forgot Password</button>
            </div>
        </div>
    );
};

export default LoginPage;
