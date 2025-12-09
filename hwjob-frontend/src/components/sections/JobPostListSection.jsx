import React from "react";
import JobPostCard from "../ui/cards/JobPostCard";
import Pagination from "../ui/pagination/Pagination";

export const JobPostListSection = ({ jobPosts, pagination }) => {
  return (
    <div className="bg-white rounded-2xl p-3 min-h-150">
      {jobPosts.length === 0 ? (
        <div className="text-center text-gray-500 py-10">
          Không có bài đăng tuyển dụng nào
        </div>
      ) : (
        <>
          <div className="flex flex-col gap-3">
            {jobPosts.map((job) => (
              <JobPostCard key={job.id} jobPost={job} />
            ))}
          </div>

          <div className="flex justify-center m-3">
            <Pagination pagination={pagination} />
          </div>
        </>
      )}
    </div>
  );
};
