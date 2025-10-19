import React from "react";

import classNames from "classnames";
import { Link, useLocation } from "react-router-dom";
import { HiOutlineLogout } from "react-icons/hi";
import { FcBullish } from "react-icons/fc";
import {
  ADMIN_SIDEBAR_FOOTER_LINKS,
  ADMIN_SIDEBAR_LINKS,
} from "../../../constants/navigation";
import MenuItemLink from "../../ui/MenuItemLink";
const linkClasses =
  "flex items-center gap-2 font-light px-3 py-2 hover:bg-neutral-700 hover:no-underline active:bg-neutral-600 rounded-sm text-base";

function SideBar() {
  return (
    <div className="fixed top-0 left-0 flex flex-col bg-darkBlue w-60  h-screen p-3 ">
      <div className="flex items-center gap-2 px-1 py-3">
        <FcBullish fontSize={24} />
        <span className="text-neutral-100 font-bold text-lg">HWJOB</span>
      </div>
      <div className="flex-1 py-8 flex flex-col gap-0.5">
        {ADMIN_SIDEBAR_LINKS.map((item) => (
          <MenuItemLink
            key={item.key}
            item={item}
            baseClass="flex items-center text-lg gap-3 cursor-pointer rounded-lg p-2 transition-all duration-200 text-gray-400"
            activeClass="bg-brightOrange text-white"
            noActiveClass="hover:bg-gray-500/50 dark:hover:bg-gray-600"
          />
        ))}
      </div>
      <div className="flex flex-col gap-0.5 border-t border-neutral-700 pt-2">
        {ADMIN_SIDEBAR_FOOTER_LINKS.map((item) => (
          <MenuItemLink
            key={item.key}
            item={item}
            baseClass="flex items-center text-lg gap-3 cursor-pointer rounded-lg p-2 transition-all duration-200  text-gray-400"
            activeClass="bg-brightOrange text-white"
            noActiveClass="hover:bg-gray-200 dark:hover:bg-gray-600"
          />
        ))}
        {/* Button logout */}
        <div className={classNames("text-red-500 cursor-pointer", linkClasses)}>
          <span className="text-xl">
            <HiOutlineLogout />
          </span>
          Logout
        </div>
      </div>
    </div>
  );
}
function SidebarLink({ item }) {
  const { pathname } = useLocation();
  return (
    <Link
      to={item.path}
      className={classNames(
        pathname === item.path
          ? "bg-neutral-700 text-white"
          : "text-neutral-400",
        linkClasses
      )}
    >
      <span className="text-xl">{item.icon}</span>
      {item.label}
    </Link>
  );
}

export default SideBar;
