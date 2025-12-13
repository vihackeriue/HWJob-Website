import classNames from "classnames";
import React from "react";
import SecondTitle from "./title/SecondTitle";
import { Tab, TabList } from "@headlessui/react";

const baseClass =
  "flex items-center justify-center text-base gap-2 cursor-pointer rounded-lg px-4 py-2 transition-all duration-300 whitespace-nowrap";
const activeClass = "bg-brightOrange text-white";
const noActiveClass = "hover:bg-gray-200 dark:hover:bg-gray-600";

const MenuTabListHorizontal = ({ menus }) => {
  return (
    <div className="bg-white dark:bg-stoneBrown-900 rounded-xl p-3 shadow-sm">
      <TabList
        className="
          flex flex-row gap-2
          overflow-x-auto scrollbar-hide
        "
      >
        {menus.map((item) => (
          <Tab key={item.key} className="focus:outline-none">
            {({ selected }) => (
              <div
                className={classNames(
                  baseClass,
                  selected ? activeClass : noActiveClass
                )}
              >
                {item.icon && <span>{item.icon}</span>}
                {item.label}
              </div>
            )}
          </Tab>
        ))}
      </TabList>
    </div>
  );
};

export default MenuTabListHorizontal;
