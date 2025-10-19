import React, { useState } from "react";
import { IoIosHeartEmpty, IoMdHeart } from "react-icons/io";
import { tGlobal } from "../../../utils/translator";
const JobPostCard = ({ jobPost }) => {
  const [isLiked, setIsSaved] = useState(jobPost.isLiked);

  const handleToggleSave = () => {
    if (!isLiked) {
      // like Job
    } else {
      // unlike Job
    }
    setIsSaved(!isLiked);
  };

  return (
    <div className="flex gap-3 m-3 pr-2 bg-lightGrayishBlue dark:bg-stoneBrown-900 rounded-2xl relative hover:shadow">
      <img
        src={jobPost.image}
        alt={jobPost.recruiter}
        className="size-32 rounded-2xl "
      />
      <div className="py-2">
        <h1 className="text-md font-semibold hover-bright-orange line-clamp-2 ">
          {jobPost.title}
        </h1>
        <p className="dark:text-gray-300">{jobPost.recruiter}</p>
        <div className="flex gap-1 dark:text-amber-500">
          <p className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {tGlobal("common.quantity")}: <span>{jobPost.quanlity}</span>
          </p>
          <div className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {jobPost.region}
          </div>
          <div className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {jobPost.industry}
          </div>
        </div>
      </div>
      <div className="absolute right-3 bottom-3 ">
        <button
          onClick={handleToggleSave}
          className="p-2 rounded-full hover:bg-gray-200 dark:hover:bg-stoneBrown-700 transition"
          title={isLiked ? tGlobal("common.unlike") : tGlobal("common.like")}
        >
          {isLiked ? (
            <IoMdHeart className="size-5 text-brightOrange" />
          ) : (
            <IoIosHeartEmpty className="size-5 text-gray-500" />
          )}
        </button>
      </div>
    </div>
  );
};

export default JobPostCard;
