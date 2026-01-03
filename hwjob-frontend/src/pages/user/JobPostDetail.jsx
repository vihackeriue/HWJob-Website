import React, {useState} from "react";

import {useParams} from "react-router-dom";
import {useDetail} from "../../hooks/useDetail";
import {getJobPostById, saveJob} from "../../services/jobPostService";
import ApplyJobDialog from "../../components/dialog/ApplyJobDialog";
import {toast} from "react-toastify";
import {OverviewSection} from "../../components/sections/common/jobPostDetail/OverviewSection";
import DescriptionSection from "../../components/sections/common/jobPostDetail/DescriptionSection";
import {applyJob} from "../../services/applicationService";
import WorkSection from "../../components/sections/candidate/JobPostDetail/WorkSection";

const JobPostDetail = () => {
    const {id} = useParams();
    const {data: jobPostData, loading, setData} = useDetail(getJobPostById, id);

    const [openApplyJobDialog, setOpenApplyJobDialog] = useState(false);

    // Ngăn lỗi null
    if (loading || !jobPostData) return <div>Đang tải...</div>;

    const handleApplyJob = async () => {
        try {
            const payload = {
                jobPostId: jobPostData.id,
            };
            // gọi API backend đăng ký
            const res = await applyJob(payload);
            const application = res.result;

            setData((prev) => ({
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
            const res = await saveJob(jobPostData.id);
            const isSaved = res.result.saved;
            setData((prev) => ({
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
                jobPost={jobPostData}
                setJobPost={setData}
                setOpenApplyJobDialog={setOpenApplyJobDialog}
                onSaveJobPost={handleSaveJob}
            />
            {jobPostData.work && (
                <WorkSection work={jobPostData.work} setJobPost={setData}/>
            )}
            <div className="bg-white rounded-2xl p-5">
                <DescriptionSection jobPost={jobPostData}/>
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
