import React from "react";

const QuantityStatisticsCard = ({ stat, title, icon }) => {
  return (
    <div className="bg-lightGrayishBlue p-4 flex-1 flex items-center rounded-lg">
      <div className="rounded-full w-12 h-12 flex items-center justify-center bg-primary">
        {icon}
      </div>
      <div className="pl-4">
        <p className="text-sm text-gray-500 font-semibold">{title}</p>
        <div className="flex items-center">
          <p className="text-xl text-gray-700 font-semibold">{stat}</p>
        </div>
      </div>
    </div>
  );
};

export default QuantityStatisticsCard;
