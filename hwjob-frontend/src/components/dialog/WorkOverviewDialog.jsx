import { useDetail } from "../../hooks/useDetail";
import { getWorkOverview } from "../../services/workService";
import PrimaryButton from "../ui/button/PrimaryButton";
import {
  FaTimes,
  FaBriefcase,
  FaBuilding,
  FaTimesCircle,
  FaCheckCircle,
} from "react-icons/fa";
import { MdAttachMoney, MdAccessTime } from "react-icons/md";
import Loading from "../ui/Loading";
import InfoCard from "../ui/cards/InfoCard";

const WorkOverviewDialog = ({ open, onClose, jobPostId, onDecide }) => {
  const {
    data: overview,
    loading,
    error,
  } = useDetail(getWorkOverview, jobPostId, open);

  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl w-[48rem]  shadow-lg">
        {/* Header */}
        <div className="flex items-center justify-between px-6 py-3 border-b border-gray-200">
          <div className="flex items-center gap-2">
            <FaBriefcase className="text-orange-500 text-xl" />
            <h2 className="text-xl font-semibold">Xác nhận nhận công việc</h2>
          </div>

          <button
            onClick={onClose}
            className="text-gray-400 hover:text-red-500 transition"
          >
            <FaTimes size={20} />
          </button>
        </div>

        {/* Content */}
        <div className="px-6 py-4">
          {loading && <Loading />}

          {error && <p className="text-red-500 text-sm">{error}</p>}

          {overview && (
            <div className="p-3 divide-y divide-gray-200/60 text-md rounded-xl ">
              {/* Job title */}
              <div className="flex items-start gap-3 py-3">
                <div className="p-2 rounded-lg bg-orange-100 text-orange-600">
                  <FaBriefcase />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Công việc</p>
                  <p className="font-semibold text-gray-800">
                    {overview.jobTitle}
                  </p>
                </div>
              </div>

              {/* Recruiter */}
              <div className="flex items-start gap-3 py-3">
                <div className="p-2 rounded-lg bg-blue-100 text-blue-600">
                  <FaBuilding />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Nhà tuyển dụng</p>
                  <p className="font-semibold text-gray-800">
                    {overview.recruiterName}
                  </p>
                </div>
              </div>

              {/* Salary */}
              <div className="flex items-start gap-3 py-3">
                <div className="p-2 rounded-lg bg-green-100 text-green-600">
                  <MdAttachMoney />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Lương</p>
                  <p className="font-semibold text-green-700">
                    {overview.agreedSalary?.toLocaleString()} (
                    {overview.salaryType})
                  </p>
                </div>
              </div>

              {/* Start time */}
              <div className="flex items-start gap-3 py-3">
                <div className="p-2 rounded-lg bg-purple-100 text-purple-600">
                  <MdAccessTime />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Bắt đầu</p>
                  <p className="font-semibold text-gray-800">
                    {new Date(overview.startTime).toLocaleString()}
                  </p>
                </div>
              </div>

              {/* End time */}
              <div className="flex items-start gap-3 py-3">
                <div className="p-2 rounded-lg bg-pink-100 text-pink-600">
                  <MdAccessTime />
                </div>
                <div>
                  <p className="text-sm text-gray-500">Kết thúc</p>
                  <p className="font-semibold text-gray-800">
                    {new Date(overview.endTime).toLocaleString()}
                  </p>
                </div>
              </div>
            </div>
          )}
        </div>

        {/* Footer */}
        <div className="flex justify-end gap-3 px-6 py-3 border-t border-gray-200">
          <PrimaryButton variant="danger" onClick={() => onDecide("CANCELLED")}>
            <FaTimesCircle />
            Từ chối
          </PrimaryButton>
          <PrimaryButton variant="success" onClick={() => onDecide("ACCEPTED")}>
            <FaCheckCircle />
            Nhận việc
          </PrimaryButton>
        </div>
      </div>
    </div>
  );
};

export default WorkOverviewDialog;
