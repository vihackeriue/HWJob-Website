import React, { useState } from "react";
import JobPostCard from "../../components/ui/cards/JobPostCard";
import Pagination from "../../components/ui/pagination/Pagination";
import SearchBar from "../../components/ui/SearchBar";
import SecondTitle from "../../components/ui/title/SecondTitle";
import FormSelect from "../../components/ui/form/FormSelect";
import FormSwitch from "../../components/ui/form/FormSwitch";
import PrimaryButton from "../../components/ui/button/PrimaryButton";
import RangeInput from "../../components/ui/RangeInput";
import { useList } from "../../hooks/useList";
import { getRegionsNotPagination } from "../../services/regionService";
import { getJobTypesNotPagination } from "../../services/jobTypeService";
import { getIndustriesNotPagination } from "../../services/industryService";
import { getLevelsNotPagination } from "../../services/levelService";
import { HiOutlineSearch } from "react-icons/hi";
import { getJobPosts } from "../../services/jobPostService";
import { JobPostListSection } from "../../components/sections/JobPostListSection";

const JobPostList = () => {
  const jobPosts = useList(getJobPosts);

  const defaultFilter = {
    industryId: null,
    levelId: null,
    jobTypeId: null,
    regionId: null,
  };
  const [formFilter, setFormFilter] = useState(defaultFilter);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormFilter((prev) => ({ ...prev, [name]: value }));
  };

  const isFiltering = Object.keys(defaultFilter).some(
    (key) => formFilter[key] !== defaultFilter[key]
  );
  const regions = useList(getRegionsNotPagination);
  const jobTypes = useList(getJobTypesNotPagination);
  const industries = useList(getIndustriesNotPagination);
  const levels = useList(getLevelsNotPagination);
  return (
    <>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 mt-3">
        <div className="col-span-1 bg-white dark:bg-stoneBrown-900/50 rounded-2xl p-3 flex flex-col gap-3">
          <SecondTitle>Lọc nâng cao</SecondTitle>
          <FormSelect
            label="Ngành nghề"
            name="industryId"
            selected={industries.data.find(
              (j) => j.id === formFilter.industryId
            )}
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
          {/* <FormSelect
            label="Ngày đăng"
            name="gender"
            onChange={handleChange}
            options={location}
            placeholder="Chọn Ngày đăng"
          /> */}
          {isFiltering && (
            <button
              className="bg-gray-300 dark:bg-stoneBrown-700 text-md px-3 py-2 rounded-xl "
              onClick={() => {
                setFormFilter(defaultFilter);
              }}
            >
              Bỏ tất cả
            </button>
          )}
          <PrimaryButton
            onClick={() => {
              console.log(formFilter);
              jobPosts.setParams(formFilter);
              jobPosts.setPage(1); // reset page
            }}
          >
            Lọc
          </PrimaryButton>
        </div>
        <div className="flex flex-col gap-3 col-span-2 ">
          <div className="flex gap-3 bg-white dark:bg-stoneBrown-900/50 rounded-2xl p-3 ">
            <div className="relative ">
              <HiOutlineSearch
                size={18}
                className="absolute left-3 top-2.5 text-gray-400"
              />
              <input
                type="text"
                placeholder="Vị trí tuyển dụng, tên công ty,..."
                className="text-lg focus:outline-none h-10 w-[24rem] rounded-xl pl-9 pr-4 transition "
              />
            </div>
            <PrimaryButton>Tìm kiếm</PrimaryButton>
          </div>

          <JobPostListSection
            jobPosts={jobPosts.data}
            pagination={{
              page: jobPosts.page,
              totalPages: jobPosts.totalPages,
              setPage: jobPosts.setPage,
            }}
          />
        </div>
      </div>
    </>
  );
};

export default JobPostList;
