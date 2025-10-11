import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "./navbar/Navbar";
import Footer from "./Footer";

export default function UserLayout() {
  return (
    <div>
      <Navbar />
      <div className="container min-h-150 mb-10">{<Outlet />}</div>
      <Footer />
    </div>
  );
}
