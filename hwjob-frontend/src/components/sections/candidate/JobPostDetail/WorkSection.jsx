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

const WorkSection = ({ work, setJobPost }) => {
  //   if (!work) return null;

  const statusConfig = STATUS_WORK_MAP[work.status];

  const candidateActions = statusConfig.actions?.CANDIDATE || [];
  const isInProgress = work.status === "IN_PROGRESS";

  const [submission, setSubmission] = useState(work.submission || "");
  const [confirmAction, setConfirmAction] = useState(null);
  const [openConfirm, setOpenConfirm] = useState(false);
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);
  const isPaid = work.status === "PAID";

  const { updateStatus } = useUpdateWorkStatus();

  useEffect(() => {
    setSubmission(work.submission || "");
  }, [work.submission]);

  if (!statusConfig) return null;
  /* ===== HANDLE CONFIRM ===== */
  const handleConfirm = async () => {
    if (!confirmAction) return;

    await updateStatus({
      jobPostId: work.jobPostId,
      status: confirmAction,
      payload: confirmAction === "SUBMITTED" ? { submission } : undefined,
      onSuccess: (updatedWork) => {
        setJobPost((prev) => ({
          ...prev,
          work: updatedWork,
        }));
        setOpenConfirm(false);
        setConfirmAction(null);
      },
    });
  };

  const submitRating = () => {
    if (!rating) return;

    // Call update status with rating payload
  };

  return (
    <>
      <div className="grid grid-cols-3 gap-3">
        <div className="col-span-2 flex flex-col gap-4 bg-white rounded-2xl p-5 border border-gray-200">
          {/* ===== SUBMISSION ===== */}
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
          <div className="flex flex-col gap-2 ">
            <label className="font-medium text-gray-700">
              Kết quả công việc
            </label>

            {isInProgress ? (
              <textarea
                value={submission}
                onChange={(e) => setSubmission(e.target.value)}
                placeholder="Nhập link, mô tả hoặc kết quả công việc..."
                className="border rounded-lg p-3 min-h-[120px] focus:outline-none focus:ring-2 focus:ring-teal-500"
              />
            ) : (
              <div className="border rounded-lg p-3 bg-gray-50 text-gray-700">
                {work.submission || "Chưa có kết quả được nộp"}
              </div>
            )}
          </div>

          {/* ===== NOTE ===== */}
          {statusConfig.note?.CANDIDATE && (
            <p className="text-sm text-gray-600 italic">
              {statusConfig.note.CANDIDATE}
            </p>
          )}
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
                    className="cursor-pointer transition"
                    color={star <= (hover || rating) ? "#facc15" : "#e5e7eb"}
                    onMouseEnter={() => setHover(star)}
                    onMouseLeave={() => setHover(0)}
                    onClick={() => setRating(star)}
                  />
                ))}
              </div>

              <div className="flex justify-center mt-3">
                <PrimaryButton
                  variant="primary"
                  disabled={!rating}
                  onClick={submitRating}
                >
                  Gửi đánh giá
                </PrimaryButton>
              </div>
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
        {/* ===== DETAIL INFO ===== */}
        <div className="col-span-1 flex flex-col gap-2 bg-white rounded-2xl p-5 border border-gray-200">
          <SecondTitle>Thông tin công việc</SecondTitle>
          <InfoCard
            icon={<GiMoneyStack className="size-8 text-teal-600" />}
            label="Lương thỏa thuận"
            value={`${work.agreedSalary} / ${work.salaryType}`}
          />

          <InfoCard
            icon={
              <HiOutlineCalendarDateRange className="size-8 text-teal-600" />
            }
            label="Thời gian bắt đầu"
            value={formatDate(work.startTime)}
          />

          <InfoCard
            icon={
              <HiOutlineCalendarDateRange className="size-8 text-teal-600" />
            }
            label="Hạn cuối"
            value={formatDate(work.endTime)}
          />
        </div>
      </div>

      {/* ===== CONFIRM DIALOG ===== */}
      <ConfirmDialog
        open={openConfirm}
        onClose={() => {
          setOpenConfirm(false);
          setConfirmAction(null);
        }}
        onConfirm={handleConfirm}
        title="Xác nhận thao tác"
        description={
          confirmAction === "SUBMITTED" ? (
            <div className="space-y-2">
              <p>Bạn có chắc chắn muốn nộp kết quả này?</p>
              <div className="border rounded p-2 text-sm bg-gray-50">
                {submission}
              </div>
            </div>
          ) : (
            "Bạn có chắc chắn muốn thực hiện hành động này?"
          )
        }
        confirmText="Xác nhận"
        cancelText="Hủy"
      />
    </>
  );
};

export default WorkSection;
