import Lottie from "lottie-react";
import React from "react";
import recruitment from "../../assets/images/recruitment.png";
import PrimaryButton from "../ui/button/PrimaryButton";
import { MdDiscount, MdWorkspacePremium } from "react-icons/md";
import { FaFilterCircleDollar } from "react-icons/fa6";
import { tGlobal } from "../../utils/translator";
const IntroduceRecruiterSection = () => {
  return (
    <div>
      <div className="flex flex-col md:flex-row items-center p-2 bg-stoneBrown-900/50 rounded-2xl ">
        <img src={recruitment} alt="recruitment" className="size-40" />

        <div className="flex flex-col-reverse md:flex-row justify-around w-full p-3">
          <div>
            <h1 className="text-2xl sm:text-3xl text-teal-900 dark:text-brightOrange  font-semibold">
              {tGlobal(`user.feature.recruitment.title`)}
            </h1>
            {tGlobal("user.feature.recruitment.desc", {
              returnObjects: true,
            }).map((line, index) => (
              <p
                key={index}
                className="px-2 py-1  mt-2 text-gray-100 bg-stoneBrown-700 rounded-lg "
              >
                {line}
              </p>
            ))}

            <div className="flex justify-end mt-3">
              <PrimaryButton>Tuyển dụng ngay</PrimaryButton>
            </div>
          </div>
          <div className="flex flex-col gap-3 mb-3 sm:mb-0">
            <div className="flex gap-3 items-center border px-3 py-1 rounded-xl bg-stoneBrown-900 shadow-lg">
              <MdWorkspacePremium className="text-amber-500 size-10" />
              <p className="text-gray-300">
                {tGlobal("user.feature.recruitment.benefit.0")}
              </p>
            </div>
            <div className="flex gap-3 items-center border px-3 py-1 rounded-xl bg-stoneBrown-900">
              <FaFilterCircleDollar className="text-amber-500 size-10" />
              <p className="text-gray-300">
                {tGlobal("user.feature.recruitment.benefit.1")}
              </p>
            </div>
            <div className="flex gap-3 items-center border px-3 py-1 rounded-xl bg-stoneBrown-900">
              <MdDiscount className="text-amber-500 size-10" />
              <p className="text-gray-300">
                {tGlobal("user.feature.recruitment.benefit.2")}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default IntroduceRecruiterSection;
