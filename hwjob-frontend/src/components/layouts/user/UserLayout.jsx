import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "./navbar/Navbar";
import Footer from "./Footer";

export default function UserLayout() {
  return (
    <div>
      <div className="sticky top-0 z-50 bg-white shadow-sm">
        <Navbar />
      </div>
      <div className="container min-h-150">{<Outlet />}</div>
      <Footer />
    </div>
  );
}
