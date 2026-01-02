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

export const getStaffOfWork = async (
  jobPostId,
  page = 1,
  size = DEFAULT_LIMIT
) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.WORK.LIST_STAFF_OF_WORK(jobPostId),
    {
      params: { page, size },
    }
  );
  return response.data;
};
export const getAllFreelancerOfRecruiter = async (
  page = 1,
  size = DEFAULT_LIMIT
) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.WORK.LIST_ALL_FREELANCER_OF_RECRUITER,
    {
      params: { page, size },
    }
  );

  return response.data;
};

export const recruiterUpdateWorkStatus = (jobPostId, candidateId, data) => {
  return axiosPrivate.patch(
    ENDPOINTS.WORK.RECRUITER_UPDATE_STATUS_WORK(jobPostId, candidateId),
    data
  );
};

export const candidateUpdateWorkStatus = (jobPostId, data) => {
  return axiosPrivate.patch(
    ENDPOINTS.WORK.CANDIDATE_UPDATE_STATUS_WORK(jobPostId),
    data
  );
};
