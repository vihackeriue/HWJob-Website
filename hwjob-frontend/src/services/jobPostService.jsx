import { axiosPrivate, axiosPublic, DEFAULT_LIMIT } from "../api/axios";

export const createJobPost = async (data) => {
  const response = await axiosPrivate.post("/job-posts", data);
  return response.data;
};
export const getJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(`job-posts?page=${page}&size=${size}`);

  return response.data;
};
export const getWorkingJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(`job-posts?page=${page}&size=${size}`);

  return response.data;
};

export const getSavedJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(`job-posts?page=${page}&size=${size}`);

  return response.data;
};
export const getAppliedJobPosts = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPublic.get(`job-posts?page=${page}&size=${size}`);

  return response.data;
};

export const getJobPostById = async (id) => {
  const response = await axiosPrivate.get(`job-posts/${id}`);
  return response.data;
};

export const applyJob = async (data) => {
  const response = await axiosPrivate.post("applications/apply", data);
  return response.data;
};
