import React from "react";
import Lottie from "lottie-react";
import loading from "../../assets/lottie/loading.json";

const Loading = () => {
  return (
    <div className="flex justify-center items-center ">
      <Lottie animationData={loading} loop className="w-40 h-40" />
    </div>
  );
};

export default Loading;
