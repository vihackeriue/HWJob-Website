import { axiosPublic, DEFAULT_LIMIT } from "../api/axios";

export const getLevels = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(`levels?page=${page}&size=${size}`);

  return response.data;
};
export const getLevelsNotPagination = async () => {
  const response = await axiosPublic.get("levels");
  return response.data;
};
