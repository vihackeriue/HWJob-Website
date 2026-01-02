import { axiosPrivate, axiosPublic, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const createJobPost = async (data) => {
  const response = await axiosPrivate.post(ENDPOINTS.JOB_POST.CREATE, data);
  return response.data;
};
export const getJobPosts = async (
  page = 1,
  size = DEFAULT_LIMIT,
  filters = {}
) => {
  const response = await axiosPublic.get(ENDPOINTS.JOB_POST.LIST, {
    params: { page, size, ...filters },
  });
  return response.data;
};
export const getWorkingJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(ENDPOINTS.JOB_POST.LIST_WORKING_JOBS, {
    params: { page, size },
  });
  return response.data;
};

export const getSavedJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.JOB_POST.LIST_SAVED_JOB_POSTS,
    {
      params: { page, size },
    }
  );

  return response.data;
};

export const getHistoryJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(
    ENDPOINTS.JOB_POST.LIST_HISTORY_JOB_POSTS,
    {
      params: { page, size },
    }
  );
  return response.data;
};
// recruiter's job posts
export const getJobPostsOfRecruiter = async (
  page = 1,
  size = DEFAULT_LIMIT,
  filters = {}
) => {
  const response = await axiosPrivate.get(
    ENDPOINTS.JOB_POST.LIST_JOB_POSTS_OF_RECRUITER,
    {
      params: { page, size, ...filters },
    }
  );
  return response.data;
};

export const getJobPostById = async (id) => {
  const response = await axiosPrivate.get(ENDPOINTS.JOB_POST.DETAIL(id));
  return response.data;
};
// Stats
export const getJobPostDetailStats = async (id) => {
  const response = await axiosPrivate.get(ENDPOINTS.JOB_POST.DETAIL_STATS(id));
  return response.data;
};
export const getJobPostOfRecruiterStats = async () => {
  const response = await axiosPrivate.get(
    ENDPOINTS.JOB_POST.STATS_OF_RECRUITER
  );
  return response.data;
};

export const saveJob = async (id) => {
  const response = await axiosPrivate.post(ENDPOINTS.JOB_POST.SAVE_JOB(id));
  return response.data;
};
