import React from "react";
import {useList} from "../../../../hooks/useList";
import {getAllApplicantOfRecruiter} from "../../../../services/applicationService";
import {STATUS_APPLICATION_MAP} from "../../../../constants/statusApplication";
import Pagination from "../../../ui/pagination/Pagination";

const ApplicantListSection = () => {
    const applications = useList(getAllApplicantOfRecruiter);

    return (
        <div className="bg-white rounded-2xl p-5 shadow">
            <h2 className="text-xl font-semibold mb-5">
                Danh sách ứng viên ứng tuyển
            </h2>

            <div className="space-y-4">
                {applications.data?.map((item) => {
                    const status = STATUS_APPLICATION_MAP[item.status];

                    return (
                        <div
                            key={`${item.candidateId}-${item.jobPostId}`}
                            className="border rounded-xl p-4 flex gap-4 hover:shadow-md transition"
                        >
                            {/* Avatar */}
                            <img
                                src={item.imageUrl}
                                alt={item.fullName}
                                className="w-14 h-14 rounded-full object-cover"
                            />

                            {/* Content */}
                            <div className="flex-1 space-y-2">
                                {/* Name + status */}
                                <div className="flex justify-between items-start">
                                    <div>
                                        <p className="font-semibold text-lg">{item.fullName}</p>
                                        <p className="text-sm text-gray-500">{item.email}</p>
                                    </div>

                                    {status && (
                                        <span
                                            className={`px-3 py-1 rounded-full text-sm font-medium ${status.className}`}
                                        >
                      {status.name}
                    </span>
                                    )}
                                </div>

                                {/* Job title */}
                                <p className="text-sm">
                                    <span className="font-medium">Bài đăng:</span>{" "}
                                    {item.jobPostTitle}
                                </p>

                                {/* Note (nếu có, dùng sau) */}
                                {status?.note?.RECRUITER && (
                                    <p className="text-xs text-gray-500 italic">
                                        {status.note.RECRUITER}
                                    </p>
                                )}

                                {/* Actions (chuẩn bị sẵn) */}
                                {status?.actions?.RECRUITER?.length > 0 && (
                                    <div className="flex gap-2 pt-2">
                                        {status.actions.RECRUITER.map((action) => (
                                            <button
                                                key={action.to}
                                                className={`
                          px-3 py-1 rounded-lg text-sm font-medium
                          ${
                                                    action.variant === "primary" &&
                                                    "bg-blue-600 text-white"
                                                }
                          ${
                                                    action.variant === "secondary" &&
                                                    "bg-gray-200 text-gray-800"
                                                }
                          ${
                                                    action.variant === "danger" &&
                                                    "bg-red-500 text-white"
                                                }
                        `}
                                            >
                                                {action.label}
                                            </button>
                                        ))}
                                    </div>
                                )}
                            </div>
                        </div>
                    );
                })}

                {applications.data?.length === 0 && (
                    <p className="text-center text-gray-500 py-6">Chưa có ứng viên nào</p>
                )}
            </div>

            <div className="flex justify-center m-3">
                <Pagination
                    pagination={{
                        page: applications.page,
                        totalPages: applications.totalPages,
                        setPage: applications.setPage,
                    }}
                />
            </div>
        </div>
    );
};

export default ApplicantListSection;
