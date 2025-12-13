import React, { useState } from "react";
import { IoIosHeartEmpty, IoMdHeart } from "react-icons/io";
import { tGlobal } from "../../../utils/translator";
import { Link } from "react-router-dom";
import { hasRole } from "../../../utils/permission";
import { ROLES } from "../../../config/roles";
import useAuth from "../../../hooks/useAuth";
const JobPostCard = ({ jobPost }) => {
  const [isLiked, setIsSaved] = useState(jobPost.saved);
  const { auth } = useAuth();
  const handleToggleSave = () => {
    if (!isLiked) {
      // like Job
    } else {
      // unlike Job
    }
    setIsSaved(!isLiked);
  };
  const getJobPostDetailUrl = () => {
    if (hasRole(auth, ROLES.RECRUITER) && jobPost.recruiter.id === auth.id) {
      return `/recruiter/job-post/${jobPost.id}`;
    }
    return `/job-post/${jobPost.id}`;
  };
  return (
    <div className="flex gap-3 pr-2 bg-lightGrayishBlue dark:bg-stoneBrown-900 rounded-2xl relative hover:shadow border border-gray-300">
      <img
        src={jobPost.recruiter.imageUrl}
        alt={jobPost.recruiter.fullName}
        className="size-32 rounded-2xl "
      />
      <div className="py-2">
        <Link
          to={getJobPostDetailUrl()}
          className="text-md font-semibold hover-bright-orange line-clamp-2 "
        >
          {jobPost.title}
        </Link>
        <p className="dark:text-gray-300">{jobPost.recruiter.fullName}</p>
        <div className="flex gap-1 dark:text-amber-500">
          <p className="py-1 px-2 rounded-lg bg-white dark:bg-stoneBrown-700">
            {tGlobal("common.quantity")}: <span>{jobPost.quantity}</span>
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
