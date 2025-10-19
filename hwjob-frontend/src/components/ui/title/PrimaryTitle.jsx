import React from "react";

const PrimaryTitle = ({ children }) => {
  return (
    <h1
      className="relative text-3xl font-bold dark:text-brightOrange py-2 pl-2 w-fit mb-3
    after:content-[''] after:block after:w-1/2 after:border-b-6  after:mt-1 after:border-brightOrange/80"
    >
      {children}
    </h1>
  );
};

export default PrimaryTitle;
