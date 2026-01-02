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

const formatDate = (date) =>
  date ? new Date(date).toLocaleDateString("vi-VN") : "--";

const formatSalary = (value) =>
  value != null ? value.toLocaleString("vi-VN") + " đ" : "--";

const FreelancerListSection = () => {
  const works = useList(getAllFreelancerOfRecruiter);

  return (
    <div className="space-y-5">
      {/* Title */}
      <div className="flex items-center justify-between">
        <h2 className="text-2xl font-semibold">Quản lý công việc</h2>
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
              className="bg-white rounded-2xl border border-gray-100 p-5 hover:shadow-md transition"
            >
              <div className="flex gap-5">
                {/* Avatar */}
                <img
                  src={item.imageUrl}
                  alt={item.fullName}
                  className="w-16 h-16 rounded-full object-cover ring-2 ring-gray-100"
                />

                {/* Content */}
                <div className="flex-1 space-y-3">
                  {/* Header */}
                  <div className="flex flex-wrap justify-between gap-3">
                    <div>
                      <p className="font-semibold text-lg leading-tight">
                        {item.fullName}
                      </p>
                      <div className="flex items-center gap-1 text-sm text-gray-500">
                        <IoMailOutline className="size-4" />
                        {item.email}
                      </div>
                    </div>

                    {status && (
                      <span
                        className={`px-3 py-1 rounded-lg text-xs font-semibold whitespace-nowrap ${status.className}`}
                      >
                        {status.name}
                      </span>
                    )}
                  </div>

                  {/* Job */}
                  <div className="flex items-center gap-2 text-sm">
                    <IoBriefcaseOutline className="size-4 text-gray-500" />
                    <span className="font-medium">{item.jobPostTitle}</span>
                  </div>

                  {/* Meta */}
                  <div className="flex flex-wrap gap-6 text-sm text-gray-600">
                    <div className="flex items-center gap-2">
                      <IoCalendarOutline className="size-4" />
                      {formatDate(item.startTime)} → {formatDate(item.endTime)}
                    </div>

                    <div className="flex items-center gap-2">
                      <IoCashOutline className="size-4" />
                      {formatSalary(item.agreedSalary)} ({item.salaryType})
                    </div>
                  </div>

                  {/* Rating */}
                  {item.myReviewRating != null && (
                    <div className="flex items-center gap-1 text-yellow-500">
                      <FaStar className="size-4" />
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

      {/* Empty */}
      {works.data?.length === 0 && (
        <p className="text-center text-gray-500 py-12">Chưa có công việc nào</p>
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
