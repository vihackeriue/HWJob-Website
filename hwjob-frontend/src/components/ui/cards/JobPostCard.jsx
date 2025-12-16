import React, { useState } from "react";
import { tGlobal } from "../../../utils/translator";
import { Link } from "react-router-dom";
import { hasRole } from "../../../utils/permission";

import useAuth from "../../../hooks/useAuth";
import { ROLES } from "../../../constants/roles";
import PrimaryButton from "../button/PrimaryButton";
import { STATUS_APPLICATION_MAP } from "../../../constants/statusApplication";
import ConfirmDialog from "../../dialog/common/ConfirmDialog";
const JobPostCard = ({ jobPost }) => {
  const { auth } = useAuth();
  const [openConfirmDialog, setOpenConfirmDialog] = useState(false);

  const getJobPostDetailUrl = () => {
    if (hasRole(auth, ROLES.RECRUITER) && jobPost.recruiter.id === auth.id) {
      return `/recruiter/job-post/${jobPost.id}`;
    }
    return `/job-post/${jobPost.id}`;
  };

  return (
    <div className="flex gap-3 pr-2 bg-lightGrayishBlue dark:bg-stoneBrown-900 rounded-2xl relative hover:shadow border border-gray-300">
      <img
        src={jobPost.recruiter.imageUrl}
        alt={jobPost.recruiter.fullName}
        className="size-32 rounded-2xl "
      />
      <div className="py-2">
        <Link
          to={getJobPostDetailUrl()}
          className="text-md font-semibold hover-bright-orange line-clamp-2 "
        >
          {jobPost.title}
        </Link>
        <p className="dark:text-gray-300">{jobPost.recruiter.fullName}</p>
        <div className="flex gap-1 dark:text-amber-500">
          <p className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {tGlobal("common.quantity")}: <span>{jobPost.quantity}</span>
          </p>
          <div className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {jobPost.region}
          </div>
          <div className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {jobPost.industry}
          </div>
        </div>
      </div>
      <div className="absolute right-3 bottom-3 flex flex-col items-end gap-2">
        {/* Đã ứng tuyển */}
        {jobPost.applicationStatus &&
          (() => {
            const statusConfig =
              STATUS_APPLICATION_MAP[jobPost.applicationStatus];

            if (!statusConfig) return null;

            return (
              <>
                {/* Status badge */}
                <span
                  className={`px-3 py-1 text-xs font-medium rounded-full ${statusConfig.className}`}
                >
                  {statusConfig.name}
                </span>
                {/* Candidate actions */}
                {/* <div className="flex gap-1">
                  {statusConfig.actions?.CANDIDATE?.map((action) => (
                    <PrimaryButton
                      key={action.to}
                      variant={action.variant}
                      onClick={() => {
                        setOpenConfirmDialog(true);
                      }}
                    >
                      {action.label}
                    </PrimaryButton>
                  ))}
                </div> */}
              </>
            );
          })()}
      </div>
      <ConfirmDialog
        open={openConfirmDialog}
        onClose={() => {
          setOpenConfirmDialog(false);
        }}
      />
    </div>
  );
};

export default JobPostCard;
