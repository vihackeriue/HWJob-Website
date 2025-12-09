import React, { useState } from "react";
import FormInput from "../ui/form/FormInput";
import { updatePasswordUser } from "../../services/userService";
import PrimaryButton from "../ui/button/PrimaryButton";

export const UpdatePasswordDialog = ({ open, onClose }) => {
  const [formChangePassword, setFormChangePassword] = useState({
    oldPassword: "",
    newPassword: "",
    reNewPassword: "",
  });
  const [errors, setErrors] = useState({});
  if (!open) return null;
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormChangePassword((prev) => ({ ...prev, [name]: value }));
  };
  const validate = () => {
    const newErrors = {};

    if (!formChangePassword.oldPassword.trim())
      newErrors.oldPassword = "Vui lòng nhập mật khẩu cũ";
    if (!formChangePassword.newPassword.trim())
      newErrors.newPassword = "Vui lòng nhập mật khẩu mới";
    if (!formChangePassword.reNewPassword.trim())
      newErrors.reNewPassword = "Vui lòng nhập lại mật khẩu mới";
    if (formChangePassword.newPassword !== formChangePassword.reNewPassword)
      newErrors.reNewPassword = "Mật khẩu mới không khớp";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();
    if (!validate()) return;
    try {
      const data = {
        oldPassword: formChangePassword.oldPassword,
        newPassword: formChangePassword.newPassword,
      };
      await updatePasswordUser(data);
      alert("thay đổi thành công");
    } catch (error) {
      alert(error.response?.data?.message || "Đăng ký tài khoản thất bại!");
    }
  };
  return (
    <div className="fixed inset-0 bg-black/30 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-lg w-[500px] p-5">
        <h2 className="text-xl font-semibold mb-4">Thay đổi mật khẩu</h2>
        <form onSubmit={handleChangePassword} className="flex flex-col gap-4">
          <FormInput
            label="Mật khẩu cũ"
            name="oldPassword"
            value={formChangePassword.oldPassword}
            onChange={handleChange}
            error={errors.oldPassword}
          />
          <FormInput
            label="Mật khẩu mới"
            name="newPassword"
            value={formChangePassword.newPassword}
            onChange={handleChange}
            error={errors.newPassword}
          />
          <FormInput
            label="Nhập lại mật khẩu mới"
            name="reNewPassword"
            value={formChangePassword.reNewPassword}
            onChange={handleChange}
            error={errors.reNewPassword}
          />
          <div className="flex justify-end gap-3">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 rounded-lg border border-gray-300 hover:bg-gray-100"
            >
              Hủy
            </button>
            <PrimaryButton
              type="submit"
              className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700"
            >
              Cập nhật
            </PrimaryButton>
          </div>
        </form>
      </div>
    </div>
  );
};
