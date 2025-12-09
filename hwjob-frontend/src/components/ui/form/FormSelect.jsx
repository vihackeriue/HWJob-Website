import {
  Combobox,
  ComboboxButton,
  ComboboxInput,
  ComboboxOption,
  ComboboxOptions,
} from "@headlessui/react";
import classNames from "classnames";
import { useState } from "react";
import { FaAngleDown } from "react-icons/fa";
import { IoCheckmarkSharp } from "react-icons/io5";

/**
 * @param {string} label - Nhãn hiển thị trên input
 * @param {array} options - Danh sách option [{ id, name }]
 * @param {object} selected - Giá trị đang chọn
 * @param {function} onChange - Hàm gọi khi chọn option
 * @param {string} placeholder - Placeholder hiển thị trong input
 */
export default function FormSelect({
  label,
  options = [],
  selected,
  onChange,
  placeholder = "Chọn một mục...",
  name, // 🚀 ADD name để giống input
  error,
}) {
  const [query, setQuery] = useState("");

  const filtered =
    query === ""
      ? options
      : options.filter((item) =>
          item.name.toLowerCase().includes(query.toLowerCase())
        );

  return (
    <div className="w-full">
      <div className="flex justify-between items-center mb-1">
        {label && (
          <label className="text-md font-medium" htmlFor={name}>
            {label}
          </label>
        )}
        {error && <span className="text-red-500 text-sm">{error}</span>}
      </div>

      <Combobox
        value={selected ?? null}
        onChange={(value) => {
          // 🚀 Quan trọng nhất → trả event giống input
          onChange({
            target: {
              name,
              value: value.id ?? value.code ?? value.name,
            },
          });

          setQuery("");
        }}
        onClose={() => setQuery("")}
      >
        <div className="relative">
          <ComboboxInput
            className={classNames(
              "w-full rounded-lg border border-gray-300 bg-gray-100 dark:bg-stoneBrown-900 dark:border-gray-500 px-3 py-2 text-md",
              "focus:outline-none focus:ring-1 focus:ring-amber-100 dark:focus:border-ring-amber-500 "
            )}
            placeholder={placeholder}
            displayValue={(item) => item?.name || ""}
            onChange={(event) => setQuery(event.target.value)}
          />

          <ComboboxButton className="group absolute inset-y-0 right-0 flex items-center pr-3">
            <FaAngleDown className="size-5 text-gray-500 group-data-hover:text-brightOrange" />
          </ComboboxButton>
        </div>

        <ComboboxOptions
          anchor="bottom"
          transition
          className={classNames(
            "z-10 mt-2 max-h-60 w-(--input-width) overflow-auto rounded-xl border border-gray-200 bg-white p-1 shadow-lg",
            "transition duration-100 ease-in data-leave:data-closed:opacity-0"
          )}
        >
          {filtered.length === 0 ? (
            <div className="px-3 py-2 text-sm text-gray-500">
              Không tìm thấy kết quả
            </div>
          ) : (
            filtered.map((item) => (
              <ComboboxOption
                key={item.id ?? item.code}
                value={item}
                className={({ active, selected }) =>
                  classNames(
                    "cursor-pointer select-none rounded-md px-3 py-2 text-sm flex items-center justify-between",
                    active
                      ? "bg-brightOrange text-white"
                      : "text-gray-800 hover:bg-gray-100",
                    selected && "font-semibold"
                  )
                }
              >
                {({ selected }) => (
                  <>
                    <span>{item.name}</span>
                    {selected && (
                      <IoCheckmarkSharp className="size-4 text-brightOrange" />
                    )}
                  </>
                )}
              </ComboboxOption>
            ))
          )}
        </ComboboxOptions>
      </Combobox>
    </div>
  );
}
