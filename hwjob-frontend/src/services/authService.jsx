import { axiosPrivate, axiosPublic } from "../api/axios";

export const loginService = async (data) => {
  const response = await axiosPublic.post("/auth/login", data);
  return response.data;
};

export const refreshService = async () => {
  const response = await axiosPrivate.post("/auth/refresh");
  return response.data;
};
// update wallet
export const updateWalletService = async (data) => {
  console.log("TOKEN gửi đi:", localStorage.getItem("site"));
  const response = await axiosPrivate.put("/auth/update-wallet", data);
  return response.data;
};

export const logoutService = async () => {
  try {
    const res = await axiosPrivate.post("/auth/logout");
    return res.data;
  } catch (err) {
    console.error("Logout failed:", err);
    throw err;
  }
};
