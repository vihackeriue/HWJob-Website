import React from "react";
import PrimaryButton from "../button/PrimaryButton";
import { STATUS_WORK_MAP } from "../../../constants/statusWork";
import { HiOutlineEye } from "react-icons/hi";
import { CiEdit } from "react-icons/ci";

const StaffCard = ({ staff, onUpdateStatus }) => {
  const status = STATUS_WORK_MAP[staff.status];

  if (!status) return null;

  return (
    <div
      className="group bg-white border border-gray-200 rounded-2xl p-4 flex flex-col items-center gap-2
                 transition-all duration-300 hover:shadow-xl hover:-translate-y-1"
    >
      {/* Avatar */}
      <img
        src={staff.imageUrl}
        alt={staff.fullName}
        className="w-28 h-28 object-cover rounded-xl border"
      />

      {/* Info */}
      <div className="text-center">
        <h3 className="text-lg font-semibold text-gray-800">
          {staff.fullName}
        </h3>

        <span
          className={`inline-block mt-2 px-3 py-1 text-sm font-medium rounded-full ${status.className}`}
        >
          {status.name}
        </span>
      </div>
      <div>
        <p>
          {staff.agreedSalary}/{staff.salaryType}
        </p>
      </div>
      {/* Note (optional) */}
      {status.note?.RECRUITER && (
        <p className="text-sm text-gray-600 italic text-center">
          {status.note.RECRUITER}
        </p>
      )}

      {/* Actions */}
      <div className="w-full flex flex-col gap-2 mt-auto">
        {/* View work detail */}

        <PrimaryButton variant="outline" onClick={() => onUpdateStatus(staff)}>
          <CiEdit size={18} />
          Trạng thái
        </PrimaryButton>
      </div>
    </div>
  );
};

export default StaffCard;
