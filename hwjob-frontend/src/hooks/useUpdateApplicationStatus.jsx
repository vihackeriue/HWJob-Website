import { toast } from "react-toastify";
import {
  candidateUpdateApplicantStatus,
  recruiterUpdateApplicantStatus,
} from "../services/applicationService";
import useAuth from "./useAuth.jsx";
import { ROLES } from "../constants/roles.jsx";
import { hasRole } from "../utils/permission.jsx";

/**
 * Hook update application status (Recruiter & Candidate)
 * @returns {Object} - updateStatus function
 */

export const useUpdateApplicationStatus = () => {
  const { auth } = useAuth();
  const updateStatus = async ({
    applicationId,
    jobPostId,
    status,
    payload,
    setListData, // optional: setData của useList
    setSelected, // optional: setSelectedApplicant / detail
    onSuccess, // optional callback
  }) => {
    try {
      // ===== Call API theo role =====
      if (hasRole(auth, ROLES.RECRUITER)) {
        await recruiterUpdateApplicantStatus(applicationId, jobPostId, {
          status,
          ...(payload ?? {}),
        });
      } else if (hasRole(auth, ROLES.CANDIDATE)) {
        await candidateUpdateApplicantStatus(jobPostId, status);
      } else {
        throw new Error("Không có quyền cập nhật trạng thái");
      }

      // Update list (ApplicantListSection)
      if (setListData) {
        setListData((prev) =>
          prev.map((item) =>
            item.id === applicationId || item.jobPostId === jobPostId
              ? { ...item, status }
              : item
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
