import { Switch } from "@headlessui/react";

import React from "react";
import SecondTitle from "../title/SecondTitle";

const FormSwitch = ({ title, options, selected, onChange }) => {
  const handleToggle = (id) => {
    if (selected.includes(id)) {
      // Bỏ chọn
      onChange(selected.filter((item) => item !== id));
    } else {
      // Chọn thêm
      onChange([...selected, id]);
    }
  };
  return (
    <div>
      <h1 className="text-md font-medium mb-1 ">{title}</h1>
      <div className="grid grid-cols-2 gap-3">
        {options.map((opt) => (
          <div
            key={opt.id}
            className="flex items-center gap-3 px-4 py-2  transition"
          >
            <Switch
              checked={selected.includes(opt.id)}
              onChange={() => handleToggle(opt.id)}
              className={`${
                selected.includes(opt.id)
                  ? "bg-brightOrange"
                  : "bg-gray-200 dark:bg-gray-700"
              } relative inline-flex h-6 w-11 items-center rounded-full transition-all`}
            >
              <span
                className={`${
                  selected.includes(opt.id) ? "translate-x-6" : "translate-x-1"
                } inline-block h-4 w-4 transform rounded-full bg-white transition`}
              />
            </Switch>
            <span className="font-medium ">{opt.title}</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FormSwitch;
