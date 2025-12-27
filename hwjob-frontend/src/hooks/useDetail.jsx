import { useEffect, useState } from "react";

export function useDetail(fetchFn, id) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);

    const promise = id ? fetchFn(id) : fetchFn();

    promise
      .then((res) => setData(res.result))
      .catch(() => setError("Không thể tải dữ liệu"))
      .finally(() => setLoading(false));
  }, [id]);

  return { data, loading, error, setData };
}
