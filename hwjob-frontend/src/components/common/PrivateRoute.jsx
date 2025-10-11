import React from "react";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "../../hooks/useAuth";

function PrivateRoute({ allowedRoles }) {
  const { loading, auth } = useAuth();
  const location = useLocation();

  if (loading) return <div>Loading...</div>;

  if (!auth?.accessToken) {
    // chưa đăng nhập → về login
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // check nếu bất kỳ role nào của user nằm trong allowedRoles
  const hasRole = auth?.roles?.some((role) => allowedRoles.includes(role));

  if (!hasRole) {
    return <Navigate to="/unauthorized" state={{ from: location }} replace />;
  }

  // đúng role → render route con
  return <Outlet />;
}

export default PrivateRoute;
