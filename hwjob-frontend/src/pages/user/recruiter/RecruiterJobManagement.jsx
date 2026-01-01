import { TabGroup, TabPanel, TabPanels } from "@headlessui/react";
import React from "react";
import MenuTabListVertical from "../../../components/ui/MenuTabListVertical";
import MyJobPostSection from "../../../components/sections/recruiter/jobManagement/MyJobPostSection";
import QuantityStatisticsCard from "../../../components/ui/cards/QuantityStatisticsCard";
import { CiLocationOn } from "react-icons/ci";
import ApplicantListSection from "../../../components/sections/recruiter/jobManagement/ApplicantListSection";
import FreelancerListSection from "../../../components/sections/recruiter/jobManagement/FreelancerListSection";
import StatisticSection from "../../../components/sections/recruiter/jobManagement/StatisticSection";

const JOB_POST_MANAGEMENT_MENUS = [
  { key: "all", label: "Quản lý bài đăng" },
  { key: "applications", label: "Quản lý ứng viên" },
  { key: "working", label: "Quản lý công việc" },
  { key: "statistic", label: "Thống kê" },
];
const RecruiterJobManagement = () => {
  return (
    <TabGroup>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 my-5">
        {/* Sidebar */}
        <div className="flex flex-col gap-3 col-span-1">
          <MenuTabListVertical
            menus={JOB_POST_MANAGEMENT_MENUS}
            title={"Quản lý công việc"}
          />
        </div>
        <div className="col-span-2 flex flex-col gap-3 ">
          <TabPanels>
            <TabPanel>
              <MyJobPostSection />
            </TabPanel>
            <TabPanel>
              <ApplicantListSection />
            </TabPanel>
            <TabPanel>
              <FreelancerListSection />
            </TabPanel>
            <TabPanel>
              <StatisticSection />
            </TabPanel>
          </TabPanels>
        </div>
      </div>
    </TabGroup>
  );
};

export default RecruiterJobManagement;
