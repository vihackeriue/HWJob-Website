import React from "react";
import { HiOutlineSearch } from "react-icons/hi";
import PrimaryButton from "../../components/ui/button/PrimaryButton";
import FormTable from "../../components/ui/form/FormTable";
import PrimaryTitle from "../../components/ui/title/PrimaryTitle";
import AddIndustryDialog from "../../components/dialog/AddIndustryDialog";

import Loading from "../../components/ui/Loading";
import { useList } from "../../hooks/useList";
import { getJobTypes } from "../../services/jobTypeService";
import { getRegions } from "../../services/regionService";
import { getLevels } from "../../services/levelService";

const industryColumns = [
  { key: "id", label: "ID" },
  { key: "name", label: "Tên ngành nghề" },
  { key: "desc", label: "Mô tả" },
];
const regionColumns = [
  { key: "code", label: "Code" },
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
  const jobTypes = useList(getJobTypes);
  const regions = useList(getRegions);
  const levels = useList(getLevels);
  if (jobTypes.loading || regions.loading) return <Loading />;

  return (
    <div className="p-5">
      <div className="flex gap-3 ">
        <div className="flex-2 flex flex-col gap-3">
          <PrimaryTitle>Ngành nghề</PrimaryTitle>
          <FormTable
            columns={industryColumns}
            data={fakeData}
            isAct={false}
            pagination={{
              page: jobTypes.page,
              totalPages: jobTypes.totalPages,
              setPage: jobTypes.setPage,
            }}
          />
        </div>
        <div className="flex-1">
          <div className="flex flex-col gap-3">
            <PrimaryTitle>Khu vực</PrimaryTitle>
            <FormTable
              columns={regionColumns}
              data={regions.data}
              isAct={false}
              pagination={{
                page: regions.page,
                totalPages: regions.totalPages,
                setPage: regions.setPage,
              }}
            />
          </div>
        </div>
      </div>
      <div className="flex gap-3">
        <div className="flex-1 flex flex-col gap-3">
          <PrimaryTitle>Loại nghề nghiệp</PrimaryTitle>
          <FormTable
            columns={jobTypeColumns}
            data={jobTypes.data}
            isAct={false}
            pagination={{
              page: jobTypes.page,
              totalPages: jobTypes.totalPages,
              setPage: jobTypes.setPage,
            }}
          />
        </div>
        <div className="flex-1 flex flex-col gap-3">
          <PrimaryTitle>Cấp bậc</PrimaryTitle>
          <FormTable
            columns={levelColumns}
            data={levels.data}
            isAct={false}
            pagination={{
              page: levels.page,
              totalPages: levels.totalPages,
              setPage: levels.setPage,
            }}
          />
        </div>
      </div>
    </div>
  );
};

export default CategoryManagement;
