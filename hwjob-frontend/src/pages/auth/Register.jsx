import Lottie from "lottie-react";
import React, { useState } from "react";
import jobSeeking from "../../assets/lottie/job_seeking.json";
import Browsing from "../../assets/lottie/Browsing.json";
import { FcGoogle } from "react-icons/fc";
import { FaFacebook } from "react-icons/fa";
import { useTranslation } from "react-i18next";
import FormSelect from "../../components/ui/form/FormSelect";
import FormInput from "../../components/ui/form/FormInput";
import { useNavigate } from "react-router-dom";
import { createUser } from "../../services/userService";
import { toast } from "react-toastify";
export default function Register() {
  const roles = [
    { code: "CANDIDATE", name: "Ứng viên" },
    { code: "RECRUITER", name: "Nhà tuyển dụng" },
  ];
  const { t } = useTranslation();
  const [formRegister, setFormRegister] = useState({
    username: "",
    email: "",
    password: "",
    rePassword: "",
    role: "CANDIDATE",
  });
  const navigate = useNavigate();
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormRegister((prev) => ({ ...prev, [name]: value }));
  };

  const [errors, setErrors] = useState({});
  const validate = () => {
    const newErrors = {};
    if (!formRegister.username.trim())
      newErrors.username = "Username is required!";
    if (!formRegister.email.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/))
      newErrors.email = "Invalid email!";
    if (formRegister.password.length < 2)
      newErrors.password = "Password must be at least 6 characters!";
    if (formRegister.password !== formRegister.rePassword)
      newErrors.rePassword = "Passwords do not match!";

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    try {
      const user = {
        username: formRegister.username,
        password: formRegister.password,
        email: formRegister.email,
        roles: [formRegister.role],
      };
      // gọi API backend đăng ký
      await createUser(user);
      toast.success("Đăng ký thành công");
      navigate("/login");
    } catch (error) {
      toast.error(
        error.response?.data?.message || "Đăng ký tài khoản thất bại!"
      );
    }
  };

  return (
    <section className="bg-stoneBrown-900/50 min-h-screen flex items-center justify-center">
      <div className="bg-lightGrayishBlue rounded-2xl flex shadow-lg max-w-3xl p-4 sm:p-10 items-center relative ">
        <Lottie
          animationData={Browsing}
          loop={true}
          style={{ width: 300, height: 300 }}
          className="absolute  -left-1/4 -top-1/10 hidden sm:block"
        />
        <div className="sm:w-2/3 px-16">
          <h2 className="text-6xl font-bold font-imperial ">
            {t("auth.title.register")}
          </h2>
          <p className="text-sm sm:text-lg mt-4 text-gray-600">
            {t("auth.description.register")}
          </p>

          <form onSubmit={handleSubmit} className="flex flex-col gap-4 ">
            <FormInput
              name="username"
              value={formRegister.username}
              error={errors.username}
              onChange={handleChange}
              placeholder={t("auth.username")}
            />

            <FormInput
              name="email"
              value={formRegister.email}
              error={errors.email}
              onChange={handleChange}
              placeholder={t("auth.email")}
            />

            <FormInput
              type="password"
              name="password"
              value={formRegister.password}
              error={errors.password}
              onChange={handleChange}
              placeholder={t("auth.password")}
            />

            <FormInput
              type="password"
              name="rePassword"
              value={formRegister.rePassword}
              error={errors.rePassword}
              onChange={handleChange}
              placeholder={t("auth.rePassword")}
            />
            <FormSelect
              name="role"
              selected={roles.find((j) => j.code === formRegister.role)}
              onChange={handleChange}
              options={roles}
              placeholder="Chọn ngành nghề"
            />

            <button
              type="submit"
              className="bg-teal-700 text-white rounded-xl py-2 hover:scale-105 duration-300"
            >
              {t("auth.signUp")}
            </button>
          </form>

          <div className="mt-6 text-gray-500 mb-4">
            <hr className="border-gray-400" />
          </div>

          <div className="flex gap-2 items-center mt-2">
            <p className="text-sm text-gray-700">{t("auth.haveAccount")}</p>
            <a
              href="/login"
              className=" text-teal-700  hover:no-underline hover:font-semibold"
            >
              {t("auth.login")}
            </a>
          </div>
        </div>
        <div className="sm:block hidden w-1/3">
          <Lottie
            animationData={jobSeeking}
            loop={true}
            style={{ width: 450, height: 450 }}
          />
        </div>
      </div>
    </section>
  );
}
