import classNames from "classnames";
import React, { useState } from "react";
import SecondTitle from "./title/SecondTitle";
import { CiCircleChevDown, CiCircleChevUp } from "react-icons/ci";
import { Tab, TabList } from "@headlessui/react";

const baseClass =
  "flex items-center text-lg gap-3 cursor-pointer rounded-lg p-2 transition-all duration-200";
const activeClass = "bg-brightOrange text-white";
const noActiveClass = "hover:bg-gray-200 dark:hover:bg-gray-600";
export const MenuTablist = ({ menus, title, orientation = "vertical" }) => {
  const [isOpen, setIsOpen] = useState(false);
  const isHorizontal = orientation === "horizontal";
  return (
    <div
      className={classNames(
        "bg-white dark:bg-stoneBrown-900 rounded-2xl shadow-sm",
        !isHorizontal && "p-3"
      )}
    >
      {/* Mobile header */}
      <div className="md:hidden flex justify-between items-center">
        <SecondTitle>{title}</SecondTitle>
        <button onClick={() => setIsOpen(!isOpen)}>
          {isOpen ? (
            <CiCircleChevUp className="size-6" />
          ) : (
            <CiCircleChevDown className="size-6" />
          )}
        </button>
      </div>

      {/* Desktop title */}
      <div className="hidden md:block">
        <SecondTitle>{title}</SecondTitle>
      </div>

      {/* Menu items */}
      <div
        className={classNames({
          "md:block": true,
          block: isOpen,
          hidden: !isOpen,
        })}
      >
        <TabList
          className={classNames("", {
            " mt-5 space-y-1": !isHorizontal,
            "flex gap-2 flex-row ": isHorizontal,
          })}
        >
          {menus.map((item) => (
            <Tab key={item.key} className="w-full focus:outline-none">
              {({ selected }) => (
                <div
                  className={classNames(
                    baseClass,
                    selected ? activeClass : noActiveClass
                  )}
                >
                  {item.label}
                </div>
              )}
            </Tab>
          ))}
        </TabList>
      </div>
    </div>
  );
};
