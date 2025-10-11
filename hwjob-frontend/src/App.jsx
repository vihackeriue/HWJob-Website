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
      </Route>

      <Route element={<PrivateRoute allowedRoles={ROLES.admin} />}>
        <Route path="admin" element={<AdminLayout />}>
          <Route index element={<Dashboard />} />
        </Route>
      </Route>
    </Routes>
  );
}

export default App;
