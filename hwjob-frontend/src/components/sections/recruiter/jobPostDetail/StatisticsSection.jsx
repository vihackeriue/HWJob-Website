import React from "react";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import { useDetail } from "../../../../hooks/useDetail";
import { getJobPostDetailStats } from "../../../../services/jobPostService";
import Loading from "../../../ui/Loading";
import PrimaryTitle from "../../../ui/title/PrimaryTitle";

import {
  IoPeopleOutline,
  IoEyeOutline,
  IoBookmarkOutline,
  IoDocumentTextOutline,
  IoCashOutline,
  IoCheckmarkCircleOutline,
  IoTimeOutline,
} from "react-icons/io5";
import SecondTitle from "../../../ui/title/SecondTitle";
import QuantityStatisticsCard from "../../../ui/cards/QuantityStatisticsCard";

/* ================= CONSTANT ================= */
const STAT_COLOR = ["#254e58", "#fa5b0f", "#3b82f6", "#ef4444"];

const formatMoney = (value = 0) => value.toLocaleString("vi-VN") + " ₫";

/* ================= MAIN ================= */
const StatisticsSection = ({ jobPostId }) => {
  const result = useDetail(getJobPostDetailStats, jobPostId);

  if (result.loading) {
    return (
      <div className="flex justify-center py-10">
        <Loading size={64} />
      </div>
    );
  }

  const stats = result?.data;

  const staffData = [
    { name: "Hoàn thành", value: stats.staffCompletedCount },
    {
      name: "Chưa hoàn thành",
      value: stats.totalStaffCount - stats.staffCompletedCount,
    },
  ];

  const salaryData = [
    { name: "Đã trả", value: stats.paidSalary },
    { name: "Chưa trả", value: stats.pendingSalary },
  ];

  return (
    <div className="p-6 space-y-6">
      <PrimaryTitle>Thống kê công việc</PrimaryTitle>

      {/* ================= TOP STAT ================= */}
      <div className="grid grid-cols-3 gap-4">
        {/* Interaction */}
        <div className="bg-white rounded-2xl shadow-lg p-6 col-span-2 border border-teal-900/20">
          <SecondTitle className="mb-4"> Thống kê tương tác</SecondTitle>
          <div className="grid grid-cols-2 gap-6">
            <StatItem
              icon={<IoEyeOutline className="size-6 text-teal-100" />}
              label="Lượt xem 1 giờ"
              value={stats.viewHourly}
            />
            <StatItem
              icon={<IoEyeOutline className="size-6 text-teal-900" />}
              label="Tổng lượt xem"
              value={stats.totalView}
            />
            <StatItem
              icon={
                <IoDocumentTextOutline className="size-6 text-brightOrange" />
              }
              label="Ứng tuyển"
              value={stats.applyCount}
            />
            <StatItem
              icon={
                <IoBookmarkOutline className="size-6 text-stoneBrown-700" />
              }
              label="Lượt lưu"
              value={stats.saveCount}
            />
          </div>
        </div>

        {/* Staff */}
        <div className="space-y-3">
          <QuantityStatisticsCard
            title="Tổng nhân viên"
            stat={stats.totalStaffCount}
            icon={<IoPeopleOutline className="size-8 text-teal-600" />}
          />
          <QuantityStatisticsCard
            title="Đã hoàn thành"
            stat={stats.staffCompletedCount}
            icon={<IoCheckmarkCircleOutline className="size-8 text-teal-600" />}
          />
          <QuantityStatisticsCard
            title="Chưa hoàn thành"
            stat={stats.totalStaffCount - stats.staffCompletedCount}
            icon={<IoTimeOutline className="size-8 text-brightOrange" />}
          />
        </div>
      </div>

      {/* ================= SALARY ================= */}
      <div className="grid grid-cols-3 gap-4">
        <QuantityStatisticsCard
          title="Tổng lương"
          stat={formatMoney(stats.totalSalary)}
          icon={<IoCashOutline className="size-6 text-teal-500" />}
        />
        <QuantityStatisticsCard
          title="Lương đã trả"
          stat={formatMoney(stats.paidSalary)}
          icon={<IoCashOutline className="size-6 text-teal-900" />}
        />
        <QuantityStatisticsCard
          title="Lương chưa trả"
          stat={formatMoney(stats.pendingSalary)}
          icon={<IoCashOutline className="size-6 text-brightOrange" />}
        />
      </div>

      {/* ================= CHART ================= */}
      <div className="grid grid-cols-2 gap-4">
        {/* Staff Pie */}
        <div className="bg-white rounded-2xl shadow-lg p-4">
          <SecondTitle>Nhân viên</SecondTitle>
          <ResponsiveContainer width="100%" height={260}>
            <PieChart>
              <Pie
                data={staffData}
                dataKey="value"
                nameKey="name"
                innerRadius={70}
                outerRadius={100}
                paddingAngle={4}
                label
              >
                {staffData.map((_, index) => (
                  <Cell key={index} fill={STAT_COLOR[index]} />
                ))}
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </div>

        {/* Salary Pie */}
        <div className="bg-white rounded-2xl shadow-lg p-4">
          <SecondTitle>Lương</SecondTitle>
          <ResponsiveContainer width="100%" height={260}>
            <PieChart>
              <Pie
                data={salaryData}
                dataKey="value"
                nameKey="name"
                innerRadius={70}
                outerRadius={100}
                paddingAngle={4}
                label
              >
                {salaryData.map((_, index) => (
                  <Cell key={index} fill={STAT_COLOR[index]} />
                ))}
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
};

/* ================= HELPER ================= */
const StatItem = ({ icon, label, value }) => {
  return (
    <div className="flex items-center gap-4">
      <div className={`p-3 rounded-xl bg-lightGrayishBlue`}>{icon}</div>
      <div>
        <p className="text-md ">{label}</p>
        <p className="text-2xl font-bold">{value}</p>
      </div>
    </div>
  );
};

export default StatisticsSection;
