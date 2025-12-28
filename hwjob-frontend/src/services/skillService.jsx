import {axiosPublic, DEFAULT_LIMIT} from "../api/axios.jsx";
import {ENDPOINTS} from "../config/endpoints.jsx";

export const getSkills = async (page = 1, size = DEFAULT_LIMIT) => {
    const response = await axiosPublic.get(ENDPOINTS.SKILL.LIST, {
        params: {page, size},
    });
    return response.data;
};
export const getSkillsNotPagination = async () => {
    const response = await axiosPublic.get(ENDPOINTS.SKILL.LIST);
    console.log(response.data);
    return response.data;
};