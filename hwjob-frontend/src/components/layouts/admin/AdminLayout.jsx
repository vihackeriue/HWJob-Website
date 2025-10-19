import React from "react";
import { Outlet } from "react-router-dom";
import SideBar from "./SideBar";

function AdminLayout() {
  return (
    <div className="flex flex-row bg-dark-900 w-screen min-h-screen ">
      <SideBar />
      <div className="container min-h-150 mb-10">{<Outlet />}</div>
    </div>
  );
}

export default AdminLayout;
