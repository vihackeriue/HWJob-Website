import React from "react";

const PrimaryButton = ({ children, onclick }) => {
  return (
    <button
      className="text-lg px-3 py-2 bg-brightOrange text-gray-100 rounded-xl "
      onclick={onclick}
    >
      {children}
    </button>
  );
};

export default PrimaryButton;
