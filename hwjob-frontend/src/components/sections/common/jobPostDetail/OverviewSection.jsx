import React, { useState } from "react";
import { hasRole } from "../../../../utils/permission";

import classNames from "classnames";
import useAuth from "../../../../hooks/useAuth";
import InfoCard from "../../../ui/cards/InfoCard";
import { GiMoneyStack } from "react-icons/gi";
import {
  IoEyeOutline,
  IoPeopleOutline,
  IoPersonOutline,
  IoStar,
} from "react-icons/io5";
import { HiOutlineCalendarDateRange } from "react-icons/hi2";
import { formatDate } from "../../../../utils/date";
import PrimaryButton from "../../../ui/button/PrimaryButton";
import { IoIosHeartEmpty, IoMdHeart, IoMdTrendingUp } from "react-icons/io";
import { ROLES } from "../../../../constants/roles";
import { useUpdateApplicationStatus } from "../../../../hooks/useUpdateApplicationStatus";
import { STATUS_APPLICATION_MAP } from "../../../../constants/statusApplication";
import { CiEdit } from "react-icons/ci";
import WorkOverviewDialog from "../../../dialog/WorkOverviewDialog";
import UpdateJobPostDialog from "../../../dialog/recruiter/UpdateJobPostDialog.jsx";
import { FaMoneyBillTrendUp } from "react-icons/fa6";
import BoostJobPostDialog from "../../../dialog/recruiter/BoostJobPostDialog.jsx";
import { PiClockCountdownFill } from "react-icons/pi";

export const OverviewSection = ({
  jobPost,
  setJobPost,
  onSaveJobPost,
  setOpenApplyJobDialog,
}) => {
  const { auth } = useAuth();
  const [openWorkOverviewDialog, setOpenWorkOverviewDialog] = useState(false);
  const [openUpdateJobPostDialog, setOpenUpdateJobPostDialog] = useState(false);
  const [openBoostJobPostDialog, setOpenBoostJobPostDialog] = useState(false);

  const { updateStatus } = useUpdateApplicationStatus();

  const renderActionButtons = () => {
    /** ===== CANDIDATE ===== */
    if (hasRole(auth, ROLES.CANDIDATE)) {
      return (
        <div className="flex gap-3 items-center">
          {/* ===== CHƯA ỨNG TUYỂN ===== */}
          {!jobPost.application && (
            <PrimaryButton onClick={() => setOpenApplyJobDialog(true)}>
              Ứng tuyển ngay
            </PrimaryButton>
          )}

          {/* ===== ĐÃ ỨNG TUYỂN ===== */}
          {jobPost.application &&
            (() => {
              const statusConfig =
                STATUS_APPLICATION_MAP[jobPost.application.status];

              if (!statusConfig) return null;

              return (
                <>
                  {/* Status badge */}
                  <span
                    className={classNames(
                      "px-4 py-2 rounded-lg text-lg font-medium",
                      statusConfig.className
                    )}
                  >
                    {statusConfig.name}
                  </span>

                  {/* Candidate actions */}
                  {statusConfig.actions?.CANDIDATE?.map((action) => (
                    <PrimaryButton
                      key={action.to}
                      variant={action.variant}
                      onClick={() => {
                        setOpenWorkOverviewDialog(true);
                      }}
                    >
                      {action.label}
                    </PrimaryButton>
                  ))}
                </>
              );
            })()}
          {/* ===== SAVE JOB ===== */}
          <PrimaryButton
            onClick={onSaveJobPost}
            variant="outline"
            className={classNames("flex gap-1 items-center", {
              "text-brightOrange border-brightOrange": jobPost.isSaved,
              "text-gray-900 border-gray-300": !jobPost.isSaved,
            })}
          >
            {jobPost.isSaved ? (
              <>
                <IoMdHeart className="size-5" />
                <span>Hủy lưu tin</span>
              </>
            ) : (
              <>
                <IoIosHeartEmpty className="size-5 text-gray-500" />
                <span>Lưu tin</span>
              </>
            )}
          </PrimaryButton>
        </div>
      );
    }

    /** ===== RECRUITER (CHỦ BÀI ĐĂNG) ===== */
    if (hasRole(auth, ROLES.RECRUITER) && jobPost.recruiter.id === auth.id) {
      return (
        <div className="flex gap-3 items-center">
          {(() => {
            const remaining = getRemainingBoostTime(jobPost.boostExpiredAt);
            if (!remaining) return null;

            return (
              <p className="flex gap-1 items-center text-md border border-teal-600 px-2 py-2 font-medium rounded-lg">
                <IoMdTrendingUp className="size-6 text-teal-500" />
                Còn {remaining.days} ngày {remaining.hours} giờ{" "}
                {remaining.minutes} phút
              </p>
            );
          })()}
          <PrimaryButton
            onClick={() => setOpenBoostJobPostDialog(true)}
            variant="outline"
          >
            <FaMoneyBillTrendUp />
            {jobPost.isBoosted ? "Gia hạn thêm" : "Lên xu hướng"}
          </PrimaryButton>
          <PrimaryButton onClick={() => setOpenUpdateJobPostDialog(true)}>
            <CiEdit size={18} />
            Chỉnh sủa bài đăng
          </PrimaryButton>
        </div>
      );
    }
    return null;
  };

  const getRemainingBoostTime = (boostExpiredAt) => {
    if (!boostExpiredAt) return null;

    const now = new Date();
    const expired = new Date(boostExpiredAt);
    const diffMs = expired - now;

    if (diffMs <= 0) return null;

    const totalMinutes = Math.floor(diffMs / 1000 / 60);
    const days = Math.floor(totalMinutes / (60 * 24));
    const hours = Math.floor((totalMinutes % (60 * 24)) / 60);
    const minutes = totalMinutes % 60;

    return { days, hours, minutes };
  };
  return (
    <div className="flex gap-3 ">
      <div className="relative flex-3 bg-white rounded-2xl p-3 flex flex-col gap-3 ">
        <div className="flex gap-5">
          <img
            src={jobPost.recruiter.imageUrl}
            alt={jobPost.recruiter.fullName}
            className="size-32 rounded-2xl border border-gray-300 p-2"
          />
          <div className="flex flex-col gap-2">
            {/* TITLE + BADGE */}
            <h3 className="text-base font-semibold text-gray-900 line-clamp-2">
              {jobPost.title}
            </h3>
            {jobPost.isBoosted && jobPost.boostExpiredAt && (
              <span className="flex items-center whitespace-nowrap rounded-md bg-orange-100 px-2 py-1 text-xs font-semibold text-brightOrange shadow-sm">
                <IoStar size={12} />
                Bài đăng nổi bật
              </span>
            )}
            <div className="flex gap-3">
              {/* RECRUITER */}
              <p className="flex items-center gap-2 text-lg px-2 py-0.5 border border-amber-300 bg-amber-50 rounded-lg">
                <IoPersonOutline size={16} />
                <span className="font-medium">
                  {jobPost.recruiter.fullName.length > 30
                    ? jobPost.recruiter.fullName.slice(0, 30) + "…"
                    : jobPost.recruiter.fullName}
                </span>
              </p>
              {/* META */}
              <div className="flex items-center gap-4 text-sm text-gray-500">
                <div className="flex items-center gap-1">
                  <IoEyeOutline size={16} />
                  <span>{jobPost.viewCount.toLocaleString()} lượt xem</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="absolute right-3 bottom-3 flex gap-3">
          {renderActionButtons()}
        </div>
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

      <WorkOverviewDialog
        open={openWorkOverviewDialog}
        jobPostId={jobPost.id}
        onClose={() => setOpenWorkOverviewDialog(false)}
        onDecide={(status) => {
          updateStatus({
            jobPostId: jobPost.id,
            status,
            onSuccess: (newStatus) => {
              setJobPost((prev) => ({
                ...prev,
                application: {
                  ...prev.application,
                  status: newStatus,
                },
              }));
              setOpenWorkOverviewDialog(false);
            },
          });
        }}
      />

      <UpdateJobPostDialog
        open={openUpdateJobPostDialog}
        onClose={() => setOpenUpdateJobPostDialog(false)}
        jobPost={jobPost}
        onUpdate={(updatedJobPost) => {
          setJobPost((prev) => ({
            ...prev,
            ...updatedJobPost,
            // Cập nhật lại các object con nếu API trả về full object
            industry: updatedJobPost.industry || prev.industry,
            level: updatedJobPost.level || prev.level,
            jobType: updatedJobPost.jobType || prev.jobType,
            region: updatedJobPost.region || prev.region,
            skills: updatedJobPost.skills || prev.skills,
          }));
        }}
      />
      <BoostJobPostDialog
        open={openBoostJobPostDialog}
        onClose={() => setOpenBoostJobPostDialog(false)}
        jobId={jobPost.id}
        onSuccess={() => {
          // Option 1: refetch từ server
          // refetchJobPost();

          // Option 2: cập nhật local state (nhẹ hơn)
          setJobPost((prev) => ({
            ...prev,
            isBoosted: true,
            boostExpiredAt: new Date(
              new Date(prev.boostExpiredAt || Date.now()).getTime() + 1
            ).toISOString(),
          }));
        }}
      />
    </div>
  );
};
