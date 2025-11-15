import { useEffect, useState } from "react";

export function useList(fetchFn) {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    fetchFn(page)
      .then((res) => {
        setData(res.result);
        setTotalPages(res.totalPages);
      })
      .finally(() => setLoading(false));
  }, [page]);

  return { data, loading, page, totalPages, setPage };
}
