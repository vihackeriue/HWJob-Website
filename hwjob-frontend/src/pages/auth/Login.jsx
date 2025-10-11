import Lottie from "lottie-react";
import React, { useState } from "react";
import jobSeeking from "../../assets/lottie/job_seeking.json";
import Browsing from "../../assets/lottie/Browsing.json";
import { FcGoogle } from "react-icons/fc";
import { FaFacebook } from "react-icons/fa";
import { useTranslation } from "react-i18next";
import useAuth from "../../hooks/useAuth";

function Login() {
  const { t } = useTranslation();
  const { login } = useAuth();

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
      await login(input);
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
          className="absolute  -left-1/4 -top-1/4 hidden sm:block w-80 h-80"
        />
        <div className="sm:w-2/3 px-16">
          <h2 className="text-6xl font-bold font-imperial ">
            {t("auth.title.login")}
          </h2>
          <p className="text-sm sm:text-lg mt-4 text-gray-600">
            {t("auth.description.login")}
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
              className="p-2 py-2 rounded-xl border"
              type="password"
              name="password"
              id="password"
              placeholder={t("auth.password")}
              onChange={handleInput}
            />

            <button
              type="submit"
              className="bg-teal-700 text-white rounded-xl py-2 hover:scale-105 duration-300"
            >
              {t("auth.login")}
            </button>
            <a
              href="#"
              className="text-md text-red-500  hover:no-underline text-end"
            >
              {t("auth.forgotPassword")}
            </a>
          </form>

          <div className="mt-6 text-gray-500 mb-4">
            <hr className="border-gray-400" />
          </div>
          <div className="grid grid-cols-2 sm:grid-cols-2 gap-4">
            <div
              onClick={() => alert("Google Sign-In")}
              className="flex justify-center items-center gap-1 p-2 rounded-xl cursor-pointer hover:scale-105 duration-300  bg-brightOrange text-gray-100"
            >
              <FcGoogle />
              <p>Google</p>
            </div>
            <div
              onClick={() => alert("Google Sign-In")}
              className="flex justify-center items-center bg-teal-700 text-gray-100 gap-1 p-2 rounded-xl cursor-pointer hover:scale-105 duration-300 "
            >
              <FaFacebook />
              <p>FaceBook</p>
            </div>
          </div>

          <div className="flex gap-2 items-center mt-2">
            <p className="text-sm text-gray-700">{t("auth.noAccount")}</p>
            <a
              href="/register"
              className=" text-teal-700 hover:no-underline hover:font-semibold"
            >
              {t("auth.signUp")}
            </a>
          </div>
        </div>
        <div className="sm:block hidden w-1/3">
          <Lottie
            animationData={jobSeeking}
            loop={true}
            className="w-100 h-100"
          />
        </div>
      </div>
    </section>
  );
}

export default Login;
