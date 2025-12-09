import React from "react";
import { FaEdit, FaLock, FaLockOpen } from "react-icons/fa";
import { RiDeleteBin6Line } from "react-icons/ri";
import Pagination from "../pagination/Pagination";

const FormTable = ({
  columns = [],
  data = [],
  isAct = true,
  isLock = false,
  onEdit = () => {},
  onDelete = () => {},
  onLock = () => {},
  pagination,
}) => {
  const hasData = Array.isArray(data) && data.length > 0;

  return (
    <div className="flex flex-col w-full rounded-2xl bg-white">
      {/* Header */}
      <div className="flex bg-gray-900 text-gray-100 font-semibold rounded-t-2xl">
        {columns.map((col) => (
          <div key={col.key} className="flex-1 p-2 border-b">
            {col.label}
          </div>
        ))}
        {isAct && <div className="p-2 border-b w-24">Hành động</div>}
        {isLock && <div className="p-2 border-b w-24">Trạng thái</div>}
      </div>

      {/* Rows */}
      {hasData ? (
        data.map((item) => (
          <div key={item.id} className="flex hover:bg-gray-50">
            {columns.map((col) => (
              <div key={col.key} className="flex-1 p-2">
                {item[col.key]}
              </div>
            ))}
            {isAct && (
              <div className="p-2 flex gap-3 w-24 justify-center">
                <button
                  className="text-teal-600 hover:scale-110 transition"
                  onClick={() => onEdit(item)}
                >
                  <FaEdit />
                </button>
                <button
                  className="text-red-600 hover:scale-110 transition"
                  onClick={() => onDelete(item.id)}
                >
                  <RiDeleteBin6Line />
                </button>
              </div>
            )}
            {isLock && (
              <div className="p-2 flex gap-3 w-24 justify-center">
                <button
                  className="text-blue-600 hover:scale-110 transition"
                  onClick={() => onLock(item)}
                >
                  {item.status === "ACTIVE" ? (
                    <FaLockOpen className="text-teal-600" />
                  ) : (
                    <FaLock className="text-red-600" />
                  )}
                </button>
              </div>
            )}
          </div>
        ))
      ) : (
        // ⬇️ Khi không có dữ liệu
        <div className="p-6 text-center text-gray-500 italic">
          Không có dữ liệu
        </div>
      )}

      {/* Pagination */}
      {hasData && pagination?.totalPages > 1 && (
        <div className="flex justify-center my-4">
          <Pagination pagination={pagination} />
        </div>
      )}
    </div>
  );
};

export default FormTable;
