import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Listbox,
  ListboxButton,
  ListboxOption,
  ListboxOptions,
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
  Popover,
  PopoverButton,
  PopoverPanel,
} from "@headlessui/react";
import {
  HiOutlineAcademicCap,
  HiOutlineArrowDown,
  HiOutlineBell,
  HiOutlineChatAlt,
  HiOutlineCheck,
  HiOutlineSearch,
} from "react-icons/hi";
import useAuth from "../../../hooks/useAuth";
import DarkMode from "../../ui/DarkMode";
function Header() {
  const navigate = useNavigate();
  const auth = useAuth();

  const searchFilter = [
    { id: 1, name: "Category" },
    { id: 2, name: "User" },
    { id: 3, name: "Poll" },
  ];
  const [selected, setSelected] = useState(searchFilter[1]);
  return (
    <div className="bg-white h-16 px-4 flex justify-between items-center border-b border-gray-200">
      <div className="flex items-center gap-2">
        <div className="relative">
          <HiOutlineSearch
            size={20}
            className="absolute text-gray-400 top-1/2 -translate-y-1/2 left-3"
          />
          <input
            type="text"
            placeholder="Search..."
            name=""
            id=""
            className="text-sm focus:outline-none active:outline-none h-10 w-[24rem] border border-gray-300 rounded-sm pr-4 pl-11"
          />
        </div>
        <div>
          <Listbox
            value={selected}
            onChange={setSelected}
            as="div"
            className="relative"
          >
            <ListboxButton className="border rounded-sm border-gray-200 pl-2 pr-4 py-2 text-sm/6 inline-flex items-center gap-2 text-gray-700 hover:bg-gray-100 focus:outline-none active:bg-gray-100 data-open:bg-gray-200">
              Filter: {selected.name}
            </ListboxButton>
            <ListboxOptions
              transition
              className="absolute mt-3.5 w-40 border rounded-sm border-gray-100 p-2 text-sm shadow-sm bg-white text-gray-700"
            >
              {searchFilter.map((item) => (
                <ListboxOption
                  key={item.name}
                  value={item}
                  className="group flex cursor-default items-center gap-2 rounded-lg px-3 py-1.5 select-none data-focus:bg-gray-100 data-selected:bg-gray-100"
                >
                  <HiOutlineCheck className="invisible size-4 fill-white group-data-selected:visible" />
                  <div className="text-sm/6 text-gray-700">{item.name}</div>
                </ListboxOption>
              ))}
            </ListboxOptions>
          </Listbox>
        </div>
      </div>

      <div className="flex items-center gap-2 text-gray-600 mr-2">
        <Popover className="relative">
          <PopoverButton className="p-1.5 rounded-full inline-flex items-center text-sm/6 text-gray-700 hover:text-opacity-100 focus:outline-none active:bg-gray-100 data-open:bg-gray-200 ">
            <HiOutlineBell fontSize={24} />
          </PopoverButton>
          <PopoverPanel
            transition
            className="absolute z-10 mt-5.5 w-80 right-0 p-2 divide-white/5 rounded-xl bg-white text-sm/6 shadow-md transition duration-200 ease-in-out [--anchor-gap:--spacing(5)] data-closed:-translate-y-1 data-closed:opacity-0"
          >
            <div className=" rounded-lg px-3 py-2 transition hover:bg-gray-100 ">
              <p className="font-semibold text-gray-700">Notifications</p>
              <p className="text-gray-400">Measure actions your users take</p>
            </div>
            <div className=" rounded-lg px-3 py-2 transition hover:bg-gray-100 ">
              <p className="font-semibold text-gray-700">Notifications</p>
              <p className="text-gray-400">Measure actions your users take</p>
            </div>
          </PopoverPanel>
        </Popover>
        <Menu as="div" className="relative">
          <MenuButton className="inline-flex items-center gap-2 rounded-md  px-3 py-1.5 text-sm/6 font-semibold text-gray-700 shadow-inner shadow-white/10 focus:not-data-focus:outline-none data-focus:outline data-focus:outline-white data-hover:bg-gray-100 data-open:bg-gray-200">
            <div className="flex items-center gap-2">
              <img
                src="https://th.bing.com/th?q=IPhone+Avatar&w=120&h=120&c=1&rs=1&qlt=90&r=0&cb=1&dpr=1.3&pid=InlineBlock&mkt=en-WW&cc=VN&setlang=en&adlt=moderate&t=1&mw=247"
                alt=""
                className="h-10 w-10 rounded-full object-cover border border-red-300"
              />
              <span className="">Wain RP</span>
            </div>
          </MenuButton>
          <MenuItems
            transition
            className="absolute w-50 mt-3.5 z-10 right-0 origin-top-right rounded-xl border border-gray-200 shadow-md bg-white p-2 text-sm/6 text-gray-700 transition duration-100 ease-out [--anchor-gap:--spacing(1)] focus:outline-none data-closed:scale-95 data-closed:opacity-0"
          >
            <MenuItem>
              <button
                className="group flex w-full items-center gap-2 rounded-sm px-4 py-2 data-focus:bg-gray-200"
                onClick={() => navigate("/profile")}
              >
                Profile
              </button>
            </MenuItem>
            <MenuItem>
              <button
                className="group flex w-full items-center gap-2 rounded-sm px-4 py-2 data-focus:bg-gray-200"
                onClick={() => navigate("/settings")}
              >
                settings
              </button>
            </MenuItem>

            <div className="my-1 h-px bg-gray-200" />
            <MenuItem>
              <button
                className="group flex w-full items-center gap-2 rounded-sm px-4 py-2 data-focus:bg-gray-200 "
                onClick={() => auth.logout()}
              >
                Sign out
              </button>
            </MenuItem>
          </MenuItems>
        </Menu>
      </div>
    </div>
  );
}

export default Header;
