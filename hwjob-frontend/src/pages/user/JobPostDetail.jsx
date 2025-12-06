import React, { useEffect, useState } from "react";
import { GiMoneyStack } from "react-icons/gi";
import PrimaryButton from "../../components/ui/button/PrimaryButton";
import { IoEarth, IoPeopleOutline } from "react-icons/io5";
import SecondTitle from "../../components/ui/title/SecondTitle";
import { IoIosHeartEmpty, IoMdHeart } from "react-icons/io";
import { HiOutlineCalendarDateRange } from "react-icons/hi2";
import PrimaryTitle from "../../components/ui/title/PrimaryTitle";
import { LiaIndustrySolid } from "react-icons/lia";
import { CiLocationOn } from "react-icons/ci";
import { SiLevelsdotfyi } from "react-icons/si";
import { FaPeopleCarryBox } from "react-icons/fa6";
import { formatDate } from "../../utils/date";
import { useParams } from "react-router-dom";
import { useDetail } from "../../hooks/useDetail";
import { applyJob, getJobPostById } from "../../services/jobPostService";

import useAuth from "../../hooks/useAuth";
import ApplyJobDialog from "../../components/dialog/ApplyJobDialog";
import { ROLES } from "../../config/roles";

const JobPostDetail = () => {
  const { id } = useParams();
  const { data, loading } = useDetail(getJobPostById, id);
  const { auth } = useAuth();
  const [openApplyJobDialog, setOpenApplyJobDialog] = useState(false);
  const [jobPost, setJobPost] = useState(null);

  useEffect(() => {
    if (data) setJobPost(data);
  }, [data]);
  // Ngăn lỗi null
  if (loading || !jobPost) return <div>Đang tải...</div>;

  const hasRole = (role) => {
    return auth?.roles?.includes(role);
  };
  const renderActionButtons = () => {
    // Nếu là ứng viên (CANDIDATE)
    if (hasRole(ROLES.CANDIDATE)) {
      return (
        <div className="flex gap-3">
          {jobPost.isApplied ? (
            <button className="px-4 py-2 bg-gray-300 rounded-lg cursor-not-allowed">
              Đã ứng tuyển
            </button>
          ) : (
            <PrimaryButton onClick={() => setOpenApplyJobDialog(true)}>
              Ứng tuyển ngay
            </PrimaryButton>
          )}

          {jobPost.isSaved ? (
            <button className="flex gap-1 items-center px-3 py-2 rounded-lg text-brightOrange border border-brightOrange">
              <IoMdHeart className="size-5" />
              <span>Hủy lưu tin</span>
            </button>
          ) : (
            <button className="flex gap-1 items-center px-3 py-2 rounded-lg text-gray-900 border border-gray-300">
              <IoIosHeartEmpty className="size-5 text-gray-500" />
              <span>Lưu tin</span>
            </button>
          )}
        </div>
      );
    }

    // Nếu là nhà tuyển dụng và đây là bài đăng của chính họ
    if (hasRole(ROLES.RECRUITER) && jobPost.recruiterName === auth.username) {
      return (
        <div className="flex gap-3">
          <PrimaryButton>Chỉnh sửa bài đăng</PrimaryButton>
        </div>
      );
    }

    return null; // Nếu recruiter xem bài của người khác thì ko show gì
  };
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

  // const handleSaveJob = async () => {};
  return (
    <div className="flex flex-col gap-3 mt-10">
      <div className="flex gap-3 ">
        <div className="flex-3 bg-white rounded-2xl p-3 flex flex-col gap-3">
          <div className="flex gap-5">
            <img
              src={jobPost.recruiter.imageUrl}
              alt={jobPost.recruiter.fullName}
              className="size-32 rounded-2xl border border-gray-300 p-2"
            />
            <div className="flex flex-col gap-3">
              <h1 className="text-md font-semibold line-clamp-2">
                {jobPost.title}
              </h1>
              <h1 className="text-md font-semibold line-clamp-2"></h1>
              <p className="text-lg">
                Nhà tuyển dụng:
                <span className="font-semibold">
                  {jobPost.recruiter.fullName}
                </span>
              </p>
            </div>
          </div>
          <div className="flex gap-3 justify-end">{renderActionButtons()}</div>
        </div>
        <div className="flex-1 flex flex-col gap-3 bg-white rounded-2xl p-3">
          <InfoCard
            icon={<GiMoneyStack className="size-12 text-teal-600" />}
            label="Lương"
            value={`${jobPost.salary} / ${jobPost.salaryType}`}
          />
          <InfoCard
            icon={<IoPeopleOutline className="size-12 text-teal-600" />}
            label="Số lượng"
            value={jobPost.quantity}
          />
          <InfoCard
            icon={
              <HiOutlineCalendarDateRange className="size-12 text-teal-600" />
            }
            label="Hạn ứng tuyển"
            value={formatDate(jobPost.endedTime)}
          />
        </div>
      </div>

      <div className="bg-white rounded-2xl p-5">
        <PrimaryTitle>Chi tiết việc làm</PrimaryTitle>
        <div
          className="prose max-w-none p-3"
          dangerouslySetInnerHTML={{
            __html: jobPost.description,
          }}
        />
        <div className="flex flex-col gap-3 bg-white rounded-2xl p-3">
          <PrimaryTitle>Thông tin chung</PrimaryTitle>
          <div className="grid grid-cols-2 gap-3">
            <InfoCard
              icon={
                <HiOutlineCalendarDateRange className="size-12 text-teal-600" />
              }
              label="Ngày đăng"
              value={formatDate(jobPost.createdAt)}
            />
            <InfoCard
              icon={<IoEarth className="size-12 text-teal-600" />}
              label="Trạng thái"
              value={jobPost.status}
            />
            <InfoCard
              icon={<LiaIndustrySolid className="size-12 text-teal-600" />}
              label="Ngành"
              value={jobPost.industry}
            />
            <InfoCard
              icon={<CiLocationOn className="size-12 text-teal-600" />}
              label="Khu vực"
              value={jobPost.region}
            />
            <InfoCard
              icon={<SiLevelsdotfyi className="size-12 text-teal-600" />}
              label="Cấp độ"
              value={jobPost.level}
            />
            <InfoCard
              icon={<FaPeopleCarryBox className="size-12 text-teal-600" />}
              label="Loại công việc"
              value={jobPost.jobType}
            />
          </div>
        </div>
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

const InfoCard = ({ icon, label, value }) => {
  return (
    <div className="flex flex-row gap-3 items-center px-2 py-1 border border-gray-300 rounded-xl bg-lightGrayishBlue">
      {icon}
      <div>
        <p>{label}</p>
        <p className="font-semibold">{value}</p>
      </div>
    </div>
  );
};
