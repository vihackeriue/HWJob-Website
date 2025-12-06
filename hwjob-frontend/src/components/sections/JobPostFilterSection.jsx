import React from "react";
import { useList } from "../../hooks/useList";
import { getRegionsNotPagination } from "../../services/regionService";
import { getJobTypesNotPagination } from "../../services/jobTypeService";
import { getIndustriesNotPagination } from "../../services/industryService";
import { getLevelsNotPagination } from "../../services/levelService";
import SecondTitle from "../ui/title/SecondTitle";
import FormSelect from "../ui/form/FormSelect";
import PrimaryButton from "../ui/button/PrimaryButton";

export const JobPostFilterSection = ({
  formFilter,
  setFormFilter,
  onFilter,
}) => {
  const regions = useList(getRegionsNotPagination);
  const jobTypes = useList(getJobTypesNotPagination);
  const industries = useList(getIndustriesNotPagination);
  const levels = useList(getLevelsNotPagination);

  const defaultFilter = {
    industryId: null,
    levelId: null,
    jobTypeId: null,
    regionId: null,
  };
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormFilter((prev) => ({ ...prev, [name]: value }));
  };
  const isFiltering = Object.keys(defaultFilter).some(
    (key) => formFilter[key] !== defaultFilter[key]
  );
  const resetFilter = () => setFormFilter(defaultFilter);
  return (
    <div className="col-span-1 bg-white dark:bg-stoneBrown-900/50 rounded-2xl p-3 flex flex-col gap-3">
      <SecondTitle>Lọc nâng cao</SecondTitle>

      <FormSelect
        label="Ngành nghề"
        name="industryId"
        selected={industries.data.find((j) => j.id === formFilter.industryId)}
        onChange={handleChange}
        options={industries.data}
        placeholder="Chọn ngành nghề"
      />

      <FormSelect
        label="Cấp bậc"
        name="levelId"
        selected={levels.data.find((j) => j.id === formFilter.levelId)}
        onChange={handleChange}
        options={levels.data}
        placeholder="Chọn cấp bậc"
      />

      <FormSelect
        label="Loại nghề"
        name="jobTypeId"
        selected={jobTypes.data.find((j) => j.id === formFilter.jobTypeId)}
        onChange={handleChange}
        options={jobTypes.data}
        placeholder="Chọn loại nghề"
      />

      <FormSelect
        label="Khu vực"
        name="regionId"
        selected={regions.data.find((r) => r.id === formFilter.regionId)}
        onChange={handleChange}
        options={regions.data}
        placeholder="Chọn khu vực"
      />

      {isFiltering && (
        <button
          className="bg-gray-300 dark:bg-stoneBrown-700 text-md px-3 py-2 rounded-xl"
          onClick={resetFilter}
        >
          Bỏ tất cả
        </button>
      )}

      <PrimaryButton onClick={onFilter}>Lọc</PrimaryButton>
    </div>
  );
};
