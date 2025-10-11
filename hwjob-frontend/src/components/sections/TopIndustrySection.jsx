import React from "react";
import Slider from "react-slick";

const TopIndustrySection = () => {
  const settings = {
    infinite: true,

    slidesToShow: 3,
    slidesToScroll: 1,
    speed: 500,
    rows: 2,
    slidesPerRow: 2,
    dots: false,
    arrows: false,
  };
  return (
    <div className="slider-container w-full">
      <Slider {...settings}>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">1</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">2</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
        <div>
          <h3 className="w-20 h-20 bg-amber-400">3</h3>
        </div>
      </Slider>
    </div>
  );
};

export default TopIndustrySection;
