import React, { useEffect, useState } from "react";
import PrimaryButton from "../ui/button/PrimaryButton";
import { STATUS_APPLICATION_MAP } from "../../constants/statusApplication";
import FormInput from "../ui/form/FormInput";
import FormSelect from "../ui/form/FormSelect";
import { SALARY_TYPE } from "../../config/constants";

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
      <div className="bg-white p-6 rounded-xl w-[28rem] shadow-lg">
        <h2 className="text-xl font-semibold mb-4">Hồ sơ ứng viên</h2>

        {/* Applicant info */}
        <div className="flex gap-4 mb-4">
          <img
            src={applicant.imageUrl}
            alt={applicant.fullName}
            className="w-20 h-20 rounded-lg object-cover border"
          />
          <div>
            <p className="font-semibold">{applicant.fullName}</p>
            <p className="text-sm text-gray-500">{applicant.email}</p>
            <p className="text-sm text-gray-500">
              Trạng thái: <b>{statusConfig?.name}</b>
            </p>
          </div>
        </div>

        {/* Actions */}

        {/* Actions khác (REJECTED...) */}
        {!showAssignForm && (
          <div className="flex flex-col gap-2">
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

            {note && (
              <div className="text-center text-sm text-gray-600 bg-gray-100 py-2 rounded-lg">
                {note}
              </div>
            )}
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

            <PrimaryButton onClick={handleConfirmAssign}>
              Xác nhận giao việc
            </PrimaryButton>
          </div>
        )}

        {/* Footer */}
        <div className="flex justify-end mt-4">
          <PrimaryButton variant="cancel" onClick={onClose}>
            Đóng
          </PrimaryButton>
        </div>
      </div>
    </div>
  );
};

export default UpdateStatusApplicantDialog;
