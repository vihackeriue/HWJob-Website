import { axiosPublic, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const getLevels = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(ENDPOINTS.LEVEL.LIST, {
    params: { page, size },
  });

  return response.data;
};
export const getLevelsNotPagination = async () => {
  const response = await axiosPublic.get(ENDPOINTS.LEVEL.LIST);
  return response.data;
};
