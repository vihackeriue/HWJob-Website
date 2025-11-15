import React, { useState } from "react";
import FormInput from "../../../components/ui/form/FormInput";
import RichTextEditor from "../../../components/ui/RichTextEditor";
import FormSelect from "../../../components/ui/form/FormSelect";
import RangeInput from "../../../components/ui/RangeInput";
import PrimaryButton from "../../../components/ui/button/PrimaryButton";
import SecondTitle from "../../../components/ui/title/SecondTitle";
import PrimaryTitle from "../../../components/ui/title/PrimaryTitle";
import { getRegionsNotPagination } from "../../../services/regionService";
import { getJobTypesNotPagination } from "../../../services/jobTypeService";
import { useList } from "../../../hooks/useList";
import { getIndustriesNotPagination } from "../../../services/industryService";
import { getLevelsNotPagination } from "../../../services/levelService";

const AddJobPost = () => {
  const [formJobPost, setFormJobPost] = useState({
    title: "Nguyễn Văn A",
    description: "",
    quantity: 5,

    salaryMin: 5,
    salaryMax: 5,
    endedTime: "",
    industryId: null,
    levelId: null,
    jobTypeId: null,
    regionId: null,
  });
  const [errors, setErrors] = useState({});

  const regions = useList(getRegionsNotPagination);
  const jobTypes = useList(getJobTypesNotPagination);
  const industries = useList(getIndustriesNotPagination);
  const levels = useList(getLevelsNotPagination);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormJobPost((prev) => ({ ...prev, [name]: value }));
  };

  const validateForm = () => {
    const newErrors = {};

    // 1. Các field không được rỗng
    if (!formJobPost.title.trim())
      newErrors.title = "Tiêu đề không được để trống";
    if (!formJobPost.description.trim())
      newErrors.description = "Mô tả không được để trống";
    if (!formJobPost.industryId)
      newErrors.industryId = "Vui lòng chọn ngành nghề";
    if (!formJobPost.levelId) newErrors.levelId = "Vui lòng chọn cấp bậc";
    if (!formJobPost.jobTypeId) newErrors.jobTypeId = "Vui lòng chọn loại nghề";
    if (!formJobPost.regionId) newErrors.regionId = "Vui lòng chọn khu vực";

    // 2. Số lượng >= 1
    if (formJobPost.quantity < 1)
      newErrors.quantity = "Số lượng phải lớn hơn hoặc bằng 1";

    // 3. Mức lương > 0 và salaryMin < salaryMax
    if (formJobPost.salaryMin <= 0)
      newErrors.salaryMin = "Mức lương từ phải lớn hơn 0";
    if (formJobPost.salaryMax <= 0)
      newErrors.salaryMax = "Mức lương đến phải lớn hơn 0";
    if (formJobPost.salaryMin > formJobPost.salaryMax)
      newErrors.salaryMin = "Mức lương từ phải nhỏ hơn hoặc bằng mức lương đến";

    // 4. Hạn nộp hồ sơ (datetime) phải > hiện tại
    if (!formJobPost.endedTime) {
      newErrors.endedTime = "Vui lòng chọn hạn nộp hồ sơ";
    } else {
      const endedTimeDate = new Date(formJobPost.endedTime);
      const now = new Date();
      if (endedTimeDate <= now) {
        newErrors.endedTime = "Hạn nộp hồ sơ phải lớn hơn thời gian hiện tại";
      }
    }

    setErrors(newErrors);

    // Nếu không có lỗi → trả về true
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = () => {
    if (!validateForm()) {
      console.log("Form có lỗi:", errors);
      return;
    }

    console.log("Form Job Post:", formJobPost);

    // Call API tạo job post
  };

  return (
    <div>
      <div className="p-1 bg-white rounded-2xl my-3">
        <PrimaryTitle>Đăng tin tuyển dụng</PrimaryTitle>
      </div>
      <div className="flex gap-3">
        <div className="flex-1 flex flex-col gap-2 bg-white p-3 rounded-2xl">
          <SecondTitle>Thông tin bài đăng</SecondTitle>
          <FormInput
            label="Tiêu đề"
            name="title"
            value={formJobPost.title}
            error={errors.title}
            onChange={handleChange}
          />
          <RichTextEditor
            label={"Mô tả công việc"}
            value={formJobPost.description}
            error={errors.description}
            onChange={(value) =>
              setFormJobPost((prev) => ({ ...prev, description: value }))
            }
            placeholder={"Mô tả công việc"}
          />
          <FormInput
            label="Số lượng"
            name="quantity"
            type="number"
            value={formJobPost.quantity}
            onChange={handleChange}
          />
          <div className="flex gap-3 justify-between">
            <FormInput
              label="Mức lương từ (Triệu)"
              name="salaryMin"
              type="number"
              value={formJobPost.salaryMin}
              onChange={handleChange}
            />
            <FormInput
              label="Mức lương đến (Triệu)"
              name="salaryMax"
              type="number"
              value={formJobPost.salaryMax}
              onChange={handleChange}
            />
          </div>
          <FormInput
            label="Hạn nộp hồ sơ"
            name="endedTime"
            type="datetime-local"
            value={formJobPost.endedTime}
            onChange={handleChange}
          />
        </div>
        <div className="flex-1 flex flex-col gap-2 bg-white p-3 rounded-2xl ">
          <SecondTitle>Danh mục</SecondTitle>

          <FormSelect
            label="Ngành nghề"
            name="industryId"
            selected={industries.data.find(
              (j) => j.id === formJobPost.industryId
            )}
            onChange={handleChange}
            options={industries.data}
            placeholder="Chọn ngành nghề"
          />

          <FormSelect
            label="Cấp bậc"
            name="levelId"
            selected={levels.data.find((j) => j.id === formJobPost.levelId)}
            onChange={handleChange}
            options={levels.data}
            placeholder="Chọn cấp bậc"
          />
          <FormSelect
            label="Loại nghề"
            name="jobTypeId"
            selected={jobTypes.data.find((j) => j.id === formJobPost.jobTypeId)}
            onChange={handleChange}
            options={jobTypes.data}
            placeholder="Chọn loại nghề"
          />
          <FormSelect
            label="Khu vực"
            name="regionId"
            selected={regions.data.find((r) => r.code === formJobPost.regionId)}
            onChange={handleChange}
            options={regions.data}
            placeholder="Chọn khu vực"
          />
          <div className="flex justify-end ">
            <PrimaryButton onClick={handleSubmit}>
              Đăng bài tuyển dụng
            </PrimaryButton>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddJobPost;
