import { axiosPublic } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const getIndustriesNotPagination = async () => {
  const response = await axiosPublic.get(ENDPOINTS.INDUSTRY.LIST);
  return response.data;
};
