import {useEffect, useState} from "react";
import {getMyInfo} from "../../services/userService.jsx";

const useMyInfo = () => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchMyInfo = async () => {
        try {
            setLoading(true);
            const res = await getMyInfo();
            setData(res.result);
        } catch (err) {
            console.error(err);
            setError("Không thể tải dữ liệu");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        void fetchMyInfo();
    }, []);

    return {
        data,
        loading,
        error,
        refetch: fetchMyInfo
    };
};

export default useMyInfo;
