import React from "react";
import { GoCheckCircleFill } from "react-icons/go";

const ProfileCard = ({ user }) => {
  return (
    <div className="flex flex-col items-center bg-white dark:bg-stoneBrown-900/50 p-10 rounded-2xl shadow-sm">
      <img
        src={user.avatar}
        alt={user.name}
        className="h-48 w-48 rounded-full object-cover border border-red-300"
      />
      <h1 className="text-xl font-semibold flex items-center gap-2 mt-3">
        <span>{user.name}</span>
        {user.verified && <GoCheckCircleFill className="text-teal-500" />}
      </h1>
      <p className="text-gray-500">@{user.username}</p>
    </div>
  );
};

export default ProfileCard;
