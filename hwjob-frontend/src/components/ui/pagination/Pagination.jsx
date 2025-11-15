import React from "react";
import { SlArrowLeft, SlArrowRight } from "react-icons/sl";

const Pagination = ({ pagination }) => {
  if (!pagination || pagination.totalPages <= 1) return null;

  let start = 1;
  let end = pagination.totalPages;

  if (pagination.totalPages > 4) {
    if (pagination.page <= 2) {
      // gần đầu
      start = 1;
      end = 4;
    } else if (pagination.page >= pagination.totalPages - 1) {
      // gần cuối
      start = pagination.totalPages - 3;
      end = pagination.totalPages;
    } else {
      // ở giữa
      start = pagination.page - 1;
      end = pagination.page + 2;
    }
  }
  let numbers = [];
  for (let i = start; i <= end; i++) {
    numbers.push(i);
  }
  return (
    <div className="flex flex-row items-center gap-4">
      <button
        onClick={() => pagination.setPage(pagination.page - 1)}
        disabled={pagination.page === 1}
        className="bg-dark-100 hover:bg-dark-50  p-2 rounded-full text-md"
      >
        <SlArrowLeft className="" />
      </button>
      {numbers.map((num) => (
        <button
          key={num}
          onClick={() => pagination.setPage(num)}
          className={`px-3 py-1 rounded-full text-md ${
            pagination.page === num
              ? "bg-brightOrange text-white"
              : "bg-dark-100 hover:bg-dark-50"
          }`}
        >
          {num}
        </button>
      ))}
      {/* <span>
        Trang {pagination.page} / {pagination.totalPages}
      </span> */}
      <button
        onClick={() => pagination.setPage(pagination.page + 1)}
        disabled={pagination.page >= pagination.totalPages}
        className="bg-dark-100 hover:bg-dark-50  p-2 rounded-full text-md"
      >
        <SlArrowRight />
      </button>
    </div>
  );
};

export default Pagination;
