import { axiosPrivate, axiosPublic, DEFAULT_LIMIT } from "../api/axios";

export const createUser = async (data) => {
  const response = await axiosPublic.post("/users", data);
  return response.data;
};

export const getUsers = async (page = 1, limit = DEFAULT_LIMIT) => {
  const response = await axiosPrivate.get(`/users?page=${page}&limit=${limit}`);

  return response.data;
};
