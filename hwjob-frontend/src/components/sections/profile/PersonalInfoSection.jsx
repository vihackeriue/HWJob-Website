import React, { useState } from "react";
import SecondTitle from "../../ui/title/SecondTitle";
import FormInput from "../../ui/form/FormInput";
import FormSelect from "../../ui/form/FormSelect";
import { CiEdit } from "react-icons/ci";

const gender = [
  { id: 1, name: "Nam" },
  { id: 2, name: "Nữ" },
  { id: 3, name: "Khác" },
];

const PersonalInfoSection = () => {
  const [formPersonalInf, setFormPersonalInf] = useState({
    name: "Nguyễn Văn A",
    gender: 1,
    dob: "01/12/2003",

    education: "Đại học",
    region: "",
    salary_expected: "",
    summary: "",
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormPersonalInf((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
      <SecondTitle>Thông tin cá nhân</SecondTitle>
      <FormInput
        label="Họ và Tên"
        name="name"
        value={formPersonalInf.name}
        onChange={handleChange}
      />
      <FormSelect
        label="Giới tính"
        name="gender"
        value={formPersonalInf.gender}
        onChange={handleChange}
        options={gender}
        placeholder="Chọn giới tính"
      />

      <FormInput
        label="Ngày Sinh"
        name="dob"
        value={formPersonalInf.dob}
        onChange={handleChange}
        type="date"
      />
      <FormInput
        label="Địa chỉ"
        name="address"
        value={formPersonalInf.address}
        onChange={handleChange}
        type="text"
      />
      <FormInput
        label="Trình độ"
        name="education"
        value={formPersonalInf.education}
        onChange={handleChange}
        type="text"
      />
      <FormInput
        label="Khu vực/ Thành phố"
        name="region"
        value={formPersonalInf.region}
        onChange={handleChange}
        type="text"
      />
      <FormInput
        label="Mức lương mong đợi"
        name="salary_expected"
        value={formPersonalInf.salary_expected}
        onChange={handleChange}
        type="text"
      />
      <div>
        <label className="block text-lg font-medium">Tóm tắt bản thân</label>
        <textarea
          type="text"
          name="summary"
          value={formPersonalInf.summary}
          className="mt-1 block w-full border px-3 py-2 rounded-md bg-gray-100 border-gray-400"
          rows={5}
          placeholder="Giới thiệu ngắn gọn về bản thân..."
        />
      </div>
      <div className="flex gap-3 justify-end mt-3">
        <button className="flex gap-1 items-center bg-brightOrange px-3 py-2 rounded-lg text-gray-100">
          <CiEdit size={20} />
          <span>Cập nhật</span>
        </button>
      </div>
    </div>
  );
};

export default PersonalInfoSection;
