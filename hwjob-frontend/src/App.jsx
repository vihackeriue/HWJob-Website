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
import JobPost from "./pages/user/JobPost";
import MyProfile from "./pages/user/profile/MyProfile";
import Overview from "./pages/user/profile/Overview";
import PersonalInfo from "./pages/user/profile/PersonalInfo";
import EditSummary from "./pages/user/profile/EditSummary";
import SecurityInfo from "./pages/user/profile/SecurityInfo";

const ROLES = {
  admin: "ROLE_ADMIN",
  User: "ROLE_USER",
};

function App() {
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register />} />

      <Route path="/" element={<UserLayout />}>
        <Route index element={<Home />} />
        <Route path="job-post" element={<JobPost />} />
        <Route path="my-profile" element={<MyProfile />}>
          <Route index element={<Overview />} />
          <Route path="personal" element={<PersonalInfo />} />
          <Route path="security" element={<SecurityInfo />} />
          <Route path="edit-summary" element={<EditSummary />} />
        </Route>
      </Route>

      <Route path="admin" element={<AdminLayout />}>
        <Route index element={<Dashboard />} />
      </Route>

      <Route element={<PrivateRoute allowedRoles={ROLES.admin} />}></Route>
    </Routes>
  );
}

export default App;
