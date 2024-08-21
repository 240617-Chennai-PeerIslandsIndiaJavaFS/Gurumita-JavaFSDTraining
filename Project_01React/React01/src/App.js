import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './components/Home/LandingPage';
import LoginPage from './components/Home/LoginPage';
import NotFoundPage from './components/Home/NotFoundPage';
import ProjectManagerHomePage from './components/ProjectManager/ProjectManagerHomePage';
import AddTeamMembers from './components/ProjectManager/AddTeamMembers';
import ResetPassword from './components/ProjectManager/ResetPassword';
import ForgotPassword from './components/Home/ForgetPassword';
import AssignTask from './components/ProjectManager/AssignTask';
import TrackUserActivityPage from './components/ProjectManager/TrackUserActivityPage';
import AdminPage from './components/Admin/AdminPage';
import Home from './components/Admin/Home';
import CreateUser from './components/Admin/CreateUser';
import UpdateUser from './components/Admin/UpdateUser';
import DeactivateUser from './components/Admin/DeactivateUser';
import CreateClient from './components/Admin/CreateClient';
import CreateProject from './components/Admin/CreateProject';
import AssignAccessLevels from './components/Admin/AssignAccessLevels';
import TeamMember from './components/TeamMember/TeamMember'; 

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/project-manager-home" element={<ProjectManagerHomePage />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/add-team-members" element={<AddTeamMembers />} />
        <Route path="/assign-task" element={<AssignTask />} />
        <Route path="/track-user-activity" element={<TrackUserActivityPage />} />
        <Route path="/admin" element={<AdminPage />}>
          <Route path="home" element={<Home />} />
          <Route path="create-user" element={<CreateUser />} />
          <Route path="update-user" element={<UpdateUser />} />
          <Route path="deactivate-user" element={<DeactivateUser />} />
          <Route path="create-client" element={<CreateClient />} />
          <Route path="create-project" element={<CreateProject />} />
          <Route path="assign-access-levels" element={<AssignAccessLevels />} />
          <Route path="track-user-activity" element={<TrackUserActivityPage />} />
        </Route>
        <Route path="/teammember" element={<TeamMember />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </Router>
  );
}

export default App;
