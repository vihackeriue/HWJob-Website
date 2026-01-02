import classNames from "classnames";
import React from "react";

const SecondTitle = ({ children, className }) => {
  return (
    <h2 className={classNames("text-2xl font-semibold ", className)}>
      {children}
    </h2>
  );
};

export default SecondTitle;
