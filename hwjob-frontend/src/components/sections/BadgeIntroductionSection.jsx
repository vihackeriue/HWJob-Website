import React from "react";
import Lottie from "lottie-react";

import badge from "../../assets/lottie/badge.json";
const BadgeIntroductionSection = ({ content }) => {
  return (
    <section className="flex flex-col md:flex-row gap-3 items-center p-4 bg-stoneBrown-900/50 rounded-2xl">
      <div className="">
        <Lottie animationData={badge} loop className="w-40 h-40" />
      </div>
      <div>
        <h1 className="text-2xl text-gray-100">
          {content("user.resumeIntroduction.title")}
        </h1>
        <div className="flex flex-col md:flex-row gap-3 items-center">
          <div className="md:w-2/3">
            <p>{content("user.resumeIntroduction.desc")}</p>
          </div>

          <div className="md:w-1/3">
            <button className="px-4 py-3  bg-brightOrange rounded-lg">
              Xem thêm
            </button>
          </div>
        </div>
      </div>
    </section>
  );
};

export default BadgeIntroductionSection;
