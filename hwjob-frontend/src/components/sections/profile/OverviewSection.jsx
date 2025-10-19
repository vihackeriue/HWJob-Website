import React from "react";
import SecondTitle from "../../ui/title/SecondTitle";
import ProgressBar from "../../ui/ProgressBar";

const OverviewSection = ({ personalInfo }) => {
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
