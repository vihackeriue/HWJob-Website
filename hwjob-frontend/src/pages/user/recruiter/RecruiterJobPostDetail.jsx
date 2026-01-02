import React, { useEffect, useState } from "react";
import { OverviewSection } from "../../../components/sections/common/jobPostDetail/OverviewSection";
import { useParams } from "react-router-dom";
import { getJobPostById } from "../../../services/jobPostService";
import { useDetail } from "../../../hooks/useDetail";
import DescriptionSection from "../../../components/sections/common/jobPostDetail/DescriptionSection";
import { TabGroup, TabPanel, TabPanels } from "@headlessui/react";
import MenuTabListHorizontal from "../../../components/ui/MenuTabListHorizontal";
import ApplicantListSection from "../../../components/sections/recruiter/jobPostDetail/ApplicantListSection";
import StaffListSection from "../../../components/sections/recruiter/jobPostDetail/StaffListSection";
import StatisticsSection from "../../../components/sections/recruiter/jobPostDetail/StatisticsSection";

const JOB_POST_MANAGEMENT_MENUS = [
  { key: "desc", label: "Mô tả chung" },
  { key: "applicantList", label: "Ứng viên đã nộp " },
  { key: "historyJob", label: "Đang làm việc" },
  { key: "stati", label: "Thống kê" },
];

const RecruiterJobPostDetail = () => {
  const { id } = useParams();
  const { data, loading } = useDetail(getJobPostById, id);
  const [jobPost, setJobPost] = useState(null);
  useEffect(() => {
    if (data) setJobPost(data);
  }, [data]);
  if (loading || !jobPost) return <div>Đang tải...</div>;
  return (
    <div className="flex flex-col gap-3 mt-10 ">
      <OverviewSection jobPost={jobPost} />
      <div className="">
        <TabGroup>
          <MenuTabListHorizontal menus={JOB_POST_MANAGEMENT_MENUS} />

          <TabPanels className="bg-white rounded-2xl p-5 mt-3 ">
            <TabPanel>
              <DescriptionSection jobPost={jobPost} />
            </TabPanel>
            <TabPanel>
              <ApplicantListSection jobPost={jobPost} />
            </TabPanel>
            <TabPanel>
              <StaffListSection jobPost={jobPost} />
            </TabPanel>
            <TabPanel>
              <StatisticsSection jobPostId={jobPost.id} />
            </TabPanel>
          </TabPanels>
        </TabGroup>
      </div>
    </div>
  );
};

export default RecruiterJobPostDetail;
