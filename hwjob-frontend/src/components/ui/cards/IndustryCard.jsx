import React from "react";
import { tGlobal } from "../../../utils/translator";

const IndustryCard = ({ industry }) => {
  return (
    <div className="flex flex-col gap-3 items-center bg-lightGrayishBlue dark:bg-stoneBrown-900 rounded-2xl m-2 p-3 hover:shadow-lg hover:shadow-amber-50 dark:hover:shadow-gray-700">
      <h1 className="text-xl font-semibold whitespace-nowrap overflow-hidden text-ellipsis max-w-full text-center hover-bright-orange">
        {industry.name}
      </h1>
      <p>
        <span className="font-semibold">{industry.totalJob} </span>
        {tGlobal("common.jobs")}
      </p>
    </div>
  );
};

export default IndustryCard;
