import React from "react";
import PrimaryTitle from "../ui/title/PrimaryTitle";
import Slider from "react-slick";
import RecruiterCard from "../ui/cards/RecruiterCard";
import {tGlobal} from "../../utils/translator";

const TopRecruiterSection = ({recruiters}) => {
    var settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 5,
        slidesToScroll: 5,
        arrows: false,

        initialSlide: 0,
        appendDots: (dots) => (
            <div className="dark:bg-gray-100">
                <ul style={{margin: "0px"}}> {dots} </ul>
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

        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3,
                    infinite: true,
                    dots: true,
                },
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2,
                    initialSlide: 2,
                },
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                },
            },
        ],
    };

    return (
        <section>
            <PrimaryTitle>{tGlobal("common.title.topRecruiter")}</PrimaryTitle>
            <div className="slider-container">
                <Slider {...settings}>
                    {recruiters.map((item) => (
                        <RecruiterCard recruiter={item}/>
                    ))}
                </Slider>
            </div>
        </section>
    );
};

export default TopRecruiterSection;
