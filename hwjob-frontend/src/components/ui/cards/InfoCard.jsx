import React from "react";

const InfoCard = ({ icon, label, value }) => {
  return (
    <div className="flex flex-row gap-3 items-center px-2 py-1 border border-gray-300 rounded-xl bg-lightGrayishBlue">
      {icon}
      <div>
        <p>{label}</p>
        <p className="font-semibold">{value}</p>
      </div>
    </div>
  );
};

export default InfoCard;
