import React, {useEffect, useState} from "react";
import {Dialog, DialogPanel, DialogTitle} from "@headlessui/react";
import PrimaryTitle from "../../ui/title/PrimaryTitle";
import SecondTitle from "../../ui/title/SecondTitle";
import {GENDER} from "../../../config/constants";
import PrimaryButton from "../../ui/button/PrimaryButton";
import {getCandidateProfileById} from "../../../services/userService";

const ViewCandidateProfileDialog = ({open, onClose, candidate}) => {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (open && candidate?.id) {
            setLoading(true);
            getCandidateProfileById(candidate.id)
                .then((res) => {
                    setProfile(res.result);
                })
                .catch((err) => {
                    console.error("Failed to fetch candidate profile:", err);
                })
                .finally(() => {
                    setLoading(false);
                });
        } else {
            setProfile(null);
        }
    }, [open, candidate]);

    if (!open) return null;

    return (
        <Dialog open={open} onClose={onClose} className="relative z-50">
            <div className="fixed inset-0 bg-black/30" aria-hidden="true"/>
            <div className="fixed inset-0 flex items-center justify-center p-4">
                <DialogPanel className="w-full max-w-4xl bg-gray-100 rounded-2xl p-6 max-h-[90vh] overflow-y-auto">
                    <div className="flex justify-between items-center mb-4">
                        <DialogTitle>
                            <PrimaryTitle>Hồ sơ ứng viên</PrimaryTitle>
                        </DialogTitle>
                        <button onClick={onClose} className="text-gray-500 hover:text-gray-700">
                            ✕
                        </button>
                    </div>

                    {loading ? (
                        <div className="text-center py-10">Đang tải thông tin...</div>
                    ) : profile ? (
                        <div className="flex flex-col gap-4">
                            {/* Header Info */}
                            <div className="bg-white p-4 rounded-2xl flex flex-col sm:flex-row items-center gap-6">
                                <img
                                    src={profile.imageUrl}
                                    alt={profile.fullName}
                                    className="w-32 h-32 rounded-full object-cover border-2 border-gray-200"
                                />
                                <div className="text-center sm:text-left">
                                    <h2 className="text-2xl font-bold text-gray-800">{profile.fullName}</h2>
                                    <p className="text-gray-600">{profile.email}</p>
                                    <p className="text-gray-600">{profile.phone}</p>
                                </div>
                            </div>

                            {/* Personal Info */}
                            <div className="bg-white p-4 rounded-2xl">
                                <SecondTitle>Thông tin cá nhân</SecondTitle>
                                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 mt-3">
                                    <InformationField label="Khu vực" value={profile.region?.name}/>
                                    <InformationField label="Ngày sinh" value={profile.dob}/>
                                    <InformationField
                                        label="Giới tính"
                                        value={GENDER.find((g) => g.code === profile.gender)?.name}
                                    />
                                    <InformationField label="Trình độ" value={profile.education}/>
                                    <InformationField label="Mức lương mong muốn" value={profile.expectSalary}/>
                                </div>
                            </div>

                            {/* Summary */}
                            <div className="bg-white p-4 rounded-2xl">
                                <SecondTitle>Giới thiệu bản thân</SecondTitle>
                                <div className="mt-2 text-gray-700">
                                    {profile.summary ? (
                                        <div
                                            className="prose max-w-none"
                                            dangerouslySetInnerHTML={{__html: profile.summary}}
                                        />
                                    ) : (
                                        <span className="italic text-gray-400">Chưa cập nhật</span>
                                    )}
                                </div>
                            </div>

                            {/* Skills */}
                            <div className="bg-white p-4 rounded-2xl">
                                <SecondTitle>Kỹ năng</SecondTitle>
                                {profile.skills && profile.skills.length > 0 ? (
                                    <div className="flex flex-wrap gap-2 mt-2">
                                        {profile.skills.map((skill) => (
                                            <span
                                                key={skill.id}
                                                className="px-3 py-1 bg-teal-50 text-teal-700 rounded-full text-sm font-medium border border-teal-200"
                                            >
                                                {skill.name}
                                            </span>
                                        ))}
                                    </div>
                                ) : (
                                    <span className="italic text-gray-400 mt-2 block">Chưa cập nhật</span>
                                )}
                            </div>
                        </div>
                    ) : (
                        <div className="text-center py-10 text-gray-500">Không tìm thấy thông tin ứng viên</div>
                    )}

                    <div className="flex justify-end mt-6">
                        <PrimaryButton onClick={onClose}>Đóng</PrimaryButton>
                    </div>
                </DialogPanel>
            </div>
        </Dialog>
    );
};

const InformationField = ({label, value}) => {
    const isEmpty = value === null || value === undefined || value === "";
    return (
        <div className="flex flex-col p-2 bg-gray-50 rounded-lg">
            <span className="text-sm font-medium text-gray-500">{label}</span>
            <span className="font-medium text-gray-800">
                {isEmpty ? <span className="italic text-gray-400">Chưa cập nhật</span> : value}
            </span>
        </div>
    );
};

export default ViewCandidateProfileDialog;
