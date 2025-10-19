import React from "react";

import { FaMinus, FaPlus } from "react-icons/fa";

const RangeInput = ({ labelFrom = "Từ", labelTo = "Đến", value, onChange }) => {
  const { from, to } = value;

  // Hàm thay đổi giá trị
  const updateValue = (key, val) => {
    const newVal = Number(val);
    if (newVal < 0) return;

    // Giữ ràng buộc: from < to
    if (key === "from" && newVal >= to) return;
    if (key === "to" && newVal <= from) return;

    onChange({ ...value, [key]: newVal });
  };

  // Hàm tăng/giảm
  const handleStep = (key, delta) => {
    const newVal = value[key] + delta;
    // Không cho nhỏ hơn 0
    if (newVal < 0) return;

    if (key === "from" && newVal >= to) return;
    if (key === "to" && newVal <= from) return;

    onChange({ ...value, [key]: newVal });
  };

  return (
    <div className="flex flex-col gap-4 p-4">
      <h1 className="text-md font-medium mb-1 ">Lương</h1>

      <div className="flex justify-between gap-5">
        {/* Ô bên trái */}
        <div className="flex items-center border border-gray-200  rounded-lg px-2 py-1.5 gap-2">
          <button
            onClick={() => handleStep("from", -1)}
            className="p-1 hover:bg-gray-100 rounded"
          >
            <FaMinus size={16} />
          </button>
          <input
            type="number"
            value={from}
            onChange={(e) => updateValue("from", e.target.value)}
            className="w-16 text-center outline-none"
          />
          <button
            onClick={() => handleStep("from", 1)}
            className="p-1 hover:bg-gray-100 rounded"
          >
            <FaPlus size={16} />
          </button>
        </div>
        {/* Ô bên phải */}
        <div className="flex items-center border border-gray-200 rounded-lg px-2 py-1.5 gap-2">
          <button
            onClick={() => handleStep("to", -1)}
            className="p-1 hover:bg-gray-100 rounded"
          >
            <FaMinus size={16} />
          </button>
          <input
            type="number"
            value={to}
            onChange={(e) => updateValue("to", e.target.value)}
            className="w-16 text-center outline-none"
          />
          <button
            onClick={() => handleStep("to", 1)}
            className="p-1 hover:bg-gray-100 rounded"
          >
            <FaPlus size={16} />
          </button>
        </div>
      </div>

      {/* Thông báo lỗi nếu vi phạm */}
      {from >= to && (
        <p className="text-sm text-red-500 text-center mt-2">
          ⚠️ Giá trị "Từ" phải nhỏ hơn "Đến"
        </p>
      )}
    </div>
  );
};

export default RangeInput;
