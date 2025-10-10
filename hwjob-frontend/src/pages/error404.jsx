import Lottie from "lottie-react";
import React from "react";

import error from "../assets/lottie/error404.json";

export default function error404() {
  return (
    <div className="w-screen h-screen flex justify-center items-center">
      <Lottie
        animationData={error}
        loop={true}
        style={{ width: 300, height: 300 }}
      />
    </div>
  );
}
