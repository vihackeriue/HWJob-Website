import React from "react";

const QuantityStatisticsCard = ({ stat, title, icon }) => {
  return (
    <div className="bg-lightGrayishBlue p-2 flex-1 flex items-center rounded-xl shadow  hover:shadow-lg transition">
      <div className="rounded-xl p-3 flex items-center justify-center bg-gray-200">
        {icon}
      </div>
      <div className="pl-4">
        <p className="text-md font-semibold">{title}</p>
        <div className="flex items-center">
          <p className="text-xl text-gray-700 font-semibold">{stat}</p>
        </div>
      </div>
    </div>
  );
};

export default QuantityStatisticsCard;
