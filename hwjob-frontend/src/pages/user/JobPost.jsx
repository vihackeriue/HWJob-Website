import React, { useState } from "react";
import JobPostCard from "../../components/ui/cards/JobPostCard";
import Pagination from "../../components/ui/pagination/Pagination";
import SearchBar from "../../components/ui/SearchBar";
import SecondTitle from "../../components/ui/title/SecondTitle";
import FormSelect from "../../components/ui/form/FormSelect";
import FormSwitch from "../../components/ui/form/FormSwitch";
import PrimaryButton from "../../components/ui/button/PrimaryButton";
import RangeInput from "../../components/ui/RangeInput";

const JobPost = () => {
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
  let page = 1;
  let totalPages = 5;
  const location = [
    { id: 1, name: "Hồ Chí Minh" },
    { id: 2, name: "Hà Nội" },
    { id: 3, name: "Đà Nẵng" },
  ];
  const jobTypes = [
    { id: 1, title: "Freelancer" },
    { id: 2, title: "Toàn thời gian" },
    { id: 3, title: "Bán thời gian" },
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormPersonalInf((prev) => ({ ...prev, [name]: value }));
  };
  const [selectedTypes, setSelectedTypes] = useState([]);
  const [range, setRange] = useState({ from: 1, to: 10 });
  return (
    <>
      <div className="flex justify-center mt-5">
        <SearchBar />
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 mt-3">
        <div className="col-span-1 bg-white dark:bg-stoneBrown-900/50 rounded-2xl  p-3 flex flex-col gap-3">
          <FormSelect
            label="Ngành nghề"
            name="gender"
            onChange={handleChange}
            options={location}
            placeholder="Chọn ngành nghề"
          />
          <FormSelect
            label="Loại công việc"
            name="gender"
            onChange={handleChange}
            options={location}
            placeholder="Loại công việc"
          />
          <FormSelect
            label="Ngày đăng"
            name="gender"
            onChange={handleChange}
            options={location}
            placeholder="Chọn Ngày đăng"
          />
          <FormSwitch
            options={jobTypes}
            selected={selectedTypes}
            onChange={setSelectedTypes}
            title={"Loại công việc"}
          />
          <RangeInput
            labelFrom="Từ"
            labelTo="Đến"
            value={range}
            onChange={setRange}
          />
          <PrimaryButton>Lọc</PrimaryButton>
        </div>
        <div className="flex flex-col  col-span-2 bg-white dark:bg-stoneBrown-900/50 rounded-2xl ">
          {jobPosts.map((item) => (
            <JobPostCard jobPost={item} />
          ))}

          <Pagination pagination={{ page, totalPages }} />
        </div>
      </div>
    </>
  );
};

export default JobPost;
