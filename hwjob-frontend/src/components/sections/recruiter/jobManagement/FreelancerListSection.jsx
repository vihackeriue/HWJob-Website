import React from "react";
import { FaStar } from "react-icons/fa";
import {
  IoCalendarOutline,
  IoCashOutline,
  IoBriefcaseOutline,
  IoMailOutline,
} from "react-icons/io5";

import { getAllFreelancerOfRecruiter } from "../../../../services/workService";
import { useList } from "../../../../hooks/useList";
import Pagination from "../../../ui/pagination/Pagination";
import { STATUS_WORK_MAP } from "../../../../constants/statusWork";
import PrimaryTitle from "../../../ui/title/PrimaryTitle";

const formatDate = (date) =>
  date ? new Date(date).toLocaleDateString("vi-VN") : "--";

const formatSalary = (value) =>
  value != null ? value.toLocaleString("vi-VN") + " đ" : "--";

const FreelancerListSection = () => {
  const works = useList(getAllFreelancerOfRecruiter);

  return (
    <div className="bg-white rounded-2xl p-3 space-y-3">
      {/* Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-2">
        <PrimaryTitle>Quản lý công việc</PrimaryTitle>
        <span className="text-sm text-gray-500">
          {works.totalElements || works.data?.length || 0} công việc
        </span>
      </div>

      {/* List */}
      <div className="space-y-4">
        {works.data?.map((item) => {
          const status = STATUS_WORK_MAP[item.status];

          return (
            <div
              key={item.workId}
              className="rounded-2xl bg-lightGrayishBlue border border-gray-300 p-5 hover:shadow-lg transition-all duration-200"
            >
              <div className="flex flex-col sm:flex-row gap-4 sm:gap-6">
                {/* Avatar */}
                <div className="flex-shrink-0">
                  <img
                    src={item.imageUrl}
                    alt={item.fullName}
                    className="w-16 h-16 rounded-full object-cover ring-2 ring-gray-100"
                  />
                </div>

                {/* Content */}
                <div className="flex-1 flex flex-col justify-between gap-2">
                  {/* Header: Name + Status */}
                  <div className="flex flex-wrap justify-between items-start gap-2">
                    <div className="space-y-1 min-w-0">
                      <p className="font-semibold text-lg line-clamp-1">
                        {item.fullName}
                      </p>
                      <div className="flex items-center gap-1 text-sm text-gray-500 line-clamp-1">
                        <IoMailOutline size={14} />
                        {item.email}
                      </div>
                    </div>

                    {status && (
                      <span
                        className={`px-3 py-1 rounded-lg text-md font-semibold whitespace-nowrap ${status.className}`}
                      >
                        {status.name}
                      </span>
                    )}
                  </div>

                  {/* Job */}
                  <div className="flex items-center gap-2 text-sm text-gray-600 mt-1">
                    <IoBriefcaseOutline size={16} />
                    <span className="font-medium line-clamp-1">
                      {item.jobPostTitle}
                    </span>
                  </div>

                  {/* Meta */}
                  <div className="flex flex-wrap gap-6 text-sm text-gray-500 mt-1">
                    <div className="flex items-center gap-2">
                      <IoCalendarOutline size={16} />
                      {formatDate(item.startTime)} → {formatDate(item.endTime)}
                    </div>
                    <div className="flex items-center gap-2">
                      <IoCashOutline size={16} />
                      {formatSalary(item.agreedSalary)} ({item.salaryType})
                    </div>
                  </div>

                  {/* Rating */}
                  {item.myReviewRating != null && (
                    <div className="flex items-center gap-1 text-yellow-500 mt-1">
                      <FaStar size={14} />
                      <span className="text-sm font-semibold">
                        {item.myReviewRating}/5
                      </span>
                    </div>
                  )}
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {/* Empty state */}
      {works.data?.length === 0 && (
        <p className="text-center text-gray-400 py-12">Chưa có công việc nào</p>
      )}

      {/* Pagination */}
      <div className="flex justify-center pt-4">
        <Pagination
          pagination={{
            page: works.page,
            totalPages: works.totalPages,
            setPage: works.setPage,
          }}
        />
      </div>
    </div>
  );
};

export default FreelancerListSection;
