import React, { useState } from "react";
import Slider from "react-slick";

import find_work_banner from "../../assets/lottie/find_work_banner.json";
import freelancer_banner from "../../assets/lottie/freelancer_banner.json";
import job_seeking from "../../assets/lottie/job_seeking.json";
import Lottie from "lottie-react";
import { useTranslation } from "react-i18next";

const BannerSection = () => {
  const { t } = useTranslation();
  const [currentSlide, setCurrentSlide] = useState(0);
  const settings = {
    dots: true,
    fade: true,
    infinite: true,
    autoplay: true,
    speed: 500,
    autoplaySpeed: 3000,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    beforeChange: (oldIndex, newIndex) => setCurrentSlide(newIndex),
  };

  return (
    <section className=" bg-teal-100 dark:bg-teal-800">
      <div className="container min-h-[620px] flex">
        <div className="grid grid-cols-1 sm:grid-cols-2 mb-3 sm:mb-0 sm:gap-10 place-items-center">
          <div className="w-full overflow-hidden slider-container sm:order-2 ">
            <Slider {...settings}>
              <div className="flex justify-center">
                <Lottie
                  animationData={find_work_banner}
                  loop
                  className="w-100 h-100"
                />
              </div>
              <div className="flex justify-center">
                <Lottie
                  animationData={job_seeking}
                  loop
                  className="w-100 h-100"
                />
              </div>
              <div className="flex justify-center">
                <Lottie
                  animationData={freelancer_banner}
                  loop
                  className="w-100 h-100"
                />
              </div>
            </Slider>
          </div>
          <div className="text-center md:text-left px-6 sm:order-1 flex flex-col gap-3">
            <div>
              <h1 className="text-5xl sm:text-6xl font-bold font-vollkorn text-brightOrange ">
                HWJOB
              </h1>
              <p className="text-md">{t("user.banner.description")}</p>
            </div>
            <div className="mt-6">
              <h1 className="text-4xl font-semibold font-imperial">
                {t(`user.banner.slide${currentSlide + 1}.title`)}
              </h1>
              <p
                className="text-gray-600 mt-3 dark:text-gray-300"
                style={{ lineHeight: 1.2 }}
              >
                {t(`user.banner.slide${currentSlide + 1}.desc`)}
              </p>
            </div>
          </div>
          {/* chữ theo slider */}
        </div>
      </div>
    </section>
  );
};

export default BannerSection;
