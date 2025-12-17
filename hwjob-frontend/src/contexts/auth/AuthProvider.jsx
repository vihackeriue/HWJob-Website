import React, {createContext, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {
    loginService,
    logoutService,
    refreshService,
    updateWalletService,
} from "../../services/authService.jsx";
import {jwtDecode} from "jwt-decode";
import {toast} from "react-toastify";
import {ROLES} from "../../constants/roles.jsx";

const AuthContext = createContext({});

export const AuthProvider = ({children}) => {
    //   const [token, setToken] = useState(localStorage.getItem("site") || "");
    const [auth, setAuth] = useState(null);
    const [loading, setLoading] = useState(true);

    const navigate = useNavigate();

    //   keep logged in for website
    useEffect(() => {
        const accessToken = localStorage.getItem("site");
        const userAvatar = localStorage.getItem("userAvatar");
        if (accessToken) {
            try {
                const decoded = jwtDecode(accessToken);
                if (decoded.exp * 1000 < Date.now()) {
                    localStorage.removeItem("site");
                    setAuth(null);
                } else {
                    const id = decoded.sub;
                    const username = decoded.username;
                    const fullname = decoded.userFullName;
                    const roles = decoded.scope ? decoded.scope.split(" ") : [];

                    // const walletAddress = decoded.walletAddress;
                    // setAuth({ username, roles, walletAddress, accessToken });
                    setAuth({id, username, fullname, userAvatar, roles, accessToken});
                }
            } catch (err) {
                console.error("Invalid token:", err);
                localStorage.removeItem("site");
                localStorage.removeItem("userAvatar");
            }
        }
        setLoading(false);
    }, []);

    const login = async (data) => {
        try {
            const res = await loginService(data);

            if (res.code === 1000) {
                const accessToken = res.result.accessToken;
                const decoded = jwtDecode(accessToken);
                const userAvatar = res.result.user.imageUrl;
                // setToken(accessToken);
                const id = decoded.sub;
                const username = decoded.username;
                const fullname = decoded.userFullName;
                const roles = decoded.scope ? decoded.scope.split(" ") : [];
                // const walletAddress = decoded.walletAddress;
                // setAuth({ username, roles, walletAddress, accessToken });
                setAuth({id, username, fullname, userAvatar, roles, accessToken});

                localStorage.setItem("site", accessToken);
                localStorage.setItem("userAvatar", userAvatar);
                toast.success("Đăng nhập thành công!");
                if (roles.includes(ROLES.ADMIN)) {
                    navigate("/admin");
                } else if (roles.includes(ROLES.CANDIDATE)) {
                    navigate("/");
                } else if (roles.includes(ROLES.RECRUITER)) {
                    navigate("/");
                } else {
                    navigate("/");
                }
            }
        } catch (error) {
            console.error("Login failed:", error);
            toast.error(
                error.response?.data?.message || "Đăng nhập thất bại! Vui lòng thử lại."
            );
        }
    };

    const updateAvatarRealtime = (newAvatar) => {
        // cập nhật trong state
        setAuth((prev) => {
            if (!prev) return prev;
            return {
                ...prev,
                userAvatar: newAvatar,
            };
        });

        // cập nhật trong localStorage
        localStorage.setItem("userAvatar", newAvatar);
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

                setAuth({username, roles, walletAddress, accessToken});
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

                setAuth({username, roles, walletAddress, accessToken});
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
            localStorage.removeItem("userAvatar");
            navigate("/");
        }
    };

    return (
        <AuthContext.Provider
            value={{
                loading,
                auth,
                login,
                logout,
                refresh,
                updateWallet,
                updateAvatarRealtime,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};
export default AuthContext;
