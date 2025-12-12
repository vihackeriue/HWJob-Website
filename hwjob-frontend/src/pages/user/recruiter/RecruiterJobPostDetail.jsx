import React, { useEffect, useState } from "react";
import { OverviewSection } from "../../../components/sections/common/jobPostDetail/OverviewSection";
import { useParams } from "react-router-dom";
import { getJobPostById } from "../../../services/jobPostService";
import { useDetail } from "../../../hooks/useDetail";
import DescriptionSection from "../../../components/sections/common/jobPostDetail/DescriptionSection";
import { MenuTablist } from "../../../components/ui/MenuTablist";
import { Tab, TabGroup, TabList, TabPanel, TabPanels } from "@headlessui/react";

const JOB_POST_MANAGEMENT_MENUS = [
  { key: "working", label: "Mô tả chung" },
  { key: "appliedJob", label: "Việc làm đã ứng tuyển" },
  { key: "savedJob", label: "Việc làm đã lưu" },
  { key: "historyJob", label: "Lịch sử làm việc" },
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
    <div className="flex flex-col gap-3 mt-10">
      <OverviewSection jobPost={jobPost} />
      <div className="bg-white rounded-2xl p-5">
        <TabGroup>
          <MenuTablist
            menus={JOB_POST_MANAGEMENT_MENUS}
            orientation="horizontal"
          />

          <TabPanels>
            <TabPanel>
              <DescriptionSection jobPost={jobPost} />
            </TabPanel>
          </TabPanels>
        </TabGroup>
      </div>
    </div>
  );
};

export default RecruiterJobPostDetail;
