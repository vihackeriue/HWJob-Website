import { toast } from "react-toastify";
import { updateApplicantStatus } from "../services/applicationService";

/**
 * Hook update stautus application
 * @returns {Object} - updateStatus function
 */

export const useUpdateApplicationStatus = () => {
  const updateStatus = async ({
    applicationId,
    jobPostId,
    status,
    setListData, // optional: setData của useList
    setSelected, // optional: setSelectedApplicant / detail
    onSuccess, // optional callback
  }) => {
    try {
      await updateApplicantStatus(applicationId, jobPostId, status);

      // Update list (ApplicantListSection)
      if (setListData) {
        setListData((prev) =>
          prev.map((item) =>
            item.id === applicationId ? { ...item, status } : item
          )
        );
      }

      // Update selected item (dialog / detail)
      if (setSelected) {
        setSelected((prev) => (prev ? { ...prev, status } : prev));
      }

      toast.success("Cập nhật trạng thái thành công");

      if (onSuccess) onSuccess(status);
    } catch (error) {
      toast.error(error.response?.data?.message || "Cập nhật thất bại");
      throw error;
    }
  };

  return { updateStatus };
};
