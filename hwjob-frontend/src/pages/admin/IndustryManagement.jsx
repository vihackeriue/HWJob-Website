import React from "react";
import { HiOutlineSearch } from "react-icons/hi";

const IndustryManagement = () => {
  return (
    <div>
      <div className="flex ">
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
        <div>Tạo mới</div>
      </div>
    </div>
  );
};

export default IndustryManagement;
