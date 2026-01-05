import {useParams} from "react-router-dom";
import {useDetail} from "../../hooks/useDetail.jsx";

import React from "react";
import SecondTitle from "../../components/ui/title/SecondTitle.jsx";
import useChat from "../../hooks/useChat.jsx";
import {toast} from "react-toastify";
import {chatService} from "../../services/chatService.jsx";
import useAuth from "../../hooks/useAuth.jsx";
import {getRecruiterProfileById} from "../../services/userService.jsx";
import {useList} from "../../hooks/useList.jsx";
import {getJobPostsByRecruiterId} from "../../services/jobPostService.jsx";
import JobPostCard from "../../components/ui/cards/JobPostCard.jsx";
import Pagination from "../../components/ui/pagination/Pagination.jsx";

const RecruiterPublicProfile = () => {
    const {id} = useParams();
    const {data: userProfile, loading} = useDetail(getRecruiterProfileById, id);
    const {auth} = useAuth();
    const {openConversation} = useChat();

    // Lấy danh sách job post của recruiter này
    const jobPosts = useList((page, size) => getJobPostsByRecruiterId(id, page, size));

    const handleMessage = async () => {
        try {
            const res = await chatService.createConversation("DIRECT", [id]);
            const conversation = res?.data?.result || res?.result;

            if (conversation) {
                openConversation(conversation);
            } else {
                toast.error("Không thể tạo cuộc trò chuyện");
            }
        } catch (error) {
            console.error("Failed to create conversation:", error);
            toast.error("Có lỗi xảy ra khi tạo cuộc trò chuyện");
        }
    };

    const handleFollow = () => {
        // TODO: Implement follow logic
        toast.info("Tính năng theo dõi đang được phát triển");
    };

    if (loading || !userProfile) return <div className="text-center py-10">Đang tải...</div>;

    // Kiểm tra đã đăng nhập và không phải profile của chính mình
    const canInteract = auth?.id && auth.id !== id;

    return (
        <div className="my-8 max-w-4xl mx-auto">
            <div className="bg-white dark:bg-stoneBrown-900/50 rounded-2xl shadow-sm p-8 mb-6">
                <div className="flex flex-col sm:flex-row items-start sm:items-center gap-6">
                    <div className="relative">
                        {userProfile.imageUrl ? (
                            <img
                                src={userProfile.imageUrl}
                                alt={userProfile.fullName}
                                className="w-24 h-24 rounded-full object-cover"
                            />
                        ) : (
                            <div
                                className="w-24 h-24 rounded-full bg-gray-300 flex items-center justify-center text-2xl font-semibold">
                                {userProfile.fullName?.charAt(0)}
                            </div>
                        )}
                    </div>
                    <div className="flex-1 min-w-0">
                        <h1 className="text-2xl font-bold">{userProfile.fullName}</h1>
                        <p className="text-gray-600 dark:text-gray-400">{userProfile.email}</p>
                    </div>

                    {/* Chỉ hiển thị nút khi đã đăng nhập và không phải profile của mình */}
                    {canInteract && (
                        <div className="flex flex-col sm:flex-row gap-3 mt-4 sm:mt-0">
                            <button
                                onClick={handleFollow}
                                className="px-6 py-2 border border-teal-600 text-teal-600 rounded-lg hover:bg-teal-50 dark:hover:bg-teal-900/20 transition"
                            >
                                Theo dõi
                            </button>
                            <button
                                onClick={handleMessage}
                                className="px-6 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700 transition"
                            >
                                Nhắn tin
                            </button>
                        </div>
                    )}
                </div>
            </div>

            <div className="bg-white dark:bg-stoneBrown-900/50 rounded-2xl shadow-sm p-8 mb-6">
                <SecondTitle>Tóm tắt</SecondTitle>
                <div className="mt-4 text-gray-700 dark:text-gray-300 leading-relaxed">
                    {userProfile.summary ? (
                        <div dangerouslySetInnerHTML={{__html: userProfile.summary}}/>
                    ) : (
                        <span className="italic text-gray-400">Chưa có tóm tắt</span>
                    )}
                </div>
            </div>

            <div className="bg-white dark:bg-stoneBrown-900/50 rounded-2xl shadow-sm p-8 mb-6">
                <SecondTitle>Thông tin cá nhân</SecondTitle>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
                    <InformationField label="Tên đầy đủ" value={userProfile.fullName}/>
                    <InformationField label="Email" value={userProfile.email}/>
                    <InformationField label="Số điện thoại" value={userProfile.phone}/>
                    <InformationField label="Khu vực" value={userProfile.region}/>
                    <InformationField label="Website" value={userProfile.website}/>
                </div>
            </div>

            {/* Danh sách việc làm của Recruiter */}
            <div className="bg-white dark:bg-stoneBrown-900/50 rounded-2xl shadow-sm p-8 mb-6">
                <SecondTitle>Việc làm đang tuyển</SecondTitle>
                <div className="mt-6">
                    {jobPosts.loading ? (
                        <div className="text-center py-4">Đang tải danh sách việc làm...</div>
                    ) : jobPosts.data.length > 0 ? (
                        <div className="flex flex-col gap-4">
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                {jobPosts.data.map((job) => (
                                    <JobPostCard key={job.id} jobPost={job}/>
                                ))}
                            </div>
                            <div className="flex justify-center mt-4">
                                <Pagination
                                    pagination={{
                                        page: jobPosts.page,
                                        totalPages: jobPosts.totalPages,
                                        setPage: jobPosts.setPage,
                                    }}
                                />
                            </div>
                        </div>
                    ) : (
                        <div className="text-center py-4 text-gray-500 italic">
                            Nhà tuyển dụng này chưa có bài đăng nào.
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

const InformationField = ({label, value}) => {
    const isEmpty = value === null || value === undefined || value === "" || value === "null";

    return (
        <div className="flex flex-col">
            <span className="text-sm font-medium text-gray-600 dark:text-gray-400">{label}</span>
            <span className="mt-1 text-base">
                {isEmpty ? (
                    <span className="italic text-gray-400">Không có</span>
                ) : (
                    <span className="font-medium">{value}</span>
                )}
            </span>
        </div>
    );
};

export default RecruiterPublicProfile;
