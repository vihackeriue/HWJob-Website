import {useState} from "react";
import {updateAvatarUser} from "../../services/userService.jsx";

export const useUpdateAvatar = () => {
    const [loading, setLoading] = useState(false);

    const updateAvatar = async (file) => {
        try {
            setLoading(true);
            const data = await updateAvatarUser(file);
            return data; // trả về URL ảnh mới
        } finally {
            setLoading(false);
        }
    };

    return {updateAvatar, loading};
};
