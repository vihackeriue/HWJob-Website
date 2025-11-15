import React, { useEffect, useState } from "react";
import { getJobTypes } from "../services/jobTypeService";

export const useJobTypeList = () => {
  const [loading, setLoading] = useState(true);
  const [jobTypes, setJobTypes] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  useEffect(() => {
    const fechJobTypes = async () => {
      setLoading(true);
      try {
        const res = await getJobTypes(page);
        setJobTypes(res.result);
        setTotalPages(res.totalPages);
      } catch (error) {
        const errMsg =
          error.response?.data?.message ||
          "Có lỗi xảy ra khi lấy danh sách loại nghề nghiệp!";
        alert(errMsg);
      } finally {
        setLoading(false);
      }
    };

    fechJobTypes();
  }, [page]);

  return { loading, jobTypes, page, totalPages, setPage };
};
