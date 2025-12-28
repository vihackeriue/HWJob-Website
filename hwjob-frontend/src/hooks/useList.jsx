import {useEffect, useState} from "react";
import {DEFAULT_LIMIT} from "../api/axios";

export function useList(fetchFn, initialParams = {}) {
    const [data, setData] = useState([]);
    const [page, setPage] = useState(1);
    const [size] = useState(DEFAULT_LIMIT);
    const [totalPages, setTotalPages] = useState(1);
    const [params, setParams] = useState(initialParams);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        fetchFn(page, size, params)
            .then((res) => {
                setData(res.result);
                setTotalPages(res.totalPages);
                console.log("API RESULT", res);
            })
            .finally(() => setLoading(false));
    }, [page, params]);

    return {data, loading, page, totalPages, setPage, setParams, setData};
}
