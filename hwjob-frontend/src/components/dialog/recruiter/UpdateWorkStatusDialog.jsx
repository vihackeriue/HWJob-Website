import React, { useEffect, useState } from "react";
import { STATUS_WORK_MAP } from "../../../constants/statusWork";
import PrimaryButton from "../../ui/button/PrimaryButton";
import { ROLES } from "../../../constants/roles";
import { FaStar } from "react-icons/fa";
import { createReview } from "../../../services/reviewService";
import { toast } from "react-toastify";
import classNames from "classnames";

const UpdateWorkStatusDialog = ({
  open,
  onClose,
  staff,
  role = ROLES.RECRUITER,
  onUpdateStatus,
}) => {
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);
  const [hasReviewed, setHasReviewed] = useState(false);

  useEffect(() => {
    if (!staff) return;

    setRating(staff.myReviewRating || 0);
    setHasReviewed(staff.myReviewRating != null);
  }, [staff]);

  /* ===== SAU ĐÓ MỚI ĐƯỢC RETURN ===== */
  if (!open || !staff) return null;

  const statusConfig = STATUS_WORK_MAP[staff.status];
  const actions = statusConfig?.actions?.[role] || [];
  const note = statusConfig?.note?.[role];
  const isPaid = staff.status === "PAID";

  /* ===== SUBMIT RATING ===== */
  const submitRating = async () => {
    if (!rating || hasReviewed) return;

    const payload = {
      workId: staff.workId,
      revieweeId: staff.candidateId,
      rating,
    };

    try {
      await createReview(payload);

      toast.success("Đã gửi đánh giá");

      // ✅ update local state → UI re-render ngay
      setHasReviewed(true);
    } catch (err) {
      toast.error(err?.response?.data?.message || "Gửi đánh giá thất bại");
    }
  };

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-xl w-[28rem] shadow-lg">
        <h2 className="text-xl font-semibold mb-4">Hồ sơ ứng viên</h2>

        {/* ===== APPLICANT INFO ===== */}
        <div className="flex gap-4 mb-4">
          <img
            src={staff.imageUrl}
            alt={staff.fullName}
            className="w-20 h-20 rounded-lg object-cover border"
          />
          <div>
            <p className="font-semibold">{staff.fullName}</p>
            <p className="text-sm text-gray-500">
              {staff.agreedSalary} / {staff.salaryType}
            </p>
            <p className="text-sm text-gray-500">
              {staff.startTime} / {staff.endTime}
            </p>
            <p className="text-sm text-gray-500">
              Trạng thái: <b>{statusConfig?.name}</b>
            </p>
          </div>
        </div>

        {/* ===== RATING (PAID ONLY) ===== */}
        {isPaid && (
          <div className="mb-4">
            <p className="text-sm font-medium mb-2">
              Đánh giá chất lượng công việc
            </p>

            <div className="flex gap-2 justify-center">
              {[1, 2, 3, 4, 5].map((star) => (
                <FaStar
                  key={star}
                  size={28}
                  className={classNames("transition", {
                    "cursor-pointer": !hasReviewed,
                    "cursor-default": hasReviewed,
                  })}
                  color={star <= (hover || rating) ? "#facc15" : "#e5e7eb"}
                  onMouseEnter={() => !hasReviewed && setHover(star)}
                  onMouseLeave={() => !hasReviewed && setHover(0)}
                  onClick={() => !hasReviewed && setRating(star)}
                />
              ))}
            </div>

            {!hasReviewed ? (
              <div className="flex justify-center mt-3">
                <PrimaryButton
                  variant="primary"
                  disabled={!rating}
                  onClick={submitRating}
                >
                  Gửi đánh giá
                </PrimaryButton>
              </div>
            ) : (
              <p className="text-center text-sm text-green-600 mt-3">
                ✔ Đã đánh giá {rating} sao
              </p>
            )}
          </div>
        )}

        {/* ===== ACTION BUTTONS ===== */}
        {!isPaid && (
          <div className="flex flex-col gap-2">
            {actions.map((action) => (
              <PrimaryButton
                key={action.to}
                variant={action.variant}
                onClick={() => onUpdateStatus(staff.candidateId, action.to)}
              >
                {action.label}
              </PrimaryButton>
            ))}

            {note && (
              <div className="text-center text-sm text-gray-600 bg-gray-100 py-2 rounded-lg">
                {note}
              </div>
            )}
          </div>
        )}

        {/* ===== FOOTER ===== */}
        <div className="flex justify-end mt-4">
          <PrimaryButton variant="cancel" onClick={onClose}>
            Đóng
          </PrimaryButton>
        </div>
      </div>
    </div>
  );
};

export default UpdateWorkStatusDialog;
