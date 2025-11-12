import React from "react";

const PrimaryButton = ({ children, onClick }) => {
  return (
    <button
      className="text-lg px-3 py-2 bg-brightOrange text-gray-100 rounded-xl "
      onClick={onClick}
    >
      {children}
    </button>
  );
};

export default PrimaryButton;
