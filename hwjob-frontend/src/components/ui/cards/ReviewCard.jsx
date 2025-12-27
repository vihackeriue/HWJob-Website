import React from "react";
import { FaStar } from "react-icons/fa6";

const ReviewCard = ({ review }) => {
  return (
    <div
      key={review.id}
      className="relative flex items-center gap-4 p-4 bg-lightGrayishBlue rounded-xl shadow-sm border border-gray-200"
    >
      {/* Ngày đánh giá – góc phải */}
      <span className="absolute top-3 right-4 text-sm text-gray-500">
        {new Date(review.createdAt).toLocaleDateString("vi-VN")}
      </span>

      {/* Avatar */}
      <img
        src={review.reviewer.imageUrl}
        alt={review.reviewer.fullName}
        className="w-12 h-12 rounded-full object-cover border border-brightOrange"
      />

      {/* Info */}
      <div className="flex-1">
        <p className="font-semibold text-gray-800">
          {review.reviewer.fullName}
        </p>

        {/* Rating */}
        <div className="flex items-center gap-1 mt-1">
          {[1, 2, 3, 4, 5].map((star) => (
            <FaStar
              key={star}
              className={
                star <= review.rating ? "text-yellow-400" : "text-gray-300"
              }
            />
          ))}
          <span className="ml-2 text-sm text-gray-500">
            {review.rating.toFixed(1)}
          </span>
        </div>
      </div>
    </div>
  );
};

export default ReviewCard;
