import React, { useState, useEffect } from 'react';
import '../../styles/ProjectManager/TrackUserActivityProject.css';

const TrackUserActivityPage = () => {
    const [teamMembers, setTeamMembers] = useState([]);
    const [selectedUser, setSelectedUser] = useState('');
    const [userActivities, setUserActivities] = useState([]);

    useEffect(() => {
        const fetchTeamMembers = async () => {
            try {
                const response = await fetch('http://localhost:4001/getall_teammembers');
                const data = await response.json();
                setTeamMembers(data);
            } catch (error) {
                console.error('Error fetching team members:', error);
            }
        };

        fetchTeamMembers();
    }, []);

    const handleTrack = async () => {
        if (selectedUser) {
            try {
                const response = await fetch(`http://localhost:4001/getuseractivitybyteammember?name=${selectedUser}`);
                const data = await response.json();
                setUserActivities(data);
            } catch (error) {
                console.error('Error fetching user activities:', error);
            }
        }
    };

    return (
        <div id="track-activity-container">
            <h1 id="track-activity-title">Track User Activity</h1>
            <div id="track-form-group">
                <label htmlFor="userSelect" id="track-label">Select User</label>
                <select
                    id="userSelect"
                    value={selectedUser}
                    onChange={(e) => setSelectedUser(e.target.value)}
                    required
                >
                    <option value="">Select a user</option>
                    {teamMembers.map(member => (
                        <option key={member.userid} value={member.name}>
                            {member.name}
                        </option>
                    ))}
                </select>
                <button id="trackButton" onClick={handleTrack}>Track</button>
            </div>
            <div id="activityContainer">
                {userActivities.length > 0 ? (
                    userActivities.map((activity, index) => (
                        <div key={index} className="track-activity-card">
                            <h3 className="card-title">{activity.projectName}</h3>
                            <p><strong>Description:</strong> {activity.projectDescription}</p>
                            <p><strong>Task:</strong> {activity.taskName}</p>
                            <p><strong>Task Description:</strong> {activity.taskDescription}</p>
                            <p><strong>Status:</strong> {activity.taskStatus}</p>
                            <p><strong>Start Date:</strong> {activity.startDate}</p>
                            <p><strong>Due Date:</strong> {activity.dueDate}</p>
                        </div>
                    ))
                ) : (
                    <p>Select User to display activity</p>
                )}
            </div>
        </div>
    );
};

export default TrackUserActivityPage;
