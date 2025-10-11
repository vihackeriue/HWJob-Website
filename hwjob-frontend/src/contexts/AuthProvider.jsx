import React, { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  loginService,
  logoutService,
  refreshService,
  updateWalletService,
} from "../services/authService";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  //   const [token, setToken] = useState(localStorage.getItem("site") || "");
  const [auth, setAuth] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  //   keep logged in for website
  useEffect(() => {
    const accessToken = localStorage.getItem("site");
    if (accessToken) {
      try {
        const decoded = jwtDecode(accessToken);
        if (decoded.exp * 1000 < Date.now()) {
          localStorage.removeItem("site");
          setAuth(null);
        } else {
          const username = decoded.sub;
          const roles = decoded.scope ? decoded.scope.split(" ") : [];
          // const walletAddress = decoded.walletAddress;
          // setAuth({ username, roles, walletAddress, accessToken });
          setAuth({ username, roles, accessToken });
        }
      } catch (err) {
        console.error("Invalid token:", err);
        localStorage.removeItem("site");
      }
    }
    setLoading(false);
  }, []);

  const login = async (data) => {
    try {
      const res = await loginService(data);

      if (res.code === 1000 && res.result.authenticated) {
        const accessToken = res.result.token;
        const decoded = jwtDecode(accessToken);

        // setToken(accessToken);
        const username = decoded.sub;
        const roles = decoded.scope ? decoded.scope.split(" ") : [];
        // const walletAddress = decoded.walletAddress;
        // setAuth({ username, roles, walletAddress, accessToken });
        setAuth({ username, roles, accessToken });

        localStorage.setItem("site", accessToken);

        if (roles.includes("ROLE_ADMIN")) {
          navigate("/admin");
        } else if (roles.includes("ROLE_USER")) {
          navigate("/");
        } else {
          navigate("/");
        }
      }
    } catch (error) {
      console.error("Login failed:", error);
    }
  };

  const refresh = async () => {
    try {
      const res = await refreshService();
      if (res.code === 1000) {
        const accessToken = res.data.token;

        const decoded = jwtDecode(accessToken);

        const username = decoded.sub;
        const roles = decoded.scope ? decoded.scope.split(" ") : [];
        const walletAddress = decoded.walletAddress;

        setAuth({ username, roles, walletAddress, accessToken });
        localStorage.setItem("site", accessToken);
      }
    } catch (error) {
      console.error("Refresh token failed:", error);
      logout();
    }
  };
  const updateWallet = async (data) => {
    try {
      const res = await updateWalletService(data);
      if (res.code === 1000) {
        const accessToken = res.data.token;

        const decoded = jwtDecode(accessToken);

        const username = decoded.sub;
        const roles = decoded.scope ? decoded.scope.split(" ") : [];
        const walletAddress = decoded.walletAddress;

        setAuth({ username, roles, walletAddress, accessToken });
        localStorage.setItem("site", accessToken);
        alert("Cập nhật ví thành công!");
      }
    } catch (error) {
      const errMsg =
        error.response?.data?.message || "Có lỗi xảy ra khi cập nhật ví!";
      alert(errMsg);
    }
  };
  const logout = async () => {
    try {
      await logoutService();
    } catch (error) {
      console.warn("Server logout failed, vẫn xoá token local:", error);
    } finally {
      setAuth(null);
      localStorage.removeItem("site");
      navigate("/");
    }
  };

  return (
    <AuthContext.Provider
      value={{ loading, auth, login, logout, refresh, updateWallet }}
    >
      {children}
    </AuthContext.Provider>
  );
};
export default AuthContext;
