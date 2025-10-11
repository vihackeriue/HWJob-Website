import axios from "axios";

const BASE_URL = "http://localhost:8080/hwjob/api/";
export const DEFAULT_LIMIT = 6;

export const axiosPublic = axios.create({
  baseURL: BASE_URL,
  headers: { "Content-Type": "application/json" },
});

export const axiosPrivate = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});
axiosPrivate.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("site");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
