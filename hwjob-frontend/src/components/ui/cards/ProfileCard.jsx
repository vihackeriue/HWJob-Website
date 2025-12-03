import React, { useState } from "react";
import { CiEdit } from "react-icons/ci";
import { GoCheckCircleFill } from "react-icons/go";
import UpdateAvatarDialog from "../../dialog/UpdateAvatarDialog";
import { useUpdateAvatar } from "../../../hooks/useUpdateAvatar";
import useAuth from "../../../hooks/useAuth";

const ProfileCard = ({ user }) => {
  const [openUpdateAvatarDialog, setOpenUpdateAvatarDialog] = useState(false);
  const { auth, updateAvatarRealtime } = useAuth();
  const { updateAvatar } = useUpdateAvatar();

  const handleUpdateAvatar = async (file) => {
    if (!file) {
      alert("Vui lòng chọn ảnh!");
      return;
    }
    const res = await updateAvatar(file);

    updateAvatarRealtime(res.result.imageUrl);

    setOpenUpdateAvatarDialog(false);
  };
  return (
    <div className="flex flex-col items-center bg-white dark:bg-stoneBrown-900/50 p-10 rounded-2xl shadow-sm">
      <div className="relative w-48 h-48">
        <img
          src={auth?.userAvatar}
          alt={auth?.username}
          className="w-48 h-48 rounded-full object-cover border border-red-300"
        />

        {/* Nút chỉnh sửa avatar */}
        <button
          onClick={() => setOpenUpdateAvatarDialog(true)}
          className="absolute bottom-2 right-2 bg-white shadow-md p-2 rounded-full hover:bg-gray-100"
        >
          <CiEdit className="text-gray-700" />
        </button>
      </div>
      <h1 className="text-xl font-semibold flex items-center gap-2 mt-3">
        <span>{user.name}</span>
        {user.verified && <GoCheckCircleFill className="text-teal-500" />}
      </h1>
      <p className="text-gray-500">@{user.username}</p>

      <UpdateAvatarDialog
        open={openUpdateAvatarDialog}
        onClose={() => setOpenUpdateAvatarDialog(false)}
        onSubmit={handleUpdateAvatar}
      />
    </div>
  );
};

export default ProfileCard;
