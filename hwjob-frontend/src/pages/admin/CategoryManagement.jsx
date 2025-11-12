import React, { useState } from "react";
import { HiOutlineSearch } from "react-icons/hi";
import PrimaryButton from "../../components/ui/button/PrimaryButton";
import FormTable from "../../components/ui/form/FormTable";
import PrimaryTitle from "../../components/ui/title/PrimaryTitle";
import AddIndustryDialog from "../../components/dialog/AddIndustryDialog";

const industryColumns = [
  { key: "id", label: "ID" },
  { key: "name", label: "Tên ngành nghề" },
  { key: "desc", label: "Mô tả" },
];
const regionColumns = [
  { key: "id", label: "ID" },
  { key: "name", label: "Tên khu vực" },
];
const jobTypeColumns = [
  { key: "id", label: "ID" },
  { key: "name", label: "Tên loại nghề" },
];
const levelColumns = [
  { key: "id", label: "ID" },
  { key: "name", label: "Tên cấp bậc" },
];
const fakeData = [
  { id: 1, name: "Công nghệ thông tin", status: "ACTIVE" },
  { id: 2, name: "Kế toán", status: "INACTIVE" },
  { id: 3, name: "Kế toán", status: "INACTIVE" },
  { id: 4, name: "Kế toán", status: "INACTIVE" },
  { id: 5, name: "Kế toán", status: "INACTIVE" },
];

const CategoryManagement = () => {
  const [industries, setIndustries] = useState([
    { id: 1, name: "Công nghệ thông tin" },
    { id: 2, name: "Kế toán" },
  ]);
  const [page, setPage] = useState(1);
  const perPage = 3;
  const totalPages = Math.ceil(fakeData.length / perPage);
  const [openDialog, setOpenDialog] = useState(false);

  const handleAddIndustry = (newIndustry) => {
    setIndustries((prev) => [...prev, { id: Date.now(), ...newIndustry }]);
  };
  return (
    <div className="flex gap-3 p-5">
      <div className="flex-2 flex flex-col gap-3">
        <PrimaryTitle>Ngành nghề</PrimaryTitle>

        <FormTable
          columns={industryColumns}
          data={fakeData}
          isAct={false}
          pagination={{ page, totalPages, setPage }}
        />
      </div>
      <div className="flex-1">
        <div className="flex flex-col gap-3">
          <PrimaryTitle>Khu vực</PrimaryTitle>
          <FormTable
            columns={regionColumns}
            data={fakeData}
            isAct={false}
            pagination={{ page, totalPages, setPage }}
          />
        </div>
        <div className="flex  flex-col gap-3">
          <PrimaryTitle>Loại nghề nghiệp</PrimaryTitle>
          <FormTable
            columns={jobTypeColumns}
            data={fakeData}
            isAct={false}
            pagination={{ page, totalPages, setPage }}
          />
        </div>
        <div className="flex flex-col gap-3">
          <PrimaryTitle>Cấp bậc</PrimaryTitle>
          <FormTable
            columns={levelColumns}
            data={fakeData}
            isAct={false}
            pagination={{ page, totalPages, setPage }}
          />
        </div>
      </div>
      <AddIndustryDialog
        open={openDialog}
        onClose={() => setOpenDialog(false)}
        onSubmit={handleAddIndustry}
      />
    </div>
  );
};

export default CategoryManagement;
