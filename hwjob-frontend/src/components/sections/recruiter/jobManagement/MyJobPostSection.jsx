import React from "react";
import { JobPostListSection } from "../../JobPostListSection";
import { useList } from "../../../../hooks/useList";
import { getJobPostsOfRecruiter } from "../../../../services/jobPostService";

const MyJobPostSection = () => {
  const MyJobPosts = useList(getJobPostsOfRecruiter);
  return (
    <JobPostListSection
      jobPosts={MyJobPosts.data}
      pagination={{
        page: MyJobPosts.page,
        totalPages: MyJobPosts.totalPages,
        setPage: MyJobPosts.setPage,
      }}
    />
  );
};

export default MyJobPostSection;
