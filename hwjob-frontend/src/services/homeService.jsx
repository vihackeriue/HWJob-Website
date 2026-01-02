import {axiosPrivate, axiosPublic} from "../api/axios.jsx";
import {ENDPOINTS} from "../config/endpoints.jsx";

export const getTopRecruiters = async () => {
    const response = await axiosPublic.get(ENDPOINTS.HOME.TOP_RECRUITER);
    console.log(response.data);
    return response.data;
}

export const getTop10RecommendJobPosts = async (page = 1, size = 10) => {
    const response = await axiosPrivate.get(ENDPOINTS.HOME.RECOMMEND_JOB_POSTS,
        {
            params: {page, size}
        }
    );
    console.log(response.data);
    return response.data;
}