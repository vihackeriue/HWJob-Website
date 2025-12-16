import {axiosPrivate, axiosPublic, DEFAULT_LIMIT} from "../api/axios";
import {ENDPOINTS} from "../config/endpoints";
import {ROLES} from "../constants/roles.jsx";

export const createUser = async (data) => {
    const response = await axiosPublic.post(ENDPOINTS.AUTH.REGISTER, data);
    return response.data;
};

export const getUsers = async (page = 1, size = DEFAULT_LIMIT) => {
    const response = await axiosPrivate.get(ENDPOINTS.USER.LIST, {
        params: {page, size},
    });
    return response.data;
};


export const getMyInfo = async () => {
    const response = await axiosPrivate.get(ENDPOINTS.USER.MY_INFO);
    return response.data;
}

export const updateAvatarUser = async (file) => {
    const formData = new FormData();
    formData.append("file", file);

    const response = await axiosPrivate.patch(
        ENDPOINTS.USER.UPDATE_AVATAR,
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        }
    );

    return response.data;
};
export const updatePasswordUser = async (data) => {
    const response = await axiosPrivate.patch(
        ENDPOINTS.USER.UPDATE_PASSWORD,
        data
    );

    return response.data;
};

export const updateUserInfo = async (data, role) => {
    if (role === ROLES.CANDIDATE) {
        return await axiosPrivate.put(
            ENDPOINTS.USER.CANDIDATE.UPDATE_INFO,
            data
        );
    }

    if (role === ROLES.RECRUITER) {
        return await axiosPrivate.put(
            ENDPOINTS.USER.RECRUITER.UPDATE_INFO,
            data
        );
    }

    throw new Error("Unsupported role");
};

