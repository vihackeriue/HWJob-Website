import { TabGroup, TabPanel, TabPanels } from "@headlessui/react";
import React from "react";
import MenuTabListVertical from "../../../components/ui/MenuTabListVertical";
import MyJobPostSection from "../../../components/sections/recruiter/jobManagement/MyJobPostSection";
import QuantityStatisticsCard from "../../../components/ui/cards/QuantityStatisticsCard";
import { CiLocationOn } from "react-icons/ci";

const JOB_POST_MANAGEMENT_MENUS = [
  { key: "working", label: "Tất cả bài đăng" },
  { key: "appliedJob", label: "Việc làm đã ứng tuyển" },
  { key: "savedJob", label: "Việc làm đã lưu" },
  { key: "historyJob", label: "Lịch sử làm việc" },
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
          <div className="flex gap-4 w-full bg-dark-100 p-3 rounded-2xl shadow bg-white">
            <QuantityStatisticsCard
              stat={22}
              icon={<CiLocationOn className="size-12 text-teal-600" />}
              title={"Đã ứng tuyển"}
            />
            <QuantityStatisticsCard
              stat={22}
              icon={<CiLocationOn className="size-12 text-teal-600" />}
              title={"Tổng việc làm"}
            />
            <QuantityStatisticsCard
              stat={22}
              icon={<CiLocationOn className="size-12 text-teal-600" />}
              title={"Việc đã xong"}
            />
            <QuantityStatisticsCard
              stat={22}
              icon={<CiLocationOn className="size-12 text-teal-600" />}
              title={"Việc đã lưu"}
            />
          </div>
          <TabPanels>
            <TabPanel>
              <MyJobPostSection />
            </TabPanel>
            <TabPanel></TabPanel>
          </TabPanels>
        </div>
      </div>
    </TabGroup>
  );
};

export default RecruiterJobManagement;
