import React from "react";
import JobPostCard from "../ui/cards/JobPostCard";
import Pagination from "../ui/pagination/Pagination";

export const JobPostListSection = ({ jobPosts, pagination }) => {
  return (
    <div className="bg-white rounded-2xl p-3">
      <div className="flex flex-col gap-3">
        {jobPosts.map((job) => (
          <JobPostCard key={job.id} jobPost={job} />
        ))}
      </div>
      <Pagination pagination={pagination} />
    </div>
  );
};
