import React from "react";
import { Outlet } from "react-router-dom";

function AdminLayout() {
  return (
    <div>
      <div className="container min-h-150 mb-10">{<Outlet />}</div>
    </div>
  );
}

export default AdminLayout;
