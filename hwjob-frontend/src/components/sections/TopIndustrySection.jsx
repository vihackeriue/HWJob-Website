import React from "react";
import Slider from "react-slick";
import PrimaryTitle from "../ui/title/PrimaryTitle";
import IndustryCard from "../ui/cards/IndustryCard";
import { tGlobal } from "../../utils/translator";

const TopIndustrySection = ({ industries }) => {
  const settings = {
    infinite: true,

    slidesToShow: 3,
    slidesToScroll: 1,
    speed: 500,
    rows: 1,
    slidesPerRow: 2,
    dots: false,
    arrows: false,
  };
  return (
    <section className="bg-white rounded-2xl p-3 dark:bg-stoneBrown-900/50">
      <PrimaryTitle>{tGlobal("common.title.topIndustry")}</PrimaryTitle>
      <div className="slider-container w-full">
        <Slider {...settings}>
          {industries.map((item) => (
            <IndustryCard industry={item} />
          ))}
        </Slider>
      </div>
    </section>
  );
};

export default TopIndustrySection;
