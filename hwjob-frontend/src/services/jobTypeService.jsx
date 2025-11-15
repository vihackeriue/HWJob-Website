import { axiosPublic, DEFAULT_LIMIT } from "../api/axios";

export const getJobTypes = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(`job-types?page=${page}&size=${size}`);
  return response.data;
};
export const getJobTypesNotPagination = async () => {
  const response = await axiosPublic.get("job-types");
  return response.data;
};
