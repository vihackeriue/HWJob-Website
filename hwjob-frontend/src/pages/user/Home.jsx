import React from "react";
import BannerSection from "../../components/sections/BannerSection";
import {TopJobSection} from "../../components/sections/TopJobSection";
import TopIndustrySection from "../../components/sections/TopIndustrySection";

import {useTranslation} from "react-i18next";

import FeatureSection from "../../components/sections/FeatureSection";
import TopRecruiterSection from "../../components/sections/TopRecruiterSection";
import IntroduceRecruiterSection from "../../components/sections/IntroduceRecruiterSection";
import SuggestedJobSection from "../../components/sections/SuggestedJobSection";
import SearchBar from "../../components/ui/SearchBar";
import {useList} from "../../hooks/useList.jsx";
import {getTopRecruiters} from "../../services/homeService.jsx";

export default function Home() {
    const {t} = useTranslation();

    const {data: recruiters} = useList(getTopRecruiters);
    // const {data: recommendJobPosts} =useList()

    const jobPosts = [
        {
            id: 1,
            title:
                "Nhân Viên Tư Vấn/Bán Hàng/Kinh Doanh Thị Trường Ngành Điện Tử - Nam - Thu Nhập Upto 20 Triệu Đồng - Láng Hạ (Hà Nội)",
            recruiter: "MB Bank",
            quanlity: 4,
            image: "https://www.senviet.art/wp-content/uploads/edd/2017/09/fpt.jpg",
            region: "Hà Nội",
            industry: "Kinh doanh",
            isLiked: true,
        },
        {
            id: 2,
            title: "Lập Trình Viên Java Spring Boot - Làm Việc Hybrid Tại TP.HCM",
            recruiter: "FPT Software",
            quanlity: 3,
            image: "https://www.senviet.art/wp-content/uploads/edd/2017/09/fpt.jpg",
            region: "TP.HCM",
            industry: "CNTT",
            isLiked: false,
        },
        {
            id: 3,
            title:
                "Chuyên Viên Phân Tích Dữ Liệu (Data Analyst) - Làm Việc Toàn Quốc",
            recruiter: "VNPT",
            quanlity: 2,
            image: "https://www.senviet.art/wp-content/uploads/edd/2017/09/fpt.jpg",
            region: "Toàn quốc",
            industry: "Công nghệ dữ liệu",
            isLiked: true,
        },
        {
            id: 4,
            title:
                "Nhân Viên Thiết Kế UI/UX - Lương Tới 25 Triệu - Môi Trường Startup",
            recruiter: "Tiki",
            quanlity: 5,
            image: "https://www.senviet.art/wp-content/uploads/edd/2017/09/fpt.jpg",
            region: "TP.HCM",
            industry: "Thiết kế",
            isLiked: false,
        },
        {
            id: 5,
            title: "Kỹ Sư DevOps - Làm Việc Với AWS, Docker, Kubernetes",
            recruiter: "VNG Corporation",
            quanlity: 2,
            image: "https://www.senviet.art/wp-content/uploads/edd/2017/09/fpt.jpg",
            region: "TP.HCM",
            industry: "CNTT",
            isLiked: false,
        },
        {
            id: 6,
            title:
                "Chuyên Viên Digital Marketing - Quản Lý Kênh Quảng Cáo Google, Meta",
            recruiter: "Shopee",
            quanlity: 4,
            image: "https://www.senviet.art/wp-content/uploads/edd/2017/09/fpt.jpg",
            region: "Hà Nội",
            industry: "Marketing",
            isLiked: false,
        },
        {
            id: 7,
            title: "Kế Toán Tổng Hợp - Làm Việc Giờ Hành Chính - Thu Nhập 15 Triệu",
            recruiter: "Viettel",
            quanlity: 3,
            image:
                "https://upload.wikimedia.org/wikipedia/commons/4/48/Viettel_logo_2021.svg",
            region: "Đà Nẵng",
            industry: "Kế toán",
            isLiked: false,
        },
        {
            id: 8,
            title: "Chuyên Viên Nhân Sự (HR Executive) - Làm Việc Tại Bình Dương",
            recruiter: "Thaco Group",
            quanlity: 2,
            image:
                "https://upload.wikimedia.org/wikipedia/commons/1/15/Thaco_logo.svg",
            region: "Bình Dương",
            industry: "Nhân sự",
            isLiked: false,
        },
        {
            id: 9,
            title: "Nhân Viên Kỹ Thuật IT Hỗ Trợ Hệ Thống - Làm Việc Văn Phòng",
            recruiter: "Techcombank",
            quanlity: 4,
            image:
                "https://upload.wikimedia.org/wikipedia/commons/2/28/Techcombank_logo.svg",
            region: "Hà Nội",
            industry: "CNTT",
            isLiked: false,
        },
        {
            id: 10,
            title:
                "Nhân Viên Chăm Sóc Khách Hàng Trực Tuyến - Không Yêu Cầu Kinh Nghiệm",
            recruiter: "Grab Vietnam",
            quanlity: 6,
            image:
                "https://upload.wikimedia.org/wikipedia/commons/5/5a/Grab_Logo.svg",
            region: "Cần Thơ",
            industry: "Dịch vụ khách hàng",
            isLiked: false,
        },
    ];
    const industries = [];

    return (
        <div>
            <div className="w-screen relative left-1/2 right-1/2 -translate-x-1/2">
                <BannerSection/>
                <div
                    className="absolute left-1/2 -translate-x-1/2 -translate-y-1/2 shadow-lg hover:shadow-2xl rounded-2xl">
                    <SearchBar/>
                </div>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-5 justify-between mt-20">
                <div className="flex flex-col gap-3">
                    <TopIndustrySection t={t} industries={industries}/>
                    <SuggestedJobSection t={t} jobPosts={jobPosts}/>
                </div>
                <TopJobSection jobPosts={jobPosts} t={t}/>
            </div>
            <IntroduceRecruiterSection t={t}/>
            <TopRecruiterSection recruiters={recruiters} t={t}/>

            <FeatureSection t={t}/>
            <div>hotline</div>
        </div>
    );
}
