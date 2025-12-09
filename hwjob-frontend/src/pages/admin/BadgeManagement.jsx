import React, { useState } from "react";
import FormTable from "../../components/ui/form/FormTable";
import PrimaryTitle from "../../components/ui/title/PrimaryTitle";
import { HiOutlineSearch } from "react-icons/hi";
import PrimaryButton from "../../components/ui/button/PrimaryButton";
import AddBadgeDialog from "../../components/dialog/AddBadgeDialog";
const columns = [
  { key: "id", label: "ID" },
  { key: "name", label: "Tên huy hiệu" },
  { key: "desc", label: "Mô tả" },
  { key: "type", label: "Loại" },
  { key: "condition", label: "điều kiện" },
  { key: "img", label: "Ảnh" },
];
const fakeData = [
  { id: 1, name: "Công nghệ thông tin", status: "ACTIVE" },
  { id: 2, name: "Kế toán", status: "INACTIVE" },
  { id: 3, name: "Kế toán", status: "INACTIVE" },
  { id: 4, name: "Kế toán", status: "INACTIVE" },
  { id: 5, name: "Kế toán", status: "INACTIVE" },
];
const BadgeManagement = () => {
  const [page, setPage] = useState(1);
  const perPage = 3;
  const totalPages = Math.ceil(fakeData.length / perPage);
  const [openDialog, setOpenDialog] = useState(false);
  const handleAddBadge = (newBadge) => {
    console.log("Ngành nghề mới:", newBadge);
    // Gọi API thêm dữ liệu, ví dụ:
    // await api.post("/badges", newBadge);
  };
  return (
    <div className="flex flex-col gap-3">
      <div className="flex-2">
        <PrimaryTitle>Huy hiệu</PrimaryTitle>
        <div className="flex justify-between items-center bg-white p-3 rounded-2xl">
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
          <PrimaryButton onClick={() => setOpenDialog(true)}>
            Tạo mới
          </PrimaryButton>
        </div>
      </div>
      <FormTable
        columns={columns}
        data={fakeData}
        pagination={{ page, totalPages, setPage }}
      />
      <AddBadgeDialog
        open={openDialog}
        onClose={() => setOpenDialog(false)}
        onSubmit={handleAddBadge}
      />
    </div>
  );
};

export default BadgeManagement;
