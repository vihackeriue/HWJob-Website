import React from "react";
import BannerSection from "../../components/sections/BannerSection";
import { TopJobSection } from "../../components/sections/TopJobSection";
import TopIndustrySection from "../../components/sections/TopIndustrySection";

import { useTranslation } from "react-i18next";

import FeatureSection from "../../components/sections/FeatureSection";
import TopRecruiterSection from "../../components/sections/TopRecruiterSection";
import IntroduceRecruiterSection from "../../components/sections/IntroduceRecruiterSection";
import PrimaryTitle from "../../components/ui/title/PrimaryTitle";
import SuggestedJobSection from "../../components/sections/SuggestedJobSection";
import SearchBar from "../../components/ui/SearchBar";

export default function Home() {
  const { t } = useTranslation();

  const recruiters = [
    {
      id: 1,
      name: "FPT Software",
      image: "https://www.senviet.art/wp-content/uploads/edd/2017/09/fpt.jpg",
      totalPost: 21,
      recruiting: 5,
    },
    {
      id: 2,
      name: "VNG Corporation",
      image:
        "https://th.bing.com/th/id/R.a8211ae17a648d727801f00d07e6b6b9?rik=bv%2fmCoC3Vr%2bYww&pid=ImgRaw&r=0",
      totalPost: 18,
      recruiting: 4,
    },
    {
      id: 3,
      name: "VinGroup",
      image:
        "https://tse3.mm.bing.net/th/id/OIP.JTIhJns-wGODaue_kgRWsAHaD4?cb=12&rs=1&pid=ImgDetMain&o=7&rm=3",
      totalPost: 25,
      recruiting: 7,
    },
    {
      id: 4,
      name: "Shopee Vietnam",
      image:
        "https://upload.wikimedia.org/wikipedia/commons/0/0e/Shopee_logo.svg",
      totalPost: 30,
      recruiting: 10,
    },
    {
      id: 5,
      name: "TMA Solutions",
      image: "https://tma.vn/Data/Sites/1/skins/default/img/logo.png",
      totalPost: 15,
      recruiting: 3,
    },
    {
      id: 6,
      name: "NashTech Vietnam",
      image:
        "https://cdn.nashtechglobal.com/wp-content/uploads/2020/05/logo.svg",
      totalPost: 22,
      recruiting: 6,
    },
    {
      id: 7,
      name: "Techcombank",
      image:
        "https://upload.wikimedia.org/wikipedia/commons/2/20/Techcombank_logo.svg",
      totalPost: 10,
      recruiting: 2,
    },
    {
      id: 8,
      name: "Viettel Group",
      image:
        "https://upload.wikimedia.org/wikipedia/commons/f/f9/Viettel_logo_2021.svg",
      totalPost: 28,
      recruiting: 8,
    },
    {
      id: 9,
      name: "MB Bank",
      image:
        "https://upload.wikimedia.org/wikipedia/commons/b/bc/MB_Bank_logo.svg",
      totalPost: 12,
      recruiting: 4,
    },
    {
      id: 10,
      name: "CMC Corporation",
      image:
        "https://upload.wikimedia.org/wikipedia/commons/3/3d/CMC_Corporation_logo.svg",
      totalPost: 17,
      recruiting: 5,
    },
  ];
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
  const industries = [
    { id: 1, name: "Software Engineer", totalJob: 20 },
    { id: 2, name: "Data Analyst", totalJob: 15 },
    { id: 3, name: "UI/UX Designer", totalJob: 12 },
    { id: 4, name: "Project Manager", totalJob: 10 },
    { id: 5, name: "DevOps Engineer", totalJob: 8 },
    { id: 6, name: "Network Administrator", totalJob: 6 },
    { id: 7, name: "Cybersecurity Specialist", totalJob: 7 },
    { id: 8, name: "Mobile Developer", totalJob: 9 },
    { id: 9, name: "Frontend Developer", totalJob: 14 },
    { id: 10, name: "Backend Developer", totalJob: 13 },
    { id: 11, name: "AI / Machine Learning Engineer", totalJob: 11 },
    { id: 12, name: "Quality Assurance (QA/QC)", totalJob: 10 },
    { id: 13, name: "Business Analyst", totalJob: 8 },
    { id: 14, name: "Digital Marketing", totalJob: 16 },
    { id: 15, name: "Human Resources (HR)", totalJob: 9 },
    { id: 16, name: "Finance / Accounting", totalJob: 7 },
    { id: 17, name: "Customer Service", totalJob: 12 },
    { id: 18, name: "Sales & Business Development", totalJob: 18 },
    { id: 19, name: "Logistics / Supply Chain", totalJob: 6 },
    { id: 20, name: "Content Creator / Copywriter", totalJob: 5 },
  ];

  return (
    <div>
      <div className="w-screen relative left-1/2 right-1/2 -translate-x-1/2">
        <BannerSection />
        <div className="absolute left-1/2 -translate-x-1/2 -translate-y-1/2 shadow-lg hover:shadow-2xl rounded-2xl">
          <SearchBar />
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-5 justify-between mt-20">
        <div className="flex flex-col gap-3">
          <TopIndustrySection t={t} industries={industries} />
          <SuggestedJobSection t={t} jobPosts={jobPosts} />
        </div>
        <TopJobSection jobPosts={jobPosts} t={t} />
      </div>
      <IntroduceRecruiterSection t={t} />
      <TopRecruiterSection recruiters={recruiters} t={t} />

      <FeatureSection t={t} />
      <div>hotline</div>
    </div>
  );
}
