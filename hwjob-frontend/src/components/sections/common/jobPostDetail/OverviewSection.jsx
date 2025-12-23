import React, { useState } from "react";
import { hasRole } from "../../../../utils/permission";

import classNames from "classnames";
import useAuth from "../../../../hooks/useAuth";
import InfoCard from "../../../ui/cards/InfoCard";
import { GiMoneyStack } from "react-icons/gi";
import { IoPeopleOutline, IoPersonOutline } from "react-icons/io5";
import { HiOutlineCalendarDateRange } from "react-icons/hi2";
import { formatDate } from "../../../../utils/date";
import PrimaryButton from "../../../ui/button/PrimaryButton";
import { IoIosHeartEmpty, IoMdHeart } from "react-icons/io";
import { ROLES } from "../../../../constants/roles";
import { useUpdateApplicationStatus } from "../../../../hooks/useUpdateApplicationStatus";
import { STATUS_APPLICATION_MAP } from "../../../../constants/statusApplication";
import { CiEdit } from "react-icons/ci";
import WorkOverviewDialog from "../../../dialog/WorkOverviewDialog";
export const OverviewSection = ({
  jobPost,
  setJobPost,
  onSaveJobPost,
  setOpenApplyJobDialog,
}) => {
  const { auth } = useAuth();
  const [openWorkOverviewDialog, setOpenWorkOverviewDialog] = useState(false);

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
        <div className="flex gap-3">
          <PrimaryButton>
            <CiEdit size={18} />
            Chỉnh sửa bài đăng
          </PrimaryButton>
        </div>
      );
    }

    return null;
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
          <div className="flex flex-col gap-3">
            <h1 className="text-md font-semibold line-clamp-2">
              {jobPost.title}
            </h1>
            <h1 className="text-md font-semibold line-clamp-2"></h1>
            <p className="flex items-center gap-1">
              <IoPersonOutline />
              <span className="font-medium">Nhà tuyển dụng:</span>{" "}
              {jobPost.recruiter.fullName}
            </p>
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
    </div>
  );
};
