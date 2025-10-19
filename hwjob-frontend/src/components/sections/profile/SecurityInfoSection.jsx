import React, { useState } from "react";
import SecondTitle from "../../ui/title/SecondTitle";
import FormInput from "../../ui/form/FormInput";
import { CiEdit } from "react-icons/ci";

const SecurityInfoSection = () => {
  const [formSecurityInf, setFormSecurityInf] = useState({
    username: "wainrp",
    email: "vana@example.com",
    phone: "0765455423",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormSecurityInf((prev) => ({ ...prev, [name]: value }));
  };
  return (
    <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
      <SecondTitle>Thông tin bảo mật</SecondTitle>
      <FormInput
        label="Username"
        name="username"
        value={formSecurityInf.username}
        onChange={handleChange}
        type="text"
        readOnly
      />
      <FormInput
        label="Email"
        name="email"
        value={formSecurityInf.email}
        onChange={handleChange}
        type="email"
        readOnly
      />
      <FormInput
        label="Số điện thoại"
        name="phone"
        value={formSecurityInf.phone}
        onChange={handleChange}
        type="number"
      />

      <div className="flex gap-3 justify-end mt-3">
        <button className="flex gap-1 items-center bg-brightOrange px-3 py-2 rounded-lg text-gray-100">
          <CiEdit size={20} />
          <span>Thay đổi mật khẩu</span>
        </button>
        <button className="flex gap-1 items-center bg-brightOrange px-3 py-2 rounded-lg text-gray-100">
          <CiEdit size={20} />
          <span>Xác minh email</span>
        </button>
        <button className="flex gap-1 items-center bg-brightOrange px-3 py-2 rounded-lg text-gray-100">
          <CiEdit size={20} />
          <span>Chỉnh sửa</span>
        </button>
      </div>
    </div>
  );
};

export default SecurityInfoSection;
