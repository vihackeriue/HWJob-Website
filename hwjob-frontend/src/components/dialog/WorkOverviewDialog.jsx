import { useDetail } from "../../hooks/useDetail";
import { getWorkOverview } from "../../services/workService";
import PrimaryButton from "../ui/button/PrimaryButton";

const WorkOverviewDialog = ({
  open,
  onClose,
  jobPostId,
  onDecide, // (status) => void
}) => {
  console.log("jobPostId", jobPostId);
  const {
    data: overview,
    loading,
    error,
  } = useDetail(getWorkOverview, jobPostId, open);

  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-xl w-full max-w-md shadow-lg">
        <h2 className="text-xl font-semibold mb-3">Xác nhận nhận công việc</h2>

        {loading && <p className="text-gray-500">Đang tải thông tin...</p>}
        {error && <p className="text-red-500 text-sm">{error}</p>}

        {overview && (
          <div className="space-y-3 text-sm bg-gray-50 p-4 rounded-lg border">
            <div>
              <b>Công việc:</b> {overview.jobTitle}
            </div>
            <div>
              <b>Nhà tuyển dụng:</b> {overview.recruiterName}
            </div>
            <div>
              <b>Lương:</b> {overview.agreedSalary?.toLocaleString()} (
              {overview.salaryType})
            </div>
            <div>
              <b>Bắt đầu:</b> {new Date(overview.startTime).toLocaleString()}
            </div>
            <div>
              <b>Kết thúc:</b> {new Date(overview.endTime).toLocaleString()}
            </div>
          </div>
        )}

        <div className="flex justify-end gap-3 mt-6">
          <PrimaryButton variant="cancel" onClick={onClose}>
            Đóng
          </PrimaryButton>

          <PrimaryButton variant="danger" onClick={() => onDecide("CANCELLED")}>
            Từ chối
          </PrimaryButton>

          <PrimaryButton variant="success" onClick={() => onDecide("ACCEPTED")}>
            Nhận việc
          </PrimaryButton>
        </div>
      </div>
    </div>
  );
};
export default WorkOverviewDialog;
