import { axiosPrivate } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const getMyReputation = async () => {
  const response = await axiosPrivate.get(
    ENDPOINTS.REPUTATION.GET_MY_REPUTATION
  );
  return response.data;
};
