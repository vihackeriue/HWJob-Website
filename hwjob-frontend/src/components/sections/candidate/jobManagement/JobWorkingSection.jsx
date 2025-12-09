import React from "react";
import { JobPostListSection } from "../../JobPostListSection";
import { useList } from "../../../../hooks/useList";
import { getWorkingJobPosts } from "../../../../services/jobPostService";

export const JobWorkingSection = () => {
  const jobWorking = useList(getWorkingJobPosts);
  return (
    <JobPostListSection
      jobPosts={jobWorking.data}
      pagination={{
        page: jobWorking.page,
        totalPages: jobWorking.totalPages,
      }}
    />
  );
};
