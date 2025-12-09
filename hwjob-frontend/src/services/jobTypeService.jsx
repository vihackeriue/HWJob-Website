import { axiosPublic, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const getJobTypes = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(ENDPOINTS.JOB_TYPE.LIST, {
    params: { page, size },
  });
  return response.data;
};
export const getJobTypesNotPagination = async () => {
  const response = await axiosPublic.get(ENDPOINTS.JOB_TYPE.LIST);
  return response.data;
};
