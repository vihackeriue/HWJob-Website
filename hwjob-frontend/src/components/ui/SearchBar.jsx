import React, { useState } from "react";
import { HiOutlineSearch } from "react-icons/hi";
import { IoClose } from "react-icons/io5";
import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { HiChevronDown } from "react-icons/hi2";

const LOCATIONS = [
  "Hồ Chí Minh",
  "Hà Nội",
  "Đà Nẵng",
  "Cần Thơ",
  "Hải Phòng",
  "Bình Dương",
  "Đồng Nai",
  "Huế",
  "Nha Trang",
  "Quảng Ninh",
  "Vũng Tàu",
  "Long An",
];

const SearchBar = () => {
  const [selected, setSelected] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  const toggleLocation = (loc) => {
    setSelected((prev) =>
      prev.includes(loc) ? prev.filter((l) => l !== loc) : [...prev, loc]
    );
  };

  const filteredLocations = LOCATIONS.filter((loc) =>
    loc.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="flex items-center gap-3 bg-white px-4 py-6 rounded-xl  ">
      {/* Ô nhập vị trí tuyển dụng */}
      <div className="relative">
        <HiOutlineSearch
          size={18}
          className="absolute left-3 top-2.5 text-gray-400"
        />
        <input
          type="text"
          placeholder="Vị trí tuyển dụng, tên công ty,..."
          className="text-lg focus:outline-none h-10 w-[24rem] rounded-xl pl-9 pr-4 transition"
        />
      </div>

      {/* Dropdown chọn địa điểm */}
      <Menu as="div" className="relative w-64">
        <MenuButton className="w-full flex items-center justify-between border rounded-xl px-3 py-2 text-lg text-gray-700 hover:border-brightOrange transition">
          <div className="flex items-center gap-2 max-w-[85%] overflow-hidden truncate">
            {selected.length === 0 ? (
              <span className="text-gray-400">Chọn địa điểm...</span>
            ) : (
              selected.map((loc) => (
                <span
                  key={loc}
                  className="flex items-center bg-brightOrange text-white px-2 py-1 rounded-lg text-md shrink-0"
                >
                  {loc}
                  <IoClose
                    className="ml-1 cursor-pointer"
                    onClick={(e) => {
                      e.stopPropagation();
                      toggleLocation(loc);
                    }}
                  />
                </span>
              ))
            )}
          </div>
          <HiChevronDown className="text-gray-500 ml-2 flex-shrink-0" />
        </MenuButton>

        <MenuItems
          anchor="bottom"
          className="absolute z-10 mt-2 w-64 h-64 bg-white border border-gray-200 rounded-xl shadow-lg overflow-y-auto focus:outline-none scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100"
        >
          {/* Ô tìm kiếm */}
          <div className="sticky top-0 bg-white border-b flex items-center gap-2 px-3 py-2">
            <HiOutlineSearch className="text-gray-500" />
            <input
              type="text"
              placeholder="Tìm địa điểm..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              onKeyDown={(e) => {
                // Ngăn HeadlessUI đóng menu khi nhấn space hoặc Enter
                e.stopPropagation();
              }}
              className="w-full text-md focus:outline-none placeholder-gray-400"
            />
          </div>

          {/* Danh sách địa điểm */}
          <div className="py-1">
            {filteredLocations.length > 0 ? (
              filteredLocations.map((loc) => (
                <MenuItem key={loc}>
                  {({ focus }) => (
                    <div
                      onClick={(e) => {
                        e.stopPropagation();
                        toggleLocation(loc);
                      }}
                      className={`flex items-center gap-2 px-3 py-2 cursor-pointer select-none ${
                        focus ? "bg-gray-100" : ""
                      }`}
                    >
                      <input
                        type="checkbox"
                        checked={selected.includes(loc)}
                        readOnly
                      />
                      <span>{loc}</span>
                    </div>
                  )}
                </MenuItem>
              ))
            ) : (
              <div className="px-3 py-2 text-sm text-gray-500">
                Không tìm thấy kết quả
              </div>
            )}
          </div>
        </MenuItems>
      </Menu>

      {/* Nút tìm kiếm */}
      <button className="flex items-center flex-shrink-0 gap-2 bg-brightOrange text-white px-4 py-2 rounded-xl hover:bg-orange-600 transition-colors text-lg">
        <HiOutlineSearch size={18} />
        <span>Tìm kiếm</span>
      </button>
    </div>
  );
};

export default SearchBar;
