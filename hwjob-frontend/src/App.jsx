import { Route, Routes } from "react-router-dom";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import Login from "./pages/auth/Login";
import UserLayout from "./components/layouts/user/UserLayout";
import Home from "./pages/user/Home";
import Register from "./pages/auth/Register";
import PrivateRoute from "./components/common/PrivateRoute";
import Dashboard from "./pages/admin/Dashboard";
import AdminLayout from "./components/layouts/admin/AdminLayout";

import MyProfile from "./pages/user/profile/MyProfile";
import Overview from "./pages/user/profile/Overview";
import PersonalInfo from "./pages/user/profile/PersonalInfo";
import EditSummary from "./pages/user/profile/EditSummary";
import SecurityInfo from "./pages/user/profile/SecurityInfo";

import CategoryManagement from "./pages/admin/CategoryManagement";
import BadgeManagement from "./pages/admin/BadgeManagement";
import UserManagement from "./pages/admin/UserManagement";
import AddJobPost from "./pages/user/recruiter/AddJobPost";
import JobPostList from "./pages/user/JobPostList";
import JobPostDetail from "./pages/user/JobPostDetail";
import { ROLES } from "./constants/role";

import CandidateJobManagement from "./pages/user/candidate/CandidateJobManagement";

function App() {
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register />} />

      <Route path="/" element={<UserLayout />}>
        <Route index element={<Home />} />
        <Route path="job-post" element={<JobPostList />} />

        <Route
          element={
            <PrivateRoute allowedRoles={[ROLES.RECRUITER, ROLES.CANDIDATE]} />
          }
        >
          <Route path="job-post/:id" element={<JobPostDetail />} />
          <Route path="my-profile" element={<MyProfile />}>
            <Route index element={<Overview />} />
            <Route path="personal" element={<PersonalInfo />} />
            <Route path="security" element={<SecurityInfo />} />
            <Route path="edit-summary" element={<EditSummary />} />
          </Route>
        </Route>
        {/* Role Recruiter */}
        <Route path="recruiter" allowedRoles={ROLES.RECRUITER}>
          <Route path="add-job-post" element={<AddJobPost />} />
        </Route>
        {/* Role Candidate */}
        <Route path="candidate" allowedRoles={ROLES.CANDIDATE}>
          <Route path="manage-job" element={<CandidateJobManagement />} />
        </Route>
      </Route>

      <Route path="admin" element={<AdminLayout />}>
        <Route index element={<Dashboard />} />
        <Route path="category" element={<CategoryManagement />} />
        <Route path="badge" element={<BadgeManagement />} />
        <Route path="user" element={<UserManagement />} />
      </Route>

      <Route element={<PrivateRoute allowedRoles={ROLES.ADMIN} />}></Route>
    </Routes>
  );
}

export default App;
