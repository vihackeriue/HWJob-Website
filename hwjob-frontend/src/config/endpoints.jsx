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
  },

  JOB_POST: {
    CREATE: "recruiters/job-posts",
    LIST: "public/job-posts",
    LIST_WORKING_JOBS: "public/job-posts",
    LIST_SAVED_JOB_POSTS: "candidates/job-posts/saved",
    LIST_APPLIED_JOB_POSTS: "candidates/applications",
    LIST_HISTORY_JOB_POSTS: "public/job-posts",
    DETAIL: (id) => `public/job-posts/${id}`,
    APPLY_JOB: "candidates/applications",
    SAVE_JOB: (id) => `candidates/job-posts/save/${id}`,
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
};
