import React from "react";
import Slider from "react-slick";

export const BestJobSection = () => {
  const settings = {
    infinite: true,
    slidesToShow: 5,
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
    <div className="slider-container w-full">
      <Slider {...settings}>
        <div className="w-100 h-10 bg-amber-400">
          <h3>1</h3>
        </div>
        <div className="w-100 h-10 bg-amber-400">
          <h3>1</h3>
        </div>
        <div className="w-100 h-10 bg-amber-400">
          <h3>1</h3>
        </div>
        <div className="w-100 h-10 bg-amber-400">
          <h3>1</h3>
        </div>
        <div className="w-100 h-10 bg-amber-400">
          <h3>1</h3>
        </div>
        <div className="w-100 h-10 bg-amber-400">
          <h3>1</h3>
        </div>
      </Slider>
    </div>
  );
};
