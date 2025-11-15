import { axiosPublic, DEFAULT_LIMIT } from "../api/axios";

export const getRegions = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(`regions?page=${page}&size=${size}`);
  return response.data;
};
export const getRegionsNotPagination = async () => {
  const response = await axiosPublic.get("regions");
  return response.data;
};
