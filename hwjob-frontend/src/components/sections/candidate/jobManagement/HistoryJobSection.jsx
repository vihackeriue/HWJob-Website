import React from "react";
import { useList } from "../../../../hooks/useList";
import { getHistoryJobPosts } from "../../../../services/jobPostService";
import { JobPostListSection } from "../../JobPostListSection";

export const HistoryJobSection = () => {
  const historyJobPosts = useList(getHistoryJobPosts);
  return (
    <JobPostListSection
      jobPosts={historyJobPosts.data}
      pagination={{
        page: historyJobPosts.page,
        totalPages: historyJobPosts.totalPages,
      }}
    />
  );
};
