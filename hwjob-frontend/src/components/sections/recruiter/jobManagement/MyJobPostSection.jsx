import React, { useState } from "react";
import { JobPostListSection } from "../../JobPostListSection";
import { useList } from "../../../../hooks/useList";
import { getJobPostsOfRecruiter } from "../../../../services/jobPostService";
import FormSelect from "../../../ui/form/FormSelect";
import PrimaryButton from "../../../ui/button/PrimaryButton";

import { IoMdAdd, IoMdSearch } from "react-icons/io";
import FormInput from "../../../ui/form/FormInput";

const RECRUITER_JOB_POST_FILTER = [
  { code: "ALL", name: "Tất cả" },
  { code: "PUBLIC", name: "Đang mở tuyển" },
  { code: "PRIVATE", name: "Đã ẩn" },
];
const MyJobPostSection = () => {
  const myJobPosts = useList(getJobPostsOfRecruiter);

  const [formFilter, setFormFilter] = useState({
    status: null,
    keyword: "",
  });

  const handleFilter = (e) => {
    const { name, value } = e.target;
    setFormFilter((prev) => ({ ...prev, [name]: value }));

    // Cập nhật params để trigger API
    myJobPosts.setParams((prev) => ({
      ...prev,
      [name]: value === "ALL" ? null : value,
    }));
    myJobPosts.setPage(1);
  };
  // Hàm xử lý khi nhấn nút Tìm kiếm
  const handleSearch = () => {
    myJobPosts.setParams((prev) => ({
      ...prev,
      keyword: formFilter.keyword.trim() || null, // Lấy giá trị từ state local
      status: formFilter.status === "ALL" ? null : formFilter.status,
    }));
    myJobPosts.setPage(1); // Reset về trang 1
  };
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormFilter((prev) => ({ ...prev, [name]: value }));
  };

  // Thêm tính năng nhấn phím Enter để tìm kiếm cho tiện
  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSearch();
    }
  };
  return (
    <>
      {/* Filter bar */}
      <div className="mb-3 p-3 bg-white rounded-xl">
        <div className="grid grid-cols-12 gap-3 ">
          <div className="flex gap-3 items-center col-span-8">
            {/* Ô nhập tiêu đề */}
            <div className="flex-3">
              <FormInput
                placeholder="Tìm kiếm bài đăng..."
                name="keyword"
                type="text"
                value={formFilter.keyword}
                onChange={handleChange}
                onKeyDown={handleKeyDown}
              />
            </div>
            {/* Nút Tìm kiếm */}
            <div className="flex-1">
              <PrimaryButton onClick={handleSearch}>
                <IoMdSearch size={20} />
                Tìm kiếm
              </PrimaryButton>
            </div>
          </div>

          {/* Nút Tạo bài đăng */}
          <div className="col-span-4">
            {/* Select trạng thái */}
            <div className="col-span-2">
              <FormSelect
                name="status"
                selected={RECRUITER_JOB_POST_FILTER.find(
                  (j) => j.code === formFilter.status
                )}
                onChange={handleFilter}
                options={RECRUITER_JOB_POST_FILTER}
              />
            </div>
          </div>
        </div>
      </div>

      <JobPostListSection
        jobPosts={myJobPosts.data}
        pagination={{
          page: myJobPosts.page,
          totalPages: myJobPosts.totalPages,
          setPage: myJobPosts.setPage,
        }}
      />
    </>
  );
};

export default MyJobPostSection;
