import React from "react";

const ProgressBar = ({ title, value }) => {
  return (
    <div className="p-1">
      <p className="text-md">
        {title}: {value}%
      </p>
      <div className="h-1 w-full bg-gray-500 my-3">
        <div
          className="h-1 bg-brightOrange transition-all"
          style={{ width: `${value}%` }}
        ></div>
      </div>
    </div>
  );
};

export default ProgressBar;
