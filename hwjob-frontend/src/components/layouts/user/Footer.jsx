import React from "react";
import { NAVBAR_USER_LINKS } from "../../../constants/user/navigation";
import { FaFacebook, FaInstagram, FaLinkedin } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

export default function Footer() {
  const { t } = useTranslation();
  return (
    <div className="bg-teal-900 text-gray-100">
      <section className="container p-10">
        <div className="py-8 px-4 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {/* Cột 1 */}
          <div>
            <h1 className="sm:text-3xl text-xl font-bold text-center sm:text-left mb-3 text-brightOrange font-vollkorn">
              HWJOB
            </h1>
            <p className="text-sm text-secondary-700 text-center sm:text-left">
              Lorem ipsum dolor sit amet consectetur adipisicing elit.
            </p>
            <div className="flex justify-center sm:justify-start items-center gap-4 mt-6">
              <FaInstagram className="text-2xl hover:text-primary duration-300" />
              <FaFacebook className="text-2xl hover:text-primary duration-300" />
              <FaLinkedin className="text-2xl hover:text-primary duration-300" />
            </div>
          </div>

          {/* Cột 2 */}
          <div>
            <h2 className="text-xl font-semibold text-center sm:text-left">
              Chức năng
            </h2>
            <div className="flex flex-col mt-6 items-center sm:items-start">
              {NAVBAR_USER_LINKS.map((item) => (
                <Link
                  to={item.path}
                  key={item.key}
                  className="text-lg font-medium hover:text-primary py-2 transition-colors duration-500"
                >
                  {t(item.label)}
                </Link>
              ))}
            </div>
          </div>

          {/* Cột 3 */}
          <div>
            <h2 className="text-xl font-semibold text-center sm:text-left">
              Liên hệ
            </h2>
            <div className="flex flex-col gap-2 mt-6 items-center sm:items-start">
              <p className="font-bold">
                Email:{" "}
                <span className="font-medium italic">admin@wainrp.com</span>
              </p>
              <form className="flex flex-col sm:flex-row gap-2 mt-4 w-full">
                <input
                  type="text"
                  name="content"
                  className="flex-1 bg-white rounded-md p-3 border border-dark-50 text-black"
                  placeholder="Nhập nội dung"
                />
                <button className="shrink-0">Gửi email</button>
              </form>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
