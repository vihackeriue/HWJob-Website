import React, { useState } from "react";

import { useParams } from "react-router-dom";
import { useDetail } from "../../hooks/useDetail";
import { getJobPostById, saveJob } from "../../services/jobPostService";
import ApplyJobDialog from "../../components/dialog/ApplyJobDialog";
import { toast } from "react-toastify";
import { OverviewSection } from "../../components/sections/common/jobPostDetail/OverviewSection";
import DescriptionSection from "../../components/sections/common/jobPostDetail/DescriptionSection";
import { applyJob } from "../../services/applicationService";

const JobPostDetail = () => {
  const { id } = useParams();
  const jobPost = useDetail(getJobPostById, id);

  const [openApplyJobDialog, setOpenApplyJobDialog] = useState(false);

  // Ngăn lỗi null
  if (jobPost.loading || !jobPost) return <div>Đang tải...</div>;

  const handleApplyJob = async () => {
    try {
      const payload = {
        jobPostId: jobPost.data.id,
      };
      // gọi API backend đăng ký
      const res = await applyJob(payload);
      const application = res.result;

      jobPost.setData((prev) => ({
        ...prev,
        application: {
          jobPostId: application.jobPostId,
          status: application.status,
        },
      }));
      toast.success("ứng tuyển thành công");
      setOpenApplyJobDialog(false);
    } catch (error) {
      toast.error(error.response?.data?.message || "Ứng tuyển thất bại!");
    }
  };

  const handleSaveJob = async () => {
    try {
      const res = await saveJob(jobPost.data.id);
      const isSaved = res.result.saved;
      jobPost.setData((prev) => ({
        ...prev,
        isSaved,
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
        jobPost={jobPost.data}
        setJobPost={jobPost.setData}
        setOpenApplyJobDialog={setOpenApplyJobDialog}
        onSaveJobPost={handleSaveJob}
      />
      <div className="bg-white rounded-2xl p-5">
        <DescriptionSection jobPost={jobPost.data} />
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
