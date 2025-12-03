import React from "react";
import { JobPostListSection } from "../../JobPostListSection";

export const AppliedJobSection = ({ jobPosts }) => {
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
