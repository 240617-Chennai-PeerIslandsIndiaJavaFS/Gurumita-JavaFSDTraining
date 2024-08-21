import React, { useEffect, useState } from 'react';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import { useLocation, useNavigate } from 'react-router-dom';
import '../../styles/TeamMember/TeamMember.css';

const TeamMember = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const username = location.state?.username || 'Unknown User';
    const [milestones, setMilestones] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);

    const fetchTeamMemberData = async () => {
        try {
            const response = await fetch(`http://localhost:4001/get_teammember_by_name?name=${username}`);
            const data = await response.json();
            console.log('Team Member Data:', data);
        } catch (error) {
            console.error('Error fetching team member data:', error);
        }
    };

    const fetchMilestones = async () => {
        try {
            const response = await fetch('http://localhost:4001/getall_milestones');
            const data = await response.json();
            setMilestones(data);
        } catch (error) {
            console.error('Error fetching milestones:', error);
        }
    };

    const fetchTasks = async () => {
        try {
            const response = await fetch(`http://localhost:4001/get_tasks_byusername?name=${username}`);
            const data = await response.json();
            setTasks(data);
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
    };

    useEffect(() => {
        if (username && username !== 'Unknown User') {
            fetchTeamMemberData();
            fetchMilestones();
            fetchTasks();
        } else {
            console.warn('Username not provided or invalid.');
        }
    }, [username]);

    const combinedData = milestones.map(milestone => {
        const milestoneTasks = tasks.filter(task => task.milestone_id === milestone.milestone_id);
        return {
            ...milestone,
            tasks: milestoneTasks
        };
    });

    const handleDragEnd = async (result) => {
        const { destination, source, draggableId } = result;

        if (!destination) return;

        const taskId = draggableId.toString();
        const sourceMilestone = milestones.find(milestone => milestone.milestone_id === combinedData[source.droppableId].milestone_id);
        const destinationMilestone = milestones.find(milestone => milestone.milestone_id === combinedData[destination.droppableId].milestone_id);
        const draggedTask = tasks.find(task => task.task_id.toString() === taskId);

        if (!draggedTask) {
            console.error(`Task with ID ${taskId} not found`);
            return;
        }

        const updatedTask = {
            ...draggedTask,
            milestone_id: destinationMilestone.milestone_id
        };

        try {
            const response = await fetch('http://localhost:4001/update_task/submit', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedTask)
            });
            const result = await response.json();
            console.log('API Response:', result);
            console.log('Updated Task:', updatedTask);
        } catch (error) {
            console.error('Error updating task:', error);
        }

        const updatedTasks = tasks.map(task =>
            task.task_id.toString() === taskId ? updatedTask : task
        );
        setTasks(updatedTasks);

        const updatedMilestones = combinedData.map(milestone => {
            if (milestone.milestone_id === sourceMilestone.milestone_id) {
                return {
                    ...milestone,
                    tasks: milestone.tasks.filter(task => task.task_id.toString() !== taskId)
                };
            } else if (milestone.milestone_id === destinationMilestone.milestone_id) {
                return {
                    ...milestone,
                    tasks: [...milestone.tasks, updatedTask]
                };
            }
            return milestone;
        });

        setMilestones(updatedMilestones);
    };

    const handleLogout = () => {
        const confirmLogout = window.confirm("Are you sure you want to logout?");
        if (confirmLogout) {
            navigate('/login');
        }
    };

    return (
        <DragDropContext onDragEnd={handleDragEnd}>
            <Droppable droppableId="milestones" direction="horizontal">
                {(provided) => (
                    <div
                        className="milestones-container"
                        ref={provided.innerRef}
                        {...provided.droppableProps}
                    >
                        <h1 className="welcome-message">Team Member Page</h1>
                        <p className="welcome-text">Welcome, {username}!</p>
                        <button className="team-logout-button" onClick={handleLogout}>Logout</button>

                        {combinedData.map((milestone, index) => (
                            <Droppable key={milestone.milestone_id.toString()} droppableId={index.toString()} direction="vertical">
                                {(provided) => (
                                    <div
                                        className="milestone"
                                        ref={provided.innerRef}
                                        {...provided.droppableProps}
                                    >
                                        <h3 className="milestone-title">{milestone.milestone_name}</h3>
                                        <p className="milestone-description">{milestone.milestone_description}</p>
                                        <div className="tasks">
                                            {milestone.tasks.length > 0 ? (
                                                <ul>
                                                    {milestone.tasks.map((task, taskIndex) => (
                                                        <Draggable key={task.task_id.toString()} draggableId={task.task_id.toString()} index={taskIndex}>
                                                            {(provided) => (
                                                                <li
                                                                    className="task-item"
                                                                    ref={provided.innerRef}
                                                                    {...provided.draggableProps}
                                                                    {...provided.dragHandleProps}
                                                                >
                                                                    <button 
                                                                        onClick={() => setSelectedTask(task)} 
                                                                        className="task-button"
                                                                    >
                                                                        {task.task_name}
                                                                    </button>
                                                                </li>
                                                            )}
                                                        </Draggable>
                                                    ))}
                                                </ul>
                                            ) : (
                                                <p>No tasks for this milestone.</p>
                                            )}
                                        </div>
                                    </div>
                                )}
                            </Droppable>
                        ))}

                        {provided.placeholder}
                    </div>
                )}
            </Droppable>

            {selectedTask && (
                <div className="task-details-overlay" onClick={() => setSelectedTask(null)}>
                    <div className="task-details" onClick={(e) => e.stopPropagation()}>
                        <button className="close-button" onClick={() => setSelectedTask(null)}>×</button>
                        <h2>Task Details</h2>
                        <div className="task-detail">
                            <strong>Task Name:</strong> {selectedTask.task_name}
                        </div>
                        <div className="task-detail">
                            <strong>Description:</strong> {selectedTask.task_description}
                        </div>
                        <div className="task-detail">
                            <strong>Status:</strong> {selectedTask.task_status}
                        </div>
                        <div className="task-detail">
                            <strong>Start Date:</strong> {new Date(selectedTask.start_date).toLocaleDateString()}
                        </div>
                        <div className="task-detail">
                            <strong>Due Date:</strong> {new Date(selectedTask.due_date).toLocaleDateString()}
                        </div>
                    </div>
                </div>
            )}
        </DragDropContext>
    );
};

export default TeamMember;