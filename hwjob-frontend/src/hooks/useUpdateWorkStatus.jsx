import { toast } from "react-toastify";

import {
  candidateUpdateWorkStatus,
  recruiterUpdateWorkStatus,
} from "../services/workService";
import { ROLES } from "../constants/roles";
import { hasRole } from "../utils/permission";
import useAuth from "./useAuth";

/**
 * Hook update work status (Recruiter & Candidate)
 */
export const useUpdateWorkStatus = () => {
  const { auth } = useAuth();

  const updateStatus = async ({
    applicationId,
    jobPostId,
    status,
    payload,
    setListData, // optional: danh sách work
    setSelected, // optional: work detail
    onSuccess,
  }) => {
    try {
      // ===== Call API theo role =====
      if (hasRole(auth, ROLES.CANDIDATE)) {
        await candidateUpdateWorkStatus(jobPostId, {
          status,
          ...(payload ?? {}),
        });
      } else if (hasRole(auth, ROLES.RECRUITER)) {
        await recruiterUpdateWorkStatus(jobPostId, applicationId, {
          status,
          ...(payload ?? {}),
        });
      } else {
        throw new Error("Không có quyền cập nhật trạng thái công việc");
      }

      // ===== Update list (optional) =====
      if (typeof setListData === "function") {
        setListData((prev) =>
          prev.map((item) =>
            item.id === applicationId ? { ...item, status } : item
          )
        );
      }

      if (typeof setSelected === "function") {
        setSelected((prev) => (prev ? { ...prev, status } : prev));
      }

      toast.success("Cập nhật công việc thành công");

      onSuccess?.(status);
      return status;
    } catch (error) {
      toast.error(
        error.response?.data?.message || "Cập nhật công việc thất bại"
      );
      throw error;
    }
  };

  return { updateStatus };
};
