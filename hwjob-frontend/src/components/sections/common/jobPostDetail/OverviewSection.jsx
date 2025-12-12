import React from "react";
import { hasRole } from "../../../../utils/permission";
import { ROLES } from "../../../../config/roles";
import classNames from "classnames";
import useAuth from "../../../../hooks/useAuth";
import InfoCard from "../../../ui/cards/InfoCard";
import { GiMoneyStack } from "react-icons/gi";
import { IoPeopleOutline } from "react-icons/io5";
import { HiOutlineCalendarDateRange } from "react-icons/hi2";
import { formatDate } from "../../../../utils/date";
import PrimaryButton from "../../../ui/button/PrimaryButton";

export const OverviewSection = ({
  jobPost,
  onSaveJobPost,
  setOpenApplyJobDialog,
}) => {
  const { auth } = useAuth();
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

          <button
            onClick={onSaveJobPost}
            className={classNames(
              "flex gap-1 items-center px-3 py-2 rounded-lg border",
              {
                "text-brightOrange border-brightOrange": jobPost.isSaved,
                "text-gray-900 border-gray-300": !jobPost.isSaved,
              }
            )}
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
          </button>
        </div>
      );
    }
    // Nếu là nhà tuyển dụng và đây là bài đăng của chính họ
    if (hasRole(auth, ROLES.RECRUITER) && jobPost.recruiter.id === auth.id) {
      return (
        <div className="flex gap-3">
          <PrimaryButton>Chỉnh sửa bài đăng</PrimaryButton>
        </div>
      );
    }

    return null; // Nếu recruiter xem bài của người khác thì ko show gì
  };
  return (
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
              Nhà tuyển dụng:{" "}
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
  );
};
