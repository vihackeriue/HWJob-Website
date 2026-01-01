import React, { useEffect, useState } from "react";
import PrimaryButton from "../ui/button/PrimaryButton";
import { STATUS_APPLICATION_MAP } from "../../constants/statusApplication";
import FormInput from "../ui/form/FormInput";
import FormSelect from "../ui/form/FormSelect";
import { SALARY_TYPE } from "../../config/constants";
import { FaInfoCircle, FaTimes, FaUser, FaUserEdit } from "react-icons/fa";
import { MdEmail } from "react-icons/md";

const UpdateStatusApplicantDialog = ({
  open,
  onClose,
  applicant,
  jobPost,
  role = "RECRUITER", // mặc định recruiter
  onUpdateStatus,
}) => {
  const [showAssignForm, setShowAssignForm] = useState(false);
  const [assignForm, setAssignForm] = useState({
    agreedSalary: "",
    salaryType: "",
    startTime: "",
    endTime: "",
  });

  useEffect(() => {
    if (open && applicant?.status === "APPROVED" && jobPost) {
      setShowAssignForm(true);
      setAssignForm({
        agreedSalary: jobPost.salary || "",
        salaryType: jobPost.salaryType || "FIXED",
        startTime: "",
        endTime: "",
      });
    } else {
      setShowAssignForm(false);
    }
  }, [open, applicant?.status, jobPost]);

  const [errors, setErrors] = useState({});
  if (!open || !applicant) return null;

  const statusConfig = STATUS_APPLICATION_MAP[applicant.status];
  const actions = statusConfig?.actions?.[role] || [];
  const note = statusConfig?.note?.[role];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAssignForm((prev) => ({ ...prev, [name]: value }));
  };

  // const handleActionClick = (action) => {
  //   if (action.to === "ASSIGNED") {
  //     setShowAssignForm(true);
  //   } else {
  //     onUpdateStatus(applicant.id, action.to);
  //   }
  // };
  const validate = () => {
    const newErrors = {};
    const now = new Date();

    // 1. Kiểm tra mức lương
    if (!assignForm.agreedSalary || assignForm.agreedSalary <= 0) {
      newErrors.agreedSalary = "Mức lương thỏa thuận phải lớn hơn 0";
    }

    if (!assignForm.salaryType) {
      newErrors.salaryType = "Vui lòng chọn loại lương";
    }

    // 2. Kiểm tra thời gian bắt đầu
    if (!assignForm.startTime) {
      newErrors.startTime = "Vui lòng chọn thời gian bắt đầu";
    } else {
      const start = new Date(assignForm.startTime);
      if (start < now) {
        newErrors.startTime = "Thời gian bắt đầu không được ở quá khứ";
      }
    }

    // 3. Kiểm tra thời gian kết thúc
    if (!assignForm.endTime) {
      newErrors.endTime = "Vui lòng chọn thời gian kết thúc";
    } else {
      const end = new Date(assignForm.endTime);
      const start = new Date(assignForm.startTime);

      // Kiểm tra kết thúc không được ở quá khứ
      if (end < now) {
        newErrors.endTime = "Thời gian kết thúc không được ở quá khứ";
      }
      // Kiểm tra logic: Kết thúc phải sau bắt đầu
      else if (assignForm.startTime && start >= end) {
        newErrors.endTime = "Thời gian kết thúc phải sau thời gian bắt đầu";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleConfirmAssign = async () => {
    if (!validate()) {
      return;
    }

    await onUpdateStatus(applicant.id, "ASSIGNED", {
      agreedSalary: Number(assignForm.agreedSalary),
      salaryType: assignForm.salaryType,
      startTime: assignForm.startTime,
      endTime: assignForm.endTime,
    });

    // onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-xl w-[48rem] shadow-lg">
        <div className="mb-4 pb-3 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <FaUserEdit className="text-orange-500 text-xl" />
              <h2 className="text-xl font-semibold">
                Cập nhật trạng thái ứng viên
              </h2>
            </div>

            {/* Close icon */}
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-red-500 transition"
            >
              <FaTimes size={20} />
            </button>
          </div>
        </div>

        {/* Applicant info */}
        <div className="mb-5">
          <div className="flex flex-col  items-center text-center">
            {/* Avatar */}
            <img
              src={applicant.imageUrl}
              alt={applicant.fullName}
              className="w-36 h-36 rounded-full object-cover border-4 border-amber-500 shadow-md"
            />

            {/* Status badge */}
            <span className="mt-3 px-4 py-1 text-sm font-medium rounded-lg bg-amber-100 text-amber-700">
              {statusConfig?.name}
            </span>

            {/* Name */}
            <div className="flex items-center gap-2 mt-3">
              <FaUser className="text-gray-500" />
              <p className="text-lg font-semibold text-gray-800">
                {applicant.fullName}
              </p>
            </div>

            {/* Email */}
            <div className="flex items-center gap-2 mt-1 text-gray-500 text-sm">
              <MdEmail />
              <span>{applicant.email}</span>
            </div>
            {note && (
              <div className="flex items-center gap-1 mt-2 bg-blue-50 border border-blue-200 text-blue-700 px-2 py-1 rounded-lg text-sm">
                <FaInfoCircle className="size-4" />
                <p className="leading-relaxed">{note}</p>
              </div>
            )}
          </div>
        </div>

        {/* Actions */}

        {/* Actions khác (REJECTED...) */}
        {!showAssignForm && (
          <div className="flex justify-end gap-2 border-t mt-4 pt-3 border-gray-200">
            {actions
              .filter((a) => a.to !== "ASSIGNED")
              .map((action) => (
                <PrimaryButton
                  key={action.to}
                  variant={action.variant}
                  onClick={() => onUpdateStatus(applicant.id, action.to)}
                >
                  {action.label}
                </PrimaryButton>
              ))}
          </div>
        )}

        {showAssignForm && (
          <div className="mt-4 space-y-3">
            <FormInput
              label="Mức lương thỏa thuận"
              name="agreedSalary"
              type="number"
              value={assignForm.agreedSalary}
              onChange={handleChange}
              error={errors.agreedSalary}
            />
            <FormSelect
              label="Loại lương"
              name="salaryType"
              selected={SALARY_TYPE.find(
                (j) => j.code === assignForm.salaryType
              )}
              onChange={handleChange}
              options={SALARY_TYPE}
              placeholder="Chọn loại lương"
              error={errors.salaryType}
            />
            <div className="grid grid-cols-2 gap-2 ">
              <FormInput
                label="Bắt đầu"
                name="startTime"
                type="datetime-local"
                value={assignForm.startTime}
                onChange={handleChange}
                error={errors.startTime}
              />
              <FormInput
                label="Kết thúc"
                name="endTime"
                type="datetime-local"
                value={assignForm.endTime}
                onChange={handleChange}
                error={errors.endTime}
              />
            </div>

            <div className="flex justify-end border-t mt-4 pt-3 border-gray-200">
              <PrimaryButton onClick={handleConfirmAssign}>
                Xác nhận giao việc
              </PrimaryButton>
            </div>
          </div>
        )}

        {/* Footer */}
      </div>
    </div>
  );
};

export default UpdateStatusApplicantDialog;
