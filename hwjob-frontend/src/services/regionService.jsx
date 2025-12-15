import {axiosPublic, DEFAULT_LIMIT} from "../api/axios";
import {ENDPOINTS} from "../config/endpoints";

export const getRegions = async (page = 1, size = DEFAULT_LIMIT) => {
    const response = await axiosPublic.get(ENDPOINTS.REGION.LIST, {
        params: {page, size},
    });
    return response.data;
};
export const getRegionsNotPagination = async () => {
    const response = await axiosPublic.get(ENDPOINTS.REGION.LIST);
    return response.data;
};
