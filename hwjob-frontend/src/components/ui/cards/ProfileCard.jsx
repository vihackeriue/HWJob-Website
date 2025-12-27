import React, { useState } from "react";
import { CiEdit } from "react-icons/ci";
import { GoCheckCircleFill } from "react-icons/go";
import UpdateAvatarDialog from "../../dialog/UpdateAvatarDialog";
import { useUpdateAvatar } from "../../../hooks/user/useUpdateAvatar.jsx";
import useAuth from "../../../hooks/useAuth";
import { useDetail } from "../../../hooks/useDetail.jsx";
import { getAverageRating } from "../../../services/reviewService.jsx";
import { FaStar } from "react-icons/fa6";
import { RiVerifiedBadgeLine } from "react-icons/ri";
import Loading from "../Loading.jsx";
import { getMyReputation } from "../../../services/reputationService.jsx";
const ProfileCard = ({ user }) => {
  const [openUpdateAvatarDialog, setOpenUpdateAvatarDialog] = useState(false);
  const { auth, updateAvatarRealtime } = useAuth();
  const { updateAvatar } = useUpdateAvatar();

  const averageRating = useDetail(getAverageRating);
  const reputation = useDetail(getMyReputation);

  console.log("averageRating", averageRating);
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
          src={user.imageUrl}
          alt={user.username}
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
      <p className="text-gray-500">@{auth.username}</p>
      <div className="flex gap-2 mt-2 text-md font-semibold">
        <div className="flex gap-1 items-center px-4 py-1 border border-brightOrange rounded-lg ">
          <FaStar className="text-yellow-400 size-6" />
          <p>{averageRating.data?.averageRating || 0}</p>
        </div>
        <div className="flex gap-1 items-center px-4 py-1 border border-brightOrange rounded-lg">
          <RiVerifiedBadgeLine className="text-teal-400 size-6" />
          <p>{Number(reputation?.data?.reputation ?? 0).toLocaleString()}</p>
        </div>
      </div>
      <UpdateAvatarDialog
        open={openUpdateAvatarDialog}
        onClose={() => setOpenUpdateAvatarDialog(false)}
        onSubmit={handleUpdateAvatar}
      />
    </div>
  );
};

export default ProfileCard;
