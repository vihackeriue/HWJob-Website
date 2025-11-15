import { axiosPublic } from "../api/axios";

export const getIndustriesNotPagination = async () => {
  const response = await axiosPublic.get("industries");
  return response.data;
};
