import { axiosPrivate, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const getWorkOverview = async (jobPostId) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.WORK.CANDIDATE_WORK_OVERVIEW(jobPostId)
  );
  return response.data;
};
export const getWorkOfCandidate = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.WORK.LIST_WORKS_OF_CANDIDATE,
    {
      params: { page, size },
    }
  );

  return response.data;
};
