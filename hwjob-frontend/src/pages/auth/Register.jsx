import Lottie from "lottie-react";
import React, { useState } from "react";
import jobSeeking from "../../assets/lottie/job_seeking.json";
import Browsing from "../../assets/lottie/Browsing.json";
import { FcGoogle } from "react-icons/fc";
import { FaFacebook } from "react-icons/fa";
import { useTranslation } from "react-i18next";
export default function Register() {
  const { t } = useTranslation();
  const [input, setInput] = useState({
    username: "",
    password: "",
  });

  const handleInput = (e) => {
    const { name, value } = e.target;
    setInput((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (input.username !== "" && input.password !== "") {
      // await login(input);
      return;
    }
    alert("please provide a valid input");
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
            <input
              className="p-2 mt-8 rounded-xl border "
              type="text"
              name="username"
              id="username"
              placeholder={t("auth.username")}
              onChange={handleInput}
            />
            <input
              className="p-2 py-2 rounded-xl border "
              type="email"
              name="email"
              id="email"
              placeholder={t("auth.email")}
              onChange={handleInput}
            />
            <input
              className="p-2 py-2 rounded-xl border"
              type="password"
              name="password"
              id="password"
              placeholder={t("auth.password")}
              onChange={handleInput}
            />
            <input
              className="p-2 py-2 rounded-xl border"
              type="password"
              name="rePassword"
              id="rePassword"
              placeholder={t("auth.rePassword")}
              onChange={handleInput}
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
