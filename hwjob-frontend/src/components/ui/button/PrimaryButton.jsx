import classNames from "classnames";

const VARIANTS = {
  primary: "bg-brightOrange text-gray-100 hover:bg-orange-600",
  cancel: "bg-gray-200 text-gray-700 hover:bg-gray-300",
  danger: "bg-red-500 text-white hover:bg-red-600",
  success: "bg-green-600 text-white hover:bg-green-700",
  outline:
    "bg-white text-brightOrange border border-brightOrange hover:bg-orange-50",
};

const PrimaryButton = ({
  children,
  onClick,
  className = "",
  type = "button",
  disabled = false,
  variant = "primary",
}) => {
  return (
    <button
      type={type}
      disabled={disabled}
      onClick={onClick}
      className={classNames(
        `
        flex items-center justify-center gap-2
        px-3 py-2
        rounded-lg
        text-sm font-medium
        transition-all duration-200
        disabled:bg-gray-300 disabled:cursor-not-allowed
        `,
        VARIANTS[variant],
        className
      )}
    >
      {children}
    </button>
  );
};

export default PrimaryButton;
