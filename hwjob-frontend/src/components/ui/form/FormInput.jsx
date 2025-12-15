import React from "react";

const FormInput = ({
                       label,
                       type = "text",
                       value,
                       onChange,
                       name,
                       error,
                       className = "",
                       ...props
                   }) => {
    return (
        <div className="flex flex-col">
            <div className="flex justify-between items-center mb-1">
                {label && (
                    <label className="text-md font-medium" htmlFor={name}>
                        {label}
                    </label>
                )}
                {error && <span className="text-red-500 text-sm">{error}</span>}
            </div>

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
