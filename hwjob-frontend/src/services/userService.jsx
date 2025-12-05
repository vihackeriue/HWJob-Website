import { axiosPrivate, axiosPublic, DEFAULT_LIMIT } from "../api/axios";
import { ENDPOINTS } from "../config/endpoints";

export const createUser = async (data) => {
  const response = await axiosPublic.post(ENDPOINTS.AUTH.REGISTER, data);
  return response.data;
};

export const getUsers = async (page = 1, size = DEFAULT_LIMIT) => {
  const response = await axiosPrivate.get(ENDPOINTS.USER.LIST, {
    params: { page, size },
  });

  return response.data;
};

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
