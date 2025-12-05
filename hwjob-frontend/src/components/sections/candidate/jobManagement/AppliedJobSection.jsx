import React from "react";
import { JobPostListSection } from "../../JobPostListSection";
import { useList } from "../../../../hooks/useList";
import { getAppliedJobPosts } from "../../../../services/jobPostService";

export const AppliedJobSection = () => {
  const appliedJobPosts = useList(getAppliedJobPosts);
  return (
    <JobPostListSection
      jobPosts={appliedJobPosts.data}
      pagination={{
        page: appliedJobPosts.page,
        totalPages: appliedJobPosts.totalPages,
      }}
    />
  );
};
