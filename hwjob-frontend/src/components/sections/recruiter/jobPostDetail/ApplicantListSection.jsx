import React, { useState } from "react";
import { useList } from "../../../../hooks/useList";
import { getCandidateApplications } from "../../../../services/applicationService";
import { useParams } from "react-router-dom";
import { HiOutlineSearch } from "react-icons/hi";
import ApplicantCard from "../../../ui/cards/ApplicantCard";
import Pagination from "../../../ui/pagination/Pagination";
import UpdateStatusApplicantDialog from "../../../dialog/UpdateStatusApplicantDialog";
import { useUpdateApplicationStatus } from "../../../../hooks/useUpdateApplicationStatus";

const ApplicantListSection = () => {
  const { id } = useParams();
  const { updateStatus } = useUpdateApplicationStatus();
  const [openViewProfileApplicantDialog, setOpenViewProfileApplicantDialog] =
    useState(false);
  const [selectedApplicant, setSelectedApplicant] = useState(null);
  const applicants = useList((page, size) =>
    getCandidateApplications(id, page, size)
  );

  const openViewDialog = (applicant) => {
    setSelectedApplicant(applicant);
    setOpenViewProfileApplicantDialog(true);
  };

  const handleUpdateStatus = async (applicationId, status) => {
    updateStatus({
      applicationId,
      jobPostId: id,
      status,
      setListData: applicants.setData,
      setSelected: setSelectedApplicant,
    });
  };

  return (
    <div>
      <div className="flex gap-3 mb-6 items-center">
        <div className="relative">
          <HiOutlineSearch
            size={20}
            className="absolute text-gray-400 top-1/2 -translate-y-1/2 left-3"
          />
          <input
            type="text"
            placeholder="Search..."
            name=""
            id=""
            className="text-sm focus:outline-none active:outline-none h-10 w-[24rem] border border-gray-300 rounded-sm pr-4 pl-11"
          />
        </div>
        <div>Lọc theo STATUS</div>
        <h1>Số lượng đã ứng tuyển: {applicants.data.length}</h1>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 px-5">
        {applicants.data.map((applicant) => (
          <ApplicantCard
            key={applicant.id}
            applicant={applicant}
            onView={openViewDialog}
            onUpdate={openViewDialog}
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
      <UpdateStatusApplicantDialog
        open={openViewProfileApplicantDialog}
        applicant={selectedApplicant}
        onClose={() => setOpenViewProfileApplicantDialog(false)}
        onUpdateStatus={handleUpdateStatus}
      />
    </div>
  );
};

export default ApplicantListSection;
