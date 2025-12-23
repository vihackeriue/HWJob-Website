export const ENDPOINTS = {
  AUTH: {
    LOGIN: "auth/login",
    REGISTER: "auth/register",
    LOGOUT: "auth/logout",
    REFRESH: "/auth/refresh",
  },
  USER: {
    CREATE: "",
    LIST: "",
    UPDATE_AVATAR: "users/me/avatar",
    UPDATE_PASSWORD: "users/me/password",
    MY_INFO: "users/me",

    CANDIDATE: {
      UPDATE_INFO: "candidates/users",
    },
    RECRUITER: {
      UPDATE_INFO: "recruiters/users",
    },
  },

  JOB_POST: {
    CREATE: "recruiters/job-posts",
    LIST: "public/job-posts",
    LIST_WORKING_JOBS: "public/job-posts",
    LIST_SAVED_JOB_POSTS: "candidates/job-posts/saved",

    LIST_HISTORY_JOB_POSTS: "public/job-posts",
    LIST_JOB_POSTS_OF_RECRUITER: "recruiters/job-posts",
    DETAIL: (id) => `public/job-posts/${id}`,

    SAVE_JOB: (id) => `candidates/job-posts/save/${id}`,
  },
  APPLICATION: {
    APPLY_JOB: "candidates/applications",
    LIST_APPLIED_JOB_POSTS: "candidates/applications",
    LIST_APPLICANTS: (jobPostId) =>
      `recruiters/applications/job-posts/${jobPostId}/candidates`,
    RECRUITER_UPDATE_STATUS_APPLICANTS: (applicationId, jobPostId) =>
      `recruiters/applications/job-posts/${jobPostId}/candidates/${applicationId}/status`,
    CANDIDATE_UPDATE_STATUS_APPLICANTS: (jobPostId) =>
      `/candidates/applications/job-posts/${jobPostId}/status`,
  },
  WORK: {
    CANDIDATE_WORK_OVERVIEW: (jobPostId) =>
      `candidates/works/${jobPostId}/overview`,
    LIST_WORKS_OF_CANDIDATE: "candidates/works/me",
    LIST_STAFF_OF_WORK: (jobPostId) =>
      `recruiters/works/job-posts/${jobPostId}/candidates`,
    RECRUITER_UPDATE_STATUS_WORK: (jobPostId, candidateId) =>
      `recruiters/works/job-posts/${jobPostId}/candidates/${candidateId}/status`,
    CANDIDATE_UPDATE_STATUS_WORK: (jobPostId) =>
      `candidates/works/job-posts/${jobPostId}/status`,
  },
  LOYALTY_POINT: {
    GET_POINTS: "loyalty-points/me",
    TOP_UP: "loyalty-points/top-up",
  },
  INDUSTRY: {
    LIST: "public/industries",
  },
  JOB_TYPE: {
    LIST: "public/job-types",
  },
  LEVEL: {
    LIST: "public/levels",
  },
  REGION: {
    LIST: "public/regions",
  },
  SKILL: {
    LIST: "public/skills",
  },
};
