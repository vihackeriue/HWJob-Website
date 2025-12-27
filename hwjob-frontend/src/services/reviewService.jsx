import { axiosPrivate, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const createReview = async (data) => {
  const response = await axiosPrivate.post(
    ENDPOINTS.REVIEW.CREATE_REVIEW,
    data
  );
  return response.data;
};
export const getMyReviews = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPrivate.get(ENDPOINTS.REVIEW.GET_MY_REVIEWS, {
    params: { page, size },
  });
  return response.data;
};
export const getAverageRating = async () => {
  const response = await axiosPrivate.get(
    ENDPOINTS.REVIEW.GET_MY_AVERAGE_RATING
  );
  return response.data;
};
