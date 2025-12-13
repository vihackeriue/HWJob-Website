import React from "react";

import ProfileCard from "../../components/ui/cards/ProfileCard";

import ProgressBar from "../../components/ui/ProgressBar";
import { PROFILE_USER_MENUS } from "../../constants/navigation";
import { TabGroup, TabPanel, TabPanels } from "@headlessui/react";
import OverviewSection from "../../components/sections/profile/OverviewSection";
import PersonalInfoSection from "../../components/sections/profile/PersonalInfoSection";
import SecurityInfoSection from "../../components/sections/profile/SecurityInfoSection";
import { EditSummarySection } from "../../components/sections/profile/EditSummarySection";
import MenuTabListVertical from "../../components/ui/MenuTabListVertical";

const user = {
  name: "Wain RP",
  username: "wainrp",
  verified: true,
  profileCompletion: 20,
};
const MyProfile = () => {
  return (
    <TabGroup>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 my-5">
        <div className="flex flex-col gap-3 col-span-1">
          <ProfileCard user={user} />

          <div className="bg-white dark:bg-stoneBrown-900 p-2 rounded-2xl">
            <ProgressBar
              title={"Mức độ hòa thiện hồ sơ"}
              value={user.profileCompletion}
            />
          </div>

          <MenuTabListVertical
            menus={PROFILE_USER_MENUS}
            title={"Quản lý công việc"}
          />
        </div>
        <TabPanels className="col-span-2  flex flex-col gap-3 ">
          <TabPanel>
            <OverviewSection />
          </TabPanel>
          <TabPanel>
            <PersonalInfoSection />
          </TabPanel>
          <TabPanel>
            <SecurityInfoSection />
          </TabPanel>
          <TabPanel>
            <EditSummarySection />
          </TabPanel>
        </TabPanels>
      </div>
    </TabGroup>
  );
};

export default MyProfile;
