import React from "react";
import OverviewSection from "../../../components/sections/profile/OverviewSection";

const Overview = () => {
  const personalInfo = {
    name: "Nguyễn Văn A",
    gender: 1,
    age: "21",
    education: "Đại học",
    email: "vi@gmail.com",
    phone: "099832732",

    summary: `
    <h2>Giới thiệu</h2>
    <p><strong>Xin chào!</strong> Mình là <strong>Nguyễn Văn A</strong> — một lập trình viên trẻ, yêu thích công nghệ và luôn mong muốn học hỏi để phát triển bản thân. Mình có kinh nghiệm làm việc với <strong>ReactJS</strong> ở frontend và <strong>Spring Boot</strong> ở backend, tập trung vào việc xây dựng các ứng dụng web hiệu năng cao và thân thiện với người dùng.</p>

    <h3>Mục tiêu nghề nghiệp</h3>
    <p>Mục tiêu của mình là trở thành một <strong>Fullstack Developer</strong> có khả năng đảm nhận toàn bộ quá trình phát triển phần mềm — từ thiết kế, lập trình, đến triển khai. Mình mong muốn được tham gia vào các dự án quy mô lớn, có tính thử thách để rèn luyện kỹ năng và nâng cao tư duy hệ thống.</p>

    <h3>Kỹ năng nổi bật</h3>
    <ul>
      <li><strong>Frontend:</strong> ReactJS, TailwindCSS, Next.js, Redux Toolkit, TypeScript</li>
      <li><strong>Backend:</strong> Spring Boot, RESTful API, JWT Authentication, Redis, MySQL, Docker</li>
      <li><strong>Khác:</strong> Git, CI/CD cơ bản, tư duy giải quyết vấn đề và khả năng học nhanh</li>
    </ul>

    <h3>Điểm mạnh cá nhân</h3>
    <p>Mình là người có tinh thần <em>tự học cao</em>, thích khám phá công nghệ mới và chủ động trong công việc. Mình coi trọng tinh thần hợp tác, chia sẻ và luôn cố gắng đóng góp tích cực vào mục tiêu chung của nhóm.</p>

    <h3>Sở thích</h3>
    <p>Ngoài công việc lập trình, mình yêu thích thể thao, đọc sách và tham gia các cộng đồng công nghệ để học hỏi, mở rộng mối quan hệ trong ngành.</p>
  `,
  };
  return <OverviewSection personalInfo={personalInfo} />;
};

export default Overview;
