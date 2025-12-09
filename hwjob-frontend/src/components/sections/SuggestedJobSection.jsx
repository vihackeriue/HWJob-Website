import React from "react";
import PrimaryTitle from "../ui/title/PrimaryTitle";
import Slider from "react-slick";
import JobPostCard from "../ui/cards/JobPostCard";
import { tGlobal } from "../../utils/translator";

const SuggestedJobSection = ({ jobPosts }) => {
  const settings = {
    infinite: true,
    dots: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    speed: 500,
    rows: 1,
    slidesPerRow: 2,
    appendDots: (dots) => (
      <div className="dark:bg-gray-100">
        <ul style={{ margin: "0px" }}> {dots} </ul>
      </div>
    ),
    customPaging: () => (
      <div
        className="
      w-2 h-2 mt-5 rounded-full 
      bg-gray-500 
      dark:bg-gray-600
      transition-all duration-300
    "
      ></div>
    ),

    arrows: false,
  };
  return (
    <section className="bg-white rounded-2xl p-3 pb-10 dark:bg-stoneBrown-900/50">
      <PrimaryTitle>{tGlobal("common.title.suggestedJob")}</PrimaryTitle>
      <div className="slider-container w-full">
        <Slider {...settings}>
          {jobPosts.map((item) => (
            <JobPostCard jobPost={item} />
          ))}
        </Slider>
      </div>
    </section>
  );
};

export default SuggestedJobSection;
