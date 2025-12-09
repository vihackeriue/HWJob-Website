import React from "react";
import FormTable from "../../components/ui/form/FormTable";
import PrimaryTitle from "../../components/ui/title/PrimaryTitle";
import { HiOutlineSearch } from "react-icons/hi";
import PrimaryButton from "../../components/ui/button/PrimaryButton";
import { getUsers } from "../../services/userService";
import { useList } from "../../hooks/useList";

const columns = [
  { key: "id", label: "ID" },
  { key: "username", label: "Tên đăng nhập" },
  { key: "email", label: "Email" },
  { key: "phone", label: "Số điện thoại" },
  { key: "role", label: "Vai trò" },
];

const UserManagement = () => {
  const users = useList(getUsers);
  return (
    <div className="flex flex-col gap-3">
      <div>
        <PrimaryTitle>Người dùng hệ thống</PrimaryTitle>
        <div className="flex gap-3  items-center bg-white p-3 rounded-2xl">
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
          <PrimaryButton>Tìm kiếm</PrimaryButton>
        </div>
      </div>
      <FormTable
        columns={columns}
        data={users.data}
        isAct={false}
        isLock={true}
        pagination={{
          page: users.page,
          totalPages: users.totalPages,
          setPage: users.setPage,
        }}
      />
    </div>
  );
};

export default UserManagement;
