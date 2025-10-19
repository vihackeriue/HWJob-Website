import React from "react";
import { FaFileAlt, FaHome, FaLock, FaPen, FaUser } from "react-icons/fa";
import SecondTitle from "../../ui/title/SecondTitle";
import MenuItemLink from "../../ui/MenuItemLink";
import { PROFILE_USER_MENUS } from "../../../constants/navigation";
import { Link, useLocation } from "react-router-dom";
import classNames from "classnames";

const ProfileSidebar = () => {
  return (
    <div className="bg-white dark:bg-stoneBrown-900 p-3 rounded-2xl shadow-sm">
      <SecondTitle>Chỉnh sửa</SecondTitle>
      <div className="space-y-1 mt-5 hidden md:block ">
        {PROFILE_USER_MENUS.map((item) => (
          <MenuItemLink
            key={item.key}
            item={item}
            baseClass="flex items-center text-lg gap-3 cursor-pointer rounded-lg p-2 transition-all duration-200"
            activeClass="bg-brightOrange text-white"
            noActiveClass="hover:bg-gray-200 dark:hover:bg-gray-600"
          />
        ))}
      </div>
      <div className="flex justify-between md:hidden">
        {PROFILE_USER_MENUS.map((item) => (
          <MenuItemLink
            key={item.key}
            item={item}
            isTitle={false}
            baseClass="flex items-center w-full justify-center text-lg gap-3 cursor-pointer rounded-lg p-2 transition-all duration-200"
            activeClass="bg-brightOrange text-white"
            noActiveClass="hover:bg-gray-200 dark:hover:bg-gray-600"
          />
        ))}
      </div>
    </div>
  );
};

export default ProfileSidebar;
const linkClasses =
  "text-lg font-medium  hover:text-primary py-2 hover-underline transition-colors duration-500 ";
function NavbarLink({ item }) {
  const { pathname } = useLocation();

  return (
    <Link
      to={item.path}
      className={classNames(
        pathname === item.path ? "text-brightOrange font-semibold" : "",
        linkClasses
      )}
    >
      {item.icon}
    </Link>
  );
}
