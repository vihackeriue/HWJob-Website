import {axiosPublic} from "../api/axios.jsx";
import {ENDPOINTS} from "../config/endpoints.jsx";

export const getTopRecruiters = async () => {
    const response = await axiosPublic.get(ENDPOINTS.HOME.TOP_RECRUITER);
    console.log(response.data);
    return response.data;
}