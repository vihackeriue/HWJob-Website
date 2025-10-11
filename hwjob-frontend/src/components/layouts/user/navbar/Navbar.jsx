import React, { useState } from "react";
import { GiVote } from "react-icons/gi";
import {
  DROPDOWN_USER_LINKS,
  NAVBAR_USER_LINKS,
} from "../../../../constants/user/navigation";
import { Link, useLocation } from "react-router-dom";
import classNames from "classnames";
import DarkMode from "../../../ui/DarkMode";
import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { LANGUAGES } from "../../../../constants/language";
import LanguageSwitcher from "../../../ui/LanguageSwitcher";
import { useTranslation } from "react-i18next";
import useAuth from "../../../../hooks/useAuth";
import ResponsiveMenu from "./ResponsiveMenu";
import { HiMenuAlt1, HiMenuAlt3 } from "react-icons/hi";

export default function Navbar() {
  const { auth, logout } = useAuth();
  const [showMenu, setShowMenu] = useState(false);
  const toggleMenu = () => {
    setShowMenu(!showMenu);
  };

  return (
    <div className="relative z-10 w-full bg-teal-900 text-gray-100">
      <div className="container py-3 md:py-2">
        <div className="flex justify-between items-center">
          <div className="flex items-center gap-3">
            <GiVote className="text-primary" size={30} />
            <span className="text-2xl sm:text-2xl font-semibold font-vollkorn">
              HWJOB
            </span>
          </div>
          <div className="hidden md:block">
            <div className="flex items-center gap-8">
              {NAVBAR_USER_LINKS.map((item) => (
                <NavbarLink key={item.key} item={item}></NavbarLink>
              ))}
              <DarkMode />
            </div>
          </div>
          <div className="hidden md:block">
            <div className="flex gap-3 items-center">
              {auth ? (
                <>
                  <Menu as="div" className="relative">
                    <MenuButton className="inline-flex items-center gap-2 rounded-md  px-3 py-1.5 text-sm/6 font-semibold shadow-white/10 focus:not-data-focus:outline-none data-focus:outline data-focus:outline-white data-hover:bg-dark-900 data-open:bg-dark-900">
                      <div className="flex items-center gap-2">
                        <img
                          src="https://th.bing.com/th?q=IPhone+Avatar&w=120&h=120&c=1&rs=1&qlt=90&r=0&cb=1&dpr=1.3&pid=InlineBlock&mkt=en-WW&cc=VN&setlang=en&adlt=moderate&t=1&mw=247"
                          alt=""
                          className="h-12 w-12 rounded-full object-cover border border-red-300"
                        />
                        <span className="text-lg uppercase">
                          {auth.username}
                        </span>
                      </div>
                    </MenuButton>
                    <MenuItems
                      transition
                      className="absolute w-50 mt-3.5 z-10 right-0 origin-top-right rounded-xl border border-gray-200 shadow-md bg-white p-2 text-sm/6 text-gray-700 transition duration-100 ease-out [--anchor-gap:--spacing(1)] focus:outline-none data-closed:scale-95 data-closed:opacity-0"
                    >
                      {DROPDOWN_USER_LINKS.map((item) => (
                        <MenuItemLink item={item} />
                      ))}
                      <div className="my-1 h-px bg-gray-200" />
                      <MenuItem>
                        <button
                          className={menuItemClasses}
                          onClick={() => logout()}
                        >
                          Sign out
                        </button>
                      </MenuItem>
                    </MenuItems>
                  </Menu>
                </>
              ) : (
                <Link to="/login">Login</Link>
              )}
            </div>
          </div>
          <div className="flex items-center  md:hidden">
            {showMenu ? (
              <HiMenuAlt1
                onClick={toggleMenu}
                className=" cursor-pointer transition-all"
                size={30}
              />
            ) : (
              <HiMenuAlt3
                onClick={toggleMenu}
                className="cursor-pointer transition-all"
                size={30}
              />
            )}
          </div>
          <LanguageSwitcher />
        </div>
      </div>
      <ResponsiveMenu showMenu={showMenu} auth={auth} logout={logout} />
    </div>
  );
}
const linkClasses =
  "text-lg font-medium  hover:text-primary py-2 hover:border-primary transition-colors duration-500 ";
function NavbarLink({ item }) {
  const { pathname } = useLocation();
  const { t } = useTranslation();
  return (
    <Link
      to={item.path}
      className={classNames(
        pathname === item.path ? "text-primary font-semibold" : "",
        linkClasses
      )}
    >
      {t(item.label)}
    </Link>
  );
}
const menuItemClasses =
  "group flex w-full items-center gap-2 rounded-sm px-4 py-2 data-focus:bg-gray-200";
function MenuItemLink({ item }) {
  const { pathname } = useLocation();
  const { t } = useTranslation();
  return (
    <MenuItem>
      <Link
        to={item.path}
        className={classNames(
          pathname === item.path ? "text-primary font-semibold" : "",
          menuItemClasses
        )}
      >
        {t(item.label)}
      </Link>
    </MenuItem>
  );
}
