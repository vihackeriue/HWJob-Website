import { axiosPrivate, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const applyJob = async (data) => {
  const response = await axiosPrivate.post(
    ENDPOINTS.APPLICATION.APPLY_JOB,
    data
  );
  return response.data;
};
export const getAppliedJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.APPLICATION.LIST_APPLIED_JOB_POSTS,
    {
      params: { page, size },
    }
  );

  return response.data;
};

export const getCandidateApplications = async (
  jobPostId,
  page = 1,
  size = DEFAULT_LIMIT
) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.APPLICATION.LIST_APPLICANTS(jobPostId),
    {
      params: { page, size },
    }
  );
  return response.data;
};
export const updateApplicantStatus = (applicationId, jobPostId, status) => {
  return axiosPrivate.patch(
    ENDPOINTS.APPLICATION.UPDATE_STATUS_APPLICANTS(applicationId, jobPostId),
    {
      status,
    }
  );
};
