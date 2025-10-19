import React from "react";
import resume from "../../assets/images/resume.png";
import IntroduceFeatureCard from "../ui/cards/IntroduceFeatureCard";
import badge from "../../assets/images/badge.png";
import streak from "../../assets/lottie/streak.json";
import Lottie from "lottie-react";
import PrimaryTitle from "../ui/title/PrimaryTitle";
import { PiCoinsFill } from "react-icons/pi";
import { FaCalendarCheck } from "react-icons/fa";
import { BsCalendar2CheckFill } from "react-icons/bs";
import PrimaryButton from "../ui/button/PrimaryButton";
import { tGlobal } from "../../utils/translator";

const FeatureSection = () => {
  return (
    <section className="flex flex-col gap-3 my-10">
      <PrimaryTitle>Tính năng nổi bật</PrimaryTitle>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-5 justify-between ">
        <IntroduceFeatureCard img={resume} content="user.feature.resume" />
        <IntroduceFeatureCard img={badge} content="user.feature.badge" />
      </div>
      <div className="flex flex-col md:flex-row items-center p-2 bg-stoneBrown-900/50 rounded-2xl">
        <Lottie animationData={streak} loop className="size-40" />
        <div className="flex flex-col sm:flex-row justify-around gap-3 w-full p-3 ">
          <div className="sm:flex-1">
            <h1 className="text-2xl md:text-3xl text-teal-900 dark:text-teal-100 font-semibold text-center md:text-left">
              {tGlobal("user.feature.streak.title")}
            </h1>

            <p className="hidden md:block">
              {tGlobal(`user.feature.streak.desc`)}
            </p>
          </div>
          <div className="flex flex-col gap-3 sm:flex-shrink-0 mt-2 md:mt-0">
            <div className="flex gap-3 items-center justify-center border px-3 py-1 rounded-xl bg-stoneBrown-900">
              <PiCoinsFill size={40} className="text-amber-500" />
              <p className="text-gray-100">
                {" "}
                {tGlobal("user.feature.streak.coin")}
              </p>
            </div>
            <PrimaryButton>{tGlobal("common.check_in")}</PrimaryButton>
          </div>
        </div>
      </div>
    </section>
  );
};

export default FeatureSection;
