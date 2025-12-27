import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { useList } from "../../../../hooks/useList";
import { getStaffOfWork } from "../../../../services/workService";

import StaffCard from "../../../ui/cards/StaffCard";
import { useUpdateWorkStatus } from "../../../../hooks/useUpdateWorkStatus";
import UpdateWorkStatusDialog from "../../../dialog/recruiter/UpdateWorkStatusDialog";

const StaffListSection = () => {
  const { id } = useParams();
  const staff = useList((page, size) => getStaffOfWork(id, page, size));
  const { updateStatus } = useUpdateWorkStatus();

  const [openUpdateWorkStatusDialog, setOpenUpdateWorkStatusDialog] =
    useState(false);
  const [selectedStaff, setSelectedStaff] = useState(null);
  const openDialogHandler = (staff) => {
    setSelectedStaff(staff);
    setOpenUpdateWorkStatusDialog(true);
  };

  const closeDialogHandler = () => {
    setSelectedStaff(null);
    setOpenUpdateWorkStatusDialog(false);
  };

  const handleUpdateStatus = async (applicationId, status, payload = {}) => {
    await updateStatus({
      applicationId: applicationId,
      jobPostId: id,
      status: status,
      payload: payload,
      setListData: staff.setData,
      setSelected: setSelectedStaff,
    });
  };

  return (
    <div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 px-5">
        {staff.data.map((staff) => (
          <StaffCard
            key={staff.id}
            staff={staff}
            onUpdateStatus={() => openDialogHandler(staff)}
          />
        ))}
      </div>

      <UpdateWorkStatusDialog
        open={openUpdateWorkStatusDialog}
        staff={selectedStaff}
        onClose={closeDialogHandler}
        onUpdateStatus={handleUpdateStatus}
      />
    </div>
  );
};

export default StaffListSection;
