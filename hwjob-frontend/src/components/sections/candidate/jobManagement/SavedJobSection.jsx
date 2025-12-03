import React from "react";
import { JobPostListSection } from "../../JobPostListSection";
import { getSavedJobPosts } from "../../../../services/jobPostService";
import { useList } from "../../../../hooks/useList";

export const SavedJobSection = () => {
  const savedJob = useList(getSavedJobPosts);

  return (
    <JobPostListSection
      jobPosts={savedJob.data}
      pagination={{
        page: savedJob.page,
        totalPages: savedJob.totalPages,
      }}
    />
  );
};
