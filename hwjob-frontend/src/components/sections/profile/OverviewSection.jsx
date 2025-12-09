import React from "react";
import SecondTitle from "../../ui/title/SecondTitle";
import ProgressBar from "../../ui/ProgressBar";

const OverviewSection = () => {
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
  return (
    <div className="flex flex-col gap-3">
      <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
        <SecondTitle>Thông tin các nhân</SecondTitle>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 mb-3">
          <PersonalInfoItem title="Họ và tên">
            {personalInfo.name}
          </PersonalInfoItem>
          <PersonalInfoItem title="Giới tính">
            {personalInfo.gender}
          </PersonalInfoItem>
          <PersonalInfoItem title="Tuổi">{personalInfo.dob}</PersonalInfoItem>
          <PersonalInfoItem title="Trình độ">
            {personalInfo.education}
          </PersonalInfoItem>
          <PersonalInfoItem title="Email">
            {personalInfo.email}
          </PersonalInfoItem>
          <PersonalInfoItem title="Phone">
            {personalInfo.phone}
          </PersonalInfoItem>
        </div>
        <SecondTitle>Kỹ năng</SecondTitle>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-1">
          <ProgressBar title={"Tiếng anh"} value={60} />
          <ProgressBar title={"Giao tiếp"} value={30} />
          <ProgressBar title={"Giao tiếp"} value={30} />
          <ProgressBar title={"Giao tiếp"} value={30} />
        </div>
      </div>

      <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl min-h-50">
        <SecondTitle>Tóm tắt bản thân</SecondTitle>
        <div
          className="prose max-w-none p-2 border rounded-xl border-gray-300 bg-lightGrayishBlue dark:bg-stoneBrown-900/50 dark:border-gray-600 "
          dangerouslySetInnerHTML={{
            __html: personalInfo.summary,
          }}
        />
      </div>
      <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
        <div className="flex justify-between items-center mb-3">
          <h1 className="text-xl">Huy hiệu</h1>
          <button>Xem thêm</button>
        </div>
        <div className="flex gap-3 justify-center">
          <img
            src="https://static.vecteezy.com/system/resources/previews/045/946/183/non_2x/badge-concept-line-icon-simple-element-illustration-badge-concept-outline-symbol-design-free-vector.jpg"
            alt=""
            className="size-24 rounded-2xl border border-gray-300"
          />
          <img
            src="https://static.vecteezy.com/system/resources/previews/045/946/183/non_2x/badge-concept-line-icon-simple-element-illustration-badge-concept-outline-symbol-design-free-vector.jpg"
            alt=""
            className="size-24 rounded-2xl border border-gray-300"
          />
          <img
            src="https://static.vecteezy.com/system/resources/previews/045/946/183/non_2x/badge-concept-line-icon-simple-element-illustration-badge-concept-outline-symbol-design-free-vector.jpg"
            alt=""
            className="size-24 rounded-2xl border border-gray-300"
          />
        </div>
      </div>
    </div>
  );
};

export default OverviewSection;

const PersonalInfoItem = ({ title, children }) => {
  return (
    <p className="bg-lightGrayishBlue dark:bg-stoneBrown-900 p-2 rounded-lg">
      <span className="font-bold">{title}: </span>
      {children}
    </p>
  );
};
