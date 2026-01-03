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
            PROFILE: (id) => `public/profiles/candidate/${id}`,
        },
        RECRUITER: {
            UPDATE_INFO: "recruiters/users",
            PROFILE: (id) => `public/profiles/recruiter/${id}`,
        },

    },

    JOB_POST: {
        CREATE: "recruiters/job-posts",
        UPDATE: (id) => `recruiters/job-posts/${id}`,
        LIST: "public/job-posts",
        LIST_WORKING_JOBS: "public/job-posts",
        LIST_SAVED_JOB_POSTS: "candidates/job-posts/saved",

        LIST_HISTORY_JOB_POSTS: "public/job-posts",
        LIST_JOB_POSTS_OF_RECRUITER: "recruiters/job-posts",
        DETAIL: (id) => `public/job-posts/${id}`,

        SAVE_JOB: (id) => `candidates/job-posts/save/${id}`,

        DETAIL_STATS: (id) => `/recruiters/job-posts/${id}/stats`,
        STATS_OF_RECRUITER: "recruiters/job-posts/stats",
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
        LIST_ALL_APPLICANT_OF_RECRUITER: "recruiters/applications/candidates/all",
        GET_RANKED_CANDIDATES: (jobPostId) => `/recruiters/applications/job-posts/${jobPostId}/candidates/ranked`,
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
        LIST_ALL_FREELANCER_OF_RECRUITER: "recruiters/works/candidates/all",
    },
    REVIEW: {
        CREATE_REVIEW: "reviews",
        GET_MY_REVIEWS: "reviews/me",
        GET_MY_AVERAGE_RATING: "reviews/average-rating",
    },
    LOYALTY_POINT: {
        GET_POINTS: "loyalty-points/me",
        GET_LOCKED_POINTS: "loyalty-points/locked/me",
        GET_HISTORY_PAYMENT: "loyalty-points/payment-history",
        TOP_UP: "loyalty-points/top-up",
    },
    REPUTATION: {
        GET_MY_REPUTATION: "reputation/me",
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

    HOME: {
        TOP_RECRUITER: "/public/home/top-recruiters",
        RECOMMEND_JOB_POSTS: "/candidates/job-posts/recommend"
    },
    WS: {
        ENDPOINT: "ws",
        DESTINATION: {
            SEND_MESSAGE: "/app/chat.send",
            USER_MESSAGES: "/user/queue/messages",
        },
    },
    CHAT: {
        CONVERSATIONS: "/chats",
        MESSAGES: (conversationId) => `/chats/${conversationId}/messages`,
    },
};
