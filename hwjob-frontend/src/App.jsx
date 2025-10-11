import { Route, Routes } from "react-router-dom";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import Login from "./pages/auth/Login";
import UserLayout from "./components/layouts/user/UserLayout";
import Home from "./pages/user/Home";
import Register from "./pages/auth/Register";

function App() {
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register />} />

      <Route path="/" element={<UserLayout />}>
        <Route index element={<Home />} />
      </Route>
    </Routes>
  );
}

export default App;
