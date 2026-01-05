import React from "react";
import { Link } from "react-router-dom";

import useAuth from "../../../hooks/useAuth";
import { hasRole } from "../../../utils/permission";
import { ROLES } from "../../../constants/roles";

import { STATUS_APPLICATION_MAP } from "../../../constants/statusApplication";
import { STATUS_WORK_MAP } from "../../../constants/statusWork";
import { SiTrendmicro } from "react-icons/si";

const JobPostCard = ({ jobPost }) => {
  const { auth } = useAuth();

  /* ===================== URL ===================== */
  const getJobPostDetailUrl = () => {
    if (hasRole(auth, ROLES.RECRUITER) && jobPost.recruiter.id === auth.id) {
      return `/recruiter/job-post/${jobPost.id}`;
    }
    return `/job-post/${jobPost.id || jobPost.jobPostId}`;
  };

  /* ===================== STATUS ===================== */
  const getStatusConfig = () => {
    if (jobPost.workStatus) {
      return STATUS_WORK_MAP[jobPost.workStatus];
    }
    if (jobPost.applicationStatus) {
      return STATUS_APPLICATION_MAP[jobPost.applicationStatus];
    }
    return null;
  };

  const statusConfig = getStatusConfig();

  return (
    <div className="flex gap-3 pr-2 bg-lightGrayishBlue dark:bg-stoneBrown-900 rounded-2xl relative hover:shadow border border-gray-300">
      {/* ===================== IMAGE ===================== */}
      <img
        src={jobPost.recruiter.imageUrl}
        alt={jobPost.recruiter.fullName}
        className="size-32 rounded-2xl"
      />

      {/* ===================== INFO ===================== */}
      <div className="py-2 flex-1">
        <Link
          to={getJobPostDetailUrl()}
          className="text-md font-semibold hover-bright-orange line-clamp-2"
        >
          {jobPost.title}
        </Link>

        <p className="dark:text-gray-300">{jobPost.recruiter.fullName}</p>

        <div className="flex gap-1 flex-wrap dark:text-amber-500">
          <p className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            Số lượng: {jobPost.quantity}
          </p>
          <div className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {jobPost.region}
          </div>
          <div className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {jobPost.industry}
          </div>
        </div>
      </div>

      {/* ===================== STATUS & ACTION ===================== */}
      {statusConfig && (
        <div className="absolute right-3 bottom-3 flex flex-col items-end gap-2">
          {/* Badge */}
          <span
            className={`px-3 py-1 text-sm font-medium rounded-full ${statusConfig.className}`}
          >
            {statusConfig.name}
          </span>

          {/* Actions */}
          {/* {actions.length > 0 && (
            <div className="flex gap-1">
              {actions.map((action) => (
                <PrimaryButton
                  key={action.to}
                  variant={action.variant}
                  onClick={() => {
                    setConfirmAction(action);
                    setOpenConfirmDialog(true);
                  }}
                >
                  {action.label}
                </PrimaryButton>
              ))}
            </div>
          )} */}
        </div>
      )}
      {jobPost.isBoosted && (
        <div className="absolute flex items-center gap-1 top-3 right-3 bg-brightOrange text-white text-md px-3 py-1 rounded-full">
          <SiTrendmicro /> <span>Tin đang nổi</span>
        </div>
      )}
    </div>
  );
};

export default JobPostCard;
