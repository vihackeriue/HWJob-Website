import React, { useEffect, useState } from "react";
import classNames from "classnames";
import { STATUS_WORK_MAP } from "../../../../constants/statusWork";
import InfoCard from "../../../ui/cards/InfoCard";
import { GiMoneyStack } from "react-icons/gi";
import { HiOutlineCalendarDateRange } from "react-icons/hi2";
import { formatDate } from "../../../../utils/date";
import PrimaryButton from "../../../ui/button/PrimaryButton";
import { useUpdateWorkStatus } from "../../../../hooks/useUpdateWorkStatus";
import ConfirmDialog from "../../../dialog/common/ConfirmDialog";
import PrimaryTitle from "../../../ui/title/PrimaryTitle";
import SecondTitle from "../../../ui/title/SecondTitle";
import { FaStar } from "react-icons/fa";
import { ROLES } from "../../../../constants/roles";
import { createReview } from "../../../../services/reviewService";
import { toast } from "react-toastify";

const WorkSection = ({ work, setJobPost }) => {
  const statusConfig = STATUS_WORK_MAP[work.status];
  const candidateActions = statusConfig.actions?.[ROLES.CANDIDATE] || [];
  const isInProgress = work.status === "IN_PROGRESS";
  const isPaid = work.status === "PAID";

  const [submission, setSubmission] = useState(work.submission || "");
  const [confirmAction, setConfirmAction] = useState(null);
  const [openConfirm, setOpenConfirm] = useState(false);

  // ⭐ rating state
  const [rating, setRating] = useState(work.myReviewRating || 0);
  const [hover, setHover] = useState(0);
  const hasReviewed = work.myReviewRating != null;
  const { updateStatus } = useUpdateWorkStatus();

  useEffect(() => {
    setSubmission(work.submission || "");
    setRating(work.myReviewRating || 0);
  }, [work]);

  if (!statusConfig) return null;

  /* ===== HANDLE CONFIRM ===== */
  const handleConfirm = async () => {
    if (!confirmAction) return;

    await updateStatus({
      jobPostId: work.jobPostId,
      status: confirmAction,
      payload: confirmAction === "SUBMITTED" ? { submission } : undefined,
      onSuccess: (status) => {
        setJobPost((prev) => ({
          ...prev,
          work: {
            ...prev.work,
            status,
            ...(confirmAction === "SUBMITTED" && { submission }),
          },
        }));
        setOpenConfirm(false);
        setConfirmAction(null);
      },
    });
  };

  /* ===== SUBMIT RATING ===== */
  const submitRating = async () => {
    if (!rating || hasReviewed) return;

    const payload = {
      workId: work.workId,
      revieweeId: work.recruiterId,
      rating,
    };

    try {
      await createReview(payload);

      toast.success("Đã gửi đánh giá");

      setJobPost((prev) => ({
        ...prev,
        work: {
          ...prev.work,
          myReviewRating: rating,
        },
      }));
    } catch (err) {
      toast.error(err?.response?.data?.message || "Gửi đánh giá thất bại");
    }
  };

  return (
    <>
      <div className="grid grid-cols-3 gap-3">
        {/* ===== LEFT ===== */}
        <div className="col-span-2 flex flex-col gap-4 bg-white rounded-2xl p-5 border border-gray-200">
          <div className="flex justify-between items-center">
            <PrimaryTitle>Công việc của bạn</PrimaryTitle>
            <span
              className={classNames(
                "px-4 py-1 rounded-full text-md font-medium",
                statusConfig.className
              )}
            >
              {statusConfig.name}
            </span>
          </div>

          {/* ===== SUBMISSION ===== */}
          <div className="flex flex-col gap-2">
            <label className="font-medium text-gray-700">
              Kết quả công việc
            </label>

            {isInProgress ? (
              <textarea
                value={submission}
                onChange={(e) => setSubmission(e.target.value)}
                className="border rounded-lg p-3 min-h-[120px]"
              />
            ) : (
              <div className="border rounded-lg p-3 bg-gray-50">
                {work.submission || "Chưa có kết quả"}
              </div>
            )}
          </div>

          {/* ===== RATING ===== */}
          {isPaid && (
            <div className="mt-4">
              <p className="text-sm font-medium mb-2">
                Đánh giá chất lượng công việc
              </p>

              <div className="flex justify-center gap-2">
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
                  ✔ Bạn đã đánh giá {rating} sao
                </p>
              )}
            </div>
          )}

          {/* ===== ACTION BUTTONS ===== */}
          {candidateActions.length > 0 && (
            <div className="flex justify-end gap-3 pt-2">
              {candidateActions.map((action) => (
                <PrimaryButton
                  key={action.to}
                  variant={action.variant}
                  disabled={
                    action.to === "SUBMITTED" &&
                    isInProgress &&
                    !submission.trim()
                  }
                  onClick={() => {
                    setConfirmAction(action.to);
                    setOpenConfirm(true);
                  }}
                >
                  {action.label}
                </PrimaryButton>
              ))}
            </div>
          )}
        </div>

        {/* ===== RIGHT ===== */}
        <div className="col-span-1 bg-white rounded-2xl p-5 border border-gray-200">
          <SecondTitle>Thông tin công việc</SecondTitle>

          <InfoCard
            icon={<GiMoneyStack className="size-8 text-teal-600" />}
            label="Lương"
            value={`${work.agreedSalary} / ${work.salaryType}`}
          />

          <InfoCard
            icon={
              <HiOutlineCalendarDateRange className="size-8 text-teal-600" />
            }
            label="Bắt đầu"
            value={formatDate(work.startTime)}
          />

          <InfoCard
            icon={
              <HiOutlineCalendarDateRange className="size-8 text-teal-600" />
            }
            label="Kết thúc"
            value={formatDate(work.endTime)}
          />
        </div>
      </div>

      {/* ===== CONFIRM ===== */}
      <ConfirmDialog
        open={openConfirm}
        onClose={() => setOpenConfirm(false)}
        onConfirm={handleConfirm}
        title="Xác nhận"
        description="Bạn có chắc chắn?"
        confirmText="Xác nhận"
        cancelText="Hủy"
      />
    </>
  );
};

export default WorkSection;
