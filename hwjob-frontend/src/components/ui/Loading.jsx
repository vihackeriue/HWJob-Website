import React from "react";
import Lottie from "lottie-react";
import loading from "../../assets/lottie/loading.json";

const Loading = ({ size = 24 }) => {
  const dimension = typeof size === "number" ? `${size}px` : size;
  return (
    <span className="inline-block align-middle">
      <Lottie
        animationData={loading}
        loop
        style={{ width: dimension, height: dimension }}
      />
    </span>
  );
};

export default Loading;
