import React from "react";
import { useList } from "../../../hooks/useList";
import { getMyReviews } from "../../../services/reviewService";
import Pagination from "../../ui/pagination/Pagination";
import { FaStar } from "react-icons/fa6";
import ReviewCard from "../../ui/cards/ReviewCard";
import SecondTitle from "../../ui/title/SecondTitle";

const RatingSection = () => {
  const myReviews = useList(getMyReviews);
  return (
    <div className="bg-white rounded-2xl p-5 min-h-150 space-y-2">
      <SecondTitle>Đánh giá của tôi</SecondTitle>
      {myReviews.data.length === 0 ? (
        <div className="text-center text-gray-500 py-10">
          Không có đánh giá nào
        </div>
      ) : (
        <>
          <div className="space-y-3">
            {myReviews.data.map((review) => (
              <ReviewCard review={review} />
            ))}
          </div>
          <div className="flex justify-center m-3">
            <Pagination
              pagination={{
                page: myReviews.page,
                totalPages: myReviews.totalPages,
                setPage: myReviews.setPage,
              }}
            />
          </div>
        </>
      )}
    </div>
  );
};

export default RatingSection;
