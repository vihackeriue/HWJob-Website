import { axiosPrivate, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";
import { v4 as uuidv4 } from "uuid";
export const getLoyaltyPointById = async () => {
  const response = await axiosPrivate.get(ENDPOINTS.LOYALTY_POINT.GET_POINTS);
  return response.data;
};

export const getMyLockedLoyaltyPoint = async () => {
  const response = await axiosPrivate.get(
    ENDPOINTS.LOYALTY_POINT.GET_LOCKED_POINTS
  );
  return response.data;
};
export const topUpPoint = (data) => {
  return axiosPrivate.post(ENDPOINTS.LOYALTY_POINT.TOP_UP, data);
};

export const withdrawPoint = (amount) => {
  const idempotentKey = `withdraw-${uuidv4()}`;

  return axiosPrivate.post(
    ENDPOINTS.LOYALTY_POINT.WITHDRAW,
    { amount },
    {
      headers: {
        "Idempotent-Key": idempotentKey,
      },
    }
  );
};

export const getHistoryPayment = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.LOYALTY_POINT.GET_HISTORY_PAYMENT,
    {
      params: { page, size },
    }
  );
  return response.data;
};
