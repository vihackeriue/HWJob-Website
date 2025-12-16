import React from "react";
import PrimaryButton from "../ui/button/PrimaryButton";
import { STATUS_APPLICATION_MAP } from "../../constants/statusApplication";

const UpdateStatusApplicantDialog = ({
  open,
  onClose,
  applicant,
  role = "RECRUITER", // mặc định recruiter
  onUpdateStatus,
}) => {
  if (!open || !applicant) return null;

  const statusConfig = STATUS_APPLICATION_MAP[applicant.status];
  const actions = statusConfig?.actions?.[role] || [];
  const note = statusConfig?.note?.[role];

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
        <div className="flex flex-col gap-2">
          {actions.map((action) => (
            <PrimaryButton
              key={action.to}
              variant={action.variant}
              onClick={() => onUpdateStatus(applicant.id, action.to)}
            >
              {action.label}
            </PrimaryButton>
          ))}

          {note && (
            <div className="w-full text-center text-sm text-gray-600 bg-gray-100 py-2 rounded-lg">
              {note}
            </div>
          )}
        </div>

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
