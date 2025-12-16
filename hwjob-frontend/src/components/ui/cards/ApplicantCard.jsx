import React from "react";
import PrimaryButton from "../button/PrimaryButton";
import { STATUS_APPLICATION_MAP } from "../../../constants/statusApplication";
import { CiEdit } from "react-icons/ci";
import { HiOutlineEye } from "react-icons/hi";

const ApplicantCard = ({ applicant, onView, onUpdate }) => {
  const status = STATUS_APPLICATION_MAP[applicant.status];

  return (
    <div
      key={applicant.id}
      className="group bg-white border border-gray-200 rounded-2xl p-4 flex flex-col items-center gap-4
                       transition-all duration-300 hover:shadow-xl hover:-translate-y-1"
    >
      {/* Avatar */}
      <img
        src={applicant.imageUrl}
        alt={applicant.fullName}
        className="w-28 h-28 object-cover rounded-xl border"
      />

      {/* Info */}
      <div className="text-center">
        <h3 className="text-lg font-semibold text-gray-800">
          {applicant.fullName}
        </h3>

        <span
          className={`inline-block mt-2 px-3 py-1 text-sm font-medium rounded-full ${status?.className}`}
        >
          {status?.name}
        </span>
      </div>

      {/* Actions */}
      <div className="w-full flex flex-col gap-2 mt-auto">
        {/* View profile */}
        <PrimaryButton variant="primary" onClick={() => onView(applicant)}>
          <HiOutlineEye size={18} />
          Hồ sơ
        </PrimaryButton>

        {/* Update status */}
        <PrimaryButton variant="outline" onClick={() => onUpdate(applicant)}>
          <CiEdit size={18} />
          Trạng thái
        </PrimaryButton>
      </div>
    </div>
  );
};

export default ApplicantCard;
