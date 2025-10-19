import React from "react";

import { Outlet } from "react-router-dom";

import ProfileCard from "../../../components/ui/cards/ProfileCard";
import ProfileSidebar from "../../../components/sections/profile/ProfileSidebar";
import ProgressBar from "../../../components/ui/ProgressBar";

const user = {
  name: "Wain RP",
  username: "wainrp",
  avatar:
    "https://th.bing.com/th?q=IPhone+Avatar&w=120&h=120&c=1&rs=1&qlt=90&r=0&cb=1&dpr=1.3&pid=InlineBlock&mkt=en-WW&cc=VN&setlang=en&adlt=moderate&t=1&mw=247",
  verified: true,
  profileCompletion: 20,
};
const MyProfile = () => {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 my-5">
      <div className="flex flex-col gap-3 col-span-1">
        <ProfileCard user={user} />

        <div className="bg-white dark:bg-stoneBrown-900 p-2 rounded-2xl">
          <ProgressBar title={"Mức độ hòa thiện hồ sơ"} value={80} />
        </div>
        <ProfileSidebar />
      </div>
      <div className="col-span-2  flex flex-col gap-3 ">
        <Outlet />
      </div>
    </div>
  );
};

export default MyProfile;
