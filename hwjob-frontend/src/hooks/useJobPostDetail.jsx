import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getJobPostById } from "../services/jobPostService";

export const useJobPostDetail = () => {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [jobPost, setJobPost] = useState(null);
  const [error, setError] = useState(null);
  useEffect(() => {
    if (!id) return;

    const fetchData = async () => {
      try {
        const data = await getJobPostById(id);
        setJobPost(data);
      } catch (err) {
        setError("Không thể tải dữ liệu bài đăng", err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  return { jobPost, loading, error };
};
