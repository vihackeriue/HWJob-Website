import React from "react";
import { Link } from "react-router-dom";
import { tGlobal } from "../../../utils/translator";

const RecruiterCard = ({ recruiter }) => {
  return (
    <div className="flex flex-col gap-1 items-center">
      <img
        src={recruiter.image}
        alt="logo"
        className="size-32 border border-gray-50 dark:border-gray-700 rounded-xl shadow-lg"
      />
      <Link
        to={"/login"}
        className="text-lg font-semibold hover:text-brightOrange"
      >
        {recruiter.name}
      </Link>
      <div className="flex gap-3 text-md">
        <p>
          {tGlobal("common.recruiter.post")}:{" "}
          <span className="font-semibold">{recruiter.totalPost}</span>
        </p>
        <p>
          {tGlobal("common.recruiter.recruiting")}:{" "}
          <span className="text-brightOrange font-semibold">
            {recruiter.recruiting}
          </span>
        </p>
      </div>
    </div>
  );
};

export default RecruiterCard;
