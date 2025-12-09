import {useEffect, useState} from "react";

export function useDetail(fetchFn, id) {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!id) return;

        setLoading(true);
        fetchFn(id)
            .then((res) => setData(res.result))
            .catch(() => setError("Không thể tải dữ liệu"))
            .finally(() => setLoading(false));
    }, [id]);

    return {data, loading, error};
}
