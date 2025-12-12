import React, { useEffect, useState } from "react";

import { useParams } from "react-router-dom";
import { useDetail } from "../../hooks/useDetail";
import {
  applyJob,
  getJobPostById,
  saveJob,
} from "../../services/jobPostService";

import ApplyJobDialog from "../../components/dialog/ApplyJobDialog";

import { toast } from "react-toastify";
import { OverviewSection } from "../../components/sections/common/jobPostDetail/OverviewSection";

import DescriptionSection from "../../components/sections/common/jobPostDetail/DescriptionSection";

const JobPostDetail = () => {
  const { id } = useParams();
  const { data, loading } = useDetail(getJobPostById, id);

  const [openApplyJobDialog, setOpenApplyJobDialog] = useState(false);
  const [jobPost, setJobPost] = useState(null);

  useEffect(() => {
    if (data) setJobPost(data);
  }, [data]);
  // Ngăn lỗi null
  if (loading || !jobPost) return <div>Đang tải...</div>;

  const handleApplyJob = async () => {
    try {
      const payload = {
        jobPostId: jobPost.id,
      };
      // gọi API backend đăng ký
      await applyJob(payload);

      alert("ứng tuyển thành công");
      setJobPost((prev) => ({
        ...prev,
        isApplied: true,
      }));
      setOpenApplyJobDialog(false);
    } catch (error) {
      alert(error.response?.data?.message || "Ứng tuyển thất bại!");
    }
  };

  const handleSaveJob = async () => {
    try {
      const res = await saveJob(jobPost.id);
      const isSaved = res.result.saved;
      setJobPost((prev) => ({
        ...prev,
        isSaved: isSaved,
      }));
      toast.success(isSaved ? "Lưu thành công!" : "Đã hủy lưu!");
    } catch (error) {
      toast.error(error.response?.data?.message || "Lưu thất bại!");
    }
  };
  // const handleUpdateJonPost = () => {};
  return (
    <div className="flex flex-col gap-3 mt-10">
      <OverviewSection
        jobPost={jobPost}
        setOpenApplyJobDialog={setOpenApplyJobDialog}
        onSaveJobPost={handleSaveJob}
      />
      <div className="bg-white rounded-2xl p-5">
        <DescriptionSection jobPost={jobPost} />
      </div>

      <ApplyJobDialog
        open={openApplyJobDialog}
        onClose={() => setOpenApplyJobDialog(false)}
        onSubmit={handleApplyJob}
      />
    </div>
  );
};

export default JobPostDetail;
