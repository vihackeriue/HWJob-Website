import { axiosPrivate } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const getLoyaltyPointById = async () => {
  const response = await axiosPrivate.get(ENDPOINTS.LOYALTY_POINT.GET_POINTS);
  return response.data;
};

export const topUpPoint = (data) => {
  return axiosPrivate.post(ENDPOINTS.LOYALTY_POINT.TOP_UP, data);
};
