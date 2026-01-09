import React, { useState } from "react";
import { useList } from "../../../../hooks/useList";
import { getRankedCandidates } from "../../../../services/applicationService";
import { useParams } from "react-router-dom";
import { HiOutlineSearch } from "react-icons/hi";
import ApplicantCard from "../../../ui/cards/ApplicantCard";
import Pagination from "../../../ui/pagination/Pagination";
import UpdateStatusApplicantDialog from "../../../dialog/UpdateStatusApplicantDialog";
import { useUpdateApplicationStatus } from "../../../../hooks/useUpdateApplicationStatus";
import ViewCandidateProfileDialog from "../../../dialog/recruiter/ViewCandidateProfileDialog";

const ApplicantListAIFilterSection = ({ jobPost }) => {
  const { id } = useParams();

  const { updateStatus } = useUpdateApplicationStatus();

  const [openUpdateStatusDialog, setOpenUpdateStatusDialog] = useState(false);
  const [openViewProfileDialog, setOpenViewProfileDialog] = useState(false);

  const [selectedApplicant, setSelectedApplicant] = useState(null);

  const applicants = useList((page, size) =>
    getRankedCandidates(id, page, size)
  );

  const handleOpenUpdateStatus = (applicant) => {
    setSelectedApplicant(applicant);
    setOpenUpdateStatusDialog(true);
  };

  const handleOpenViewProfile = (applicant) => {
    setSelectedApplicant(applicant);
    setOpenViewProfileDialog(true);
  };

  const handleUpdateStatus = async (applicationId, status, payload = {}) => {
    await updateStatus({
      applicationId,
      jobPostId: id,
      status,
      payload,
      setListData: applicants.setData,
      setSelected: setSelectedApplicant,
    });
  };

  return (
    <div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 px-5">
        {applicants.data.map((applicant) => (
          <ApplicantCard
            key={applicant.id}
            applicant={applicant}
            onView={handleOpenViewProfile}
            onUpdate={handleOpenUpdateStatus}
          />
        ))}
      </div>
      <div className="flex justify-center m-3">
        <Pagination
          pagination={{
            page: applicants.page,
            totalPages: applicants.totalPages,
            setPage: applicants.setPage,
          }}
        />
      </div>

      {/* Dialog cập nhật trạng thái */}
      <UpdateStatusApplicantDialog
        open={openUpdateStatusDialog}
        applicant={selectedApplicant}
        jobPost={jobPost}
        onClose={() => setOpenUpdateStatusDialog(false)}
        onUpdateStatus={handleUpdateStatus}
      />

      {/* Dialog xem hồ sơ ứng viên */}
      <ViewCandidateProfileDialog
        open={openViewProfileDialog}
        onClose={() => setOpenViewProfileDialog(false)}
        candidate={selectedApplicant}
      />
    </div>
  );
};

export default ApplicantListAIFilterSection;
