import React from "react";
import { useList } from "../../../../hooks/useList";
import { getAllApplicantOfRecruiter } from "../../../../services/applicationService";
import { STATUS_APPLICATION_MAP } from "../../../../constants/statusApplication";
import Pagination from "../../../ui/pagination/Pagination";
import { IoMailOutline } from "react-icons/io5";
import PrimaryTitle from "../../../ui/title/PrimaryTitle";

const ApplicantListSection = () => {
  const applications = useList(getAllApplicantOfRecruiter);

  return (
    <div className="bg-white rounded-2xl p-6 shadow-sm">
      <div className="mb-6">
        <PrimaryTitle>Danh sách ứng viên ứng tuyển</PrimaryTitle>
      </div>

      <div className="space-y-4">
        {applications.data?.map((item) => {
          const status = STATUS_APPLICATION_MAP[item.status];

          return (
            <div
              key={`${item.candidateId}-${item.jobPostId}`}
              className="flex gap-4 p-4 border border-gray-300 rounded-2xl hover:shadow-lg transition-all duration-200 bg-lightGrayishBlue"
            >
              {/* Avatar */}
              <div className="flex-shrink-0">
                <img
                  src={item.imageUrl}
                  alt={item.fullName}
                  className="w-16 h-16 rounded-full object-cover border-2 border-gray-200"
                />
              </div>

              {/* Content */}
              <div className="flex-1 flex flex-col justify-between">
                {/* Name + status */}
                <div className="flex justify-between items-start">
                  <div className="space-y-1">
                    <p className="font-semibold text-lg line-clamp-1">
                      {item.fullName}
                    </p>
                    <p className="text-sm text-gray-500 flex items-center gap-1">
                      <IoMailOutline size={14} />
                      {item.email}
                    </p>
                  </div>

                  {status && (
                    <span
                      className={`px-3 py-1 rounded-lg text-md font-medium whitespace-nowrap ${status.className}`}
                    >
                      {status.name}
                    </span>
                  )}
                </div>

                {/* Job title */}
                <p className="text-sm text-gray-600 mt-2 line-clamp-1">
                  <span className="font-medium">Bài đăng:</span>{" "}
                  {item.jobPostTitle}
                </p>

                {/* Note */}
                {status?.note?.RECRUITER && (
                  <p className="text-xs text-gray-400 italic mt-1">
                    {status.note.RECRUITER}
                  </p>
                )}

                {/* Actions */}
              </div>
            </div>
          );
        })}

        {applications.data?.length === 0 && (
          <p className="text-center text-gray-400 py-6">Chưa có ứng viên nào</p>
        )}
      </div>

      <div className="flex justify-center mt-6">
        <Pagination
          pagination={{
            page: applications.page,
            totalPages: applications.totalPages,
            setPage: applications.setPage,
          }}
        />
      </div>
    </div>
  );
};

export default ApplicantListSection;
