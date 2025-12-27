import React from "react";
import { JobPostListSection } from "../../JobPostListSection";
import { useList } from "../../../../hooks/useList";

import { getWorkOfCandidate } from "../../../../services/workService";

export const JobWorkingSection = () => {
  const jobWorking = useList(getWorkOfCandidate);
  return (
    <JobPostListSection
      jobPosts={jobWorking.data}
      pagination={{
        page: jobWorking.page,
        totalPages: jobWorking.totalPages,
        setPage: jobWorking.setPage,
      }}
    />
  );
};
