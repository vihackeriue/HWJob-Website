import React from "react";

const FormInput = ({
  label,
  type = "text",
  value,
  onChange,
  name,
  className = "",
  ...props
}) => {
  return (
    <div className="flex flex-col">
      {label && (
        <label className="block text-md font-medium mb-1" htmlFor={name}>
          {label}
        </label>
      )}
      <input
        id={name}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        className={`block w-full border px-3 py-2 rounded-md bg-gray-100 dark:bg-stoneBrown-900 border-gray-300 dark:border-gray-500 focus:outline-none focus:ring-1 focus:ring-amber-100 dark:focus:border-ring-amber-500 transition ${className}`}
        {...props}
      />
    </div>
  );
};
export default FormInput;
