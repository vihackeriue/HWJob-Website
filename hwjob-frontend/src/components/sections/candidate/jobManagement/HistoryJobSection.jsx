import React from "react";

export const HistoryJobSection = ({ jobPosts }) => {
  return (
    <JobPostListSection
      jobPosts={jobPosts.data}
      pagination={{
        page: jobPosts.page,
        totalPages: jobPosts.totalPages,
      }}
    />
  );
};
