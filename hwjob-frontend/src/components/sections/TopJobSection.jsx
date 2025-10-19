import React from "react";
import Slider from "react-slick";
import PrimaryTitle from "../ui/title/PrimaryTitle";
import JobPostCard from "../ui/cards/JobPostCard";
import { tGlobal } from "../../utils/translator";

export const TopJobSection = ({ jobPosts }) => {
  const settings = {
    infinite: true,
    slidesToShow: 4,
    slidesToScroll: 1,
    vertical: true,
    verticalSwiping: true,
    swipeToSlide: true,
    arrows: false,
    dots: false,
    beforeChange: function (currentSlide, nextSlide) {
      console.log("before change", currentSlide, nextSlide);
    },
    afterChange: function (currentSlide) {
      console.log("after change", currentSlide);
    },
  };

  return (
    <section className="p-3 bg-white dark:bg-stoneBrown-900/50 rounded-2xl">
      <PrimaryTitle>{tGlobal("common.title.topJob")}</PrimaryTitle>
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
