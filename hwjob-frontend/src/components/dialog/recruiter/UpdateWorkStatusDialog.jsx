import React, {useEffect, useState} from "react";
import {STATUS_WORK_MAP} from "../../../constants/statusWork";
import PrimaryButton from "../../ui/button/PrimaryButton";
import {ROLES} from "../../../constants/roles";
import {
    FaCheckCircle,
    FaInfoCircle,
    FaStar,
    FaTimes,
    FaUser,
    FaUserEdit,
} from "react-icons/fa";
import {createReview} from "../../../services/reviewService";
import {toast} from "react-toastify";
import classNames from "classnames";
import {MdEmail} from "react-icons/md";

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
            <div className="bg-white p-6 rounded-xl w-[48rem] shadow-lg">
                <div className="mb-4 pb-3 border-b border-gray-200">
                    <div className="flex items-center justify-between">
                        <div className="flex items-center gap-2">
                            <FaUserEdit className="text-orange-500 text-xl"/>
                            <h2 className="text-xl font-semibold">
                                Cập nhật trạng thái ứng viên làm việc
                            </h2>
                        </div>

                        {/* Close icon */}
                        <button
                            onClick={onClose}
                            className="text-gray-400 hover:text-red-500 transition"
                        >
                            <FaTimes size={20}/>
                        </button>
                    </div>
                </div>

                {/* ===== APPLICANT INFO ===== */}
                <div className="mb-5">
                    <div className="flex flex-col  items-center text-center">
                        {/* Avatar */}
                        <img
                            src={staff.imageUrl}
                            alt={staff.fullName}
                            className="w-36 h-36 rounded-full object-cover border-4 border-amber-500 shadow-md"
                        />

                        {/* Status badge */}
                        <span className="mt-3 px-4 py-1 text-sm font-medium rounded-lg bg-amber-100 text-amber-700">
              {statusConfig?.name}
            </span>

                        {/* Name */}
                        <div className="flex items-center gap-2 mt-3">
                            <FaUser className="text-gray-500"/>
                            <p className="text-lg font-semibold text-gray-800">
                                {staff.fullName}
                            </p>
                        </div>

                        {/* Email */}
                        <div className="flex items-center gap-2 mt-1 text-gray-500 text-sm">
                            <MdEmail/>
                            <span>{staff.email}</span>
                        </div>
                        {note && (
                            <div
                                className="flex items-center gap-1 mt-2 bg-blue-50 border border-blue-200 text-blue-700 px-2 py-1 rounded-lg text-sm">
                                <FaInfoCircle className="size-4"/>
                                <p className="leading-relaxed">{note}</p>
                            </div>
                        )}
                    </div>
                </div>

                {/* ===== RATING (PAID ONLY) ===== */}
                {isPaid && (
                    <div className="mt-6 bg-gray-50 border border-gray-200 rounded-xl p-4">
                        {/* Title */}
                        <div className="flex items-center gap-2 mb-3">
                            <FaStar className="text-yellow-400"/>
                            <p className="text-md font-semibold text-gray-700">
                                Đánh giá chất lượng nhân viên
                            </p>
                        </div>

                        {/* Stars */}
                        <div className="flex justify-center gap-2 mb-2">
                            {[1, 2, 3, 4, 5].map((star) => {
                                const active = star <= (hover || rating);
                                return (
                                    <FaStar
                                        key={star}
                                        size={28}
                                        className={classNames("transition-transform duration-150", {
                                            "cursor-pointer hover:scale-110": !hasReviewed,
                                            "cursor-default": hasReviewed,
                                        })}
                                        color={active ? "#facc15" : "#e5e7eb"}
                                        onMouseEnter={() => !hasReviewed && setHover(star)}
                                        onMouseLeave={() => !hasReviewed && setHover(0)}
                                        onClick={() => !hasReviewed && setRating(star)}
                                    />
                                );
                            })}
                        </div>

                        {/* Rating text */}
                        {!hasReviewed && rating > 0 && (
                            <p className="text-center text-sm text-gray-500 mb-2">
                                {rating === 1 && "Rất không hài lòng"}
                                {rating === 2 && "Chưa tốt"}
                                {rating === 3 && "Bình thường"}
                                {rating === 4 && "Tốt"}
                                {rating === 5 && "Rất hài lòng"}
                            </p>
                        )}

                        {/* Action */}
                        {!hasReviewed ? (
                            <div className="flex justify-center ">
                                <PrimaryButton
                                    variant="primary"
                                    disabled={!rating}
                                    onClick={submitRating}
                                >
                                    <FaCheckCircle/>
                                    Gửi đánh giá
                                </PrimaryButton>
                            </div>
                        ) : (
                            <div className="flex items-center justify-center gap-2 text-green-600 text-sm mt-3">
                                <FaCheckCircle/>
                                <span>Đã đánh giá {rating} sao</span>
                            </div>
                        )}
                    </div>
                )}

                {/* ===== ACTION BUTTONS ===== */}
                {!isPaid && (
                    <div className="flex justify-end gap-2 border-t mt-4 pt-3 border-gray-200">
                        {actions.map((action) => (
                            <PrimaryButton
                                key={action.to}
                                variant={action.variant}
                                onClick={() => onUpdateStatus(staff.candidateId, action.to)}
                            >
                                {action.label}
                            </PrimaryButton>
                        ))}
                    </div>
                )}

                {/* ===== FOOTER ===== */}
            </div>
        </div>
    );
};

export default UpdateWorkStatusDialog;
