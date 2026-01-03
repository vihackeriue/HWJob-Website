import React, { useState, useMemo } from "react";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  LineChart,
  Line,
  CartesianGrid,
} from "recharts";
import { MdWorkOutline } from "react-icons/md";
import { CiSearch, CiClock2 } from "react-icons/ci";
import { FaMoneyCheck } from "react-icons/fa";

import { HiOutlineEyeOff } from "react-icons/hi";
import { GiMoneyStack } from "react-icons/gi";
import { useDetail } from "../../../../hooks/useDetail";
import { getJobPostOfRecruiterStats } from "../../../../services/jobPostService";
import QuantityStatisticsCard from "../../../ui/cards/QuantityStatisticsCard";
import Loading from "../../../ui/Loading";
import { IoCashOutline } from "react-icons/io5";

/* ================= CONSTANT ================= */

const STATUS_COLORS = ["#22c55e", "#9ca3af", "#ef4444"];
const SALARY_COLORS = ["#22c55e", "#f97316"];

/* ================= HELPER ================= */

const buildHourlySeries = (data = []) => {
  const map = {};
  data.forEach((d) => {
    map[d.hour] = (map[d.hour] || 0) + d.total;
  });

  return Array.from({ length: 24 }, (_, hour) => ({
    hour,
    total: map[hour] || 0,
  }));
};

const buildLast12MonthsPosting = (postingFrequency = []) => {
  const now = new Date();

  // Map dữ liệu từ API: key = YYYY-MM
  const map = {};
  postingFrequency.forEach((item) => {
    const key = `${item.year}-${item.month}`;
    map[key] = item.count;
  });

  // Build 12 tháng gần nhất
  return Array.from({ length: 12 }).map((_, index) => {
    const date = new Date(now.getFullYear(), now.getMonth() - (11 - index), 1);

    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const key = `${year}-${month}`;

    return {
      time: `${month}/${year}`, // hiển thị MM/YYYY
      count: map[key] || 0, // không có dữ liệu => 0
    };
  });
};

/* ================= MAIN ================= */

const StatisticSection = () => {
  const { data, loading } = useDetail(getJobPostOfRecruiterStats);

  /* ===== SAFE DEFAULT ===== */
  const jobPostStats = data?.jobPostStats ?? {};
  const systemApplyGoldenHour = data?.systemApplyGoldenHour ?? [];
  const recruiterApplyGoldenHour = data?.recruiterApplyGoldenHour ?? [];
  const postingFrequency = data?.postingFrequency ?? [];
  const workSalaryStats = data?.workSalaryStats ?? {};

  /* ===== MEMO (ALWAYS CALLED) ===== */

  const jobStatusData = useMemo(
    () => [
      { name: "Mở tuyển", value: jobPostStats.openingJobPosts || 0 },
      { name: "Bị ẩn", value: jobPostStats.hiddenJobPosts || 0 },
      { name: "Hết hạn", value: jobPostStats.expiredJobPosts || 0 },
    ],
    [jobPostStats]
  );

  const salaryData = useMemo(
    () => [
      { name: "Đã thanh toán", value: workSalaryStats.paidSalary || 0 },
      { name: "Chưa thanh toán", value: workSalaryStats.unpaidSalary || 0 },
    ],
    [workSalaryStats]
  );

  const recruiterHourly = useMemo(
    () => buildHourlySeries(recruiterApplyGoldenHour),
    [recruiterApplyGoldenHour]
  );

  const systemHourly = useMemo(
    () => buildHourlySeries(systemApplyGoldenHour),
    [systemApplyGoldenHour]
  );

  const postingChartData = useMemo(
    () => buildLast12MonthsPosting(postingFrequency),
    [postingFrequency]
  );

  const paidPercent = useMemo(() => {
    if (!workSalaryStats.totalSalary) return 0;
    return Math.round(
      (workSalaryStats.paidSalary / workSalaryStats.totalSalary) * 100
    );
  }, [workSalaryStats]);

  /* ===== RENDER AFTER HOOKS ===== */
  const formatMoney = (value = 0) => value.toLocaleString("vi-VN") + " ₫";
  if (loading) return <Loading />;

  return (
    <div className="space-y-6">
      {/* ===== OVERVIEW ===== */}
      <div className="flex gap-4 bg-white p-4 rounded-2xl shadow">
        <QuantityStatisticsCard
          stat={jobPostStats.totalJobPosts}
          icon={<MdWorkOutline className="size-8 text-blue-600" />}
          title="Tổng việc làm"
        />
        <QuantityStatisticsCard
          stat={jobPostStats.openingJobPosts}
          icon={<CiSearch className="size-8 text-green-600" />}
          title="Mở tuyển"
        />
        <QuantityStatisticsCard
          stat={jobPostStats.hiddenJobPosts}
          icon={<HiOutlineEyeOff className="size-8 text-gray-600" />}
          title="Bị ẩn"
        />
        <QuantityStatisticsCard
          stat={jobPostStats.expiredJobPosts}
          icon={<CiClock2 className="size-8 text-red-600" />}
          title="Hết hạn"
        />
      </div>

      {/* ===== DONUTS ===== */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white p-4 rounded-2xl shadow h-72">
          <h3 className="font-semibold mb-2">Trạng thái bài đăng</h3>
          <ResponsiveContainer>
            <PieChart>
              <Pie
                data={jobStatusData}
                dataKey="value"
                innerRadius={55}
                outerRadius={85}
              >
                {jobStatusData.map((_, i) => (
                  <Cell key={i} fill={STATUS_COLORS[i]} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer>
        </div>
        <div className="grid grid-cols-1 gap-3">
          {/* ===== TINY LINE – RECRUITER ===== */}
          <div className="bg-white p-4 rounded-2xl shadow">
            <h3 className="font-semibold mb-2">
              Xu hướng apply theo giờ (Của tôi)
            </h3>

            <div className="h-24">
              <ResponsiveContainer>
                <LineChart
                  data={recruiterHourly}
                  margin={{
                    top: 15,
                    right: 0,
                    left: 0,
                    bottom: 5,
                  }}
                >
                  <CartesianGrid strokeDasharray="3 3" />

                  <YAxis yAxisId="left" orientation="left" />
                  <YAxis yAxisId="right" orientation="right" width="auto" />
                  <Tooltip
                    formatter={(v) => [`${v} apply`, ""]}
                    labelFormatter={(h) => `Giờ ${h}`}
                  />
                  <Line
                    yAxisId="left"
                    type="monotone"
                    dataKey="total"
                    stroke="#3b82f6"
                    strokeWidth={2}
                    dot={false}
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </div>

          {/* ===== TINY LINE – SYSTEM ===== */}
          <div className="bg-white p-4 rounded-2xl shadow">
            <h3 className="font-semibold mb-2">
              Xu hướng apply theo giờ (Toàn hệ thống)
            </h3>

            <div className="h-24">
              <ResponsiveContainer>
                <LineChart data={systemHourly}>
                  <YAxis yAxisId="right" orientation="right" hide />
                  <Tooltip
                    formatter={(v) => [`${v} apply`, ""]}
                    labelFormatter={(h) => `Giờ ${h}`}
                  />
                  <Line
                    yAxisId="right"
                    type="monotone"
                    dataKey="total"
                    stroke="#9ca3af"
                    strokeWidth={2}
                    dot={false}
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </div>
        </div>
      </div>
      <div className="flex gap-4 bg-white p-4 rounded-2xl shadow">
        <QuantityStatisticsCard
          stat={formatMoney(workSalaryStats.totalSalary)}
          icon={<GiMoneyStack className="size-8 text-blue-600" />}
          title="Tổng thanh toán"
        />
        <QuantityStatisticsCard
          stat={formatMoney(workSalaryStats.paidSalary)}
          icon={<IoCashOutline className="size-8 text-green-600" />}
          title="Đã thanh toán"
        />
        <QuantityStatisticsCard
          stat={formatMoney(workSalaryStats.unpaidSalary)}
          icon={<FaMoneyCheck className="size-8 text-gray-600" />}
          title="Chưa thanh toán"
        />
      </div>

      <div className="bg-white p-5 rounded-2xl shadow-sm h-72 flex flex-col">
        {/* Header */}
        <div className="mb-3">
          <h3 className="font-semibold text-lg">Thanh toán</h3>
          <p className="text-sm text-gray-500">
            Đã thanh toán{" "}
            <span className="font-medium text-green-600">{paidPercent}%</span>
          </p>
        </div>

        {/* Chart */}
        <div className="flex-1 relative">
          <ResponsiveContainer>
            <PieChart>
              <Pie
                data={salaryData}
                dataKey="value"
                innerRadius={55}
                outerRadius={85}
                paddingAngle={3}
                cornerRadius={10}
              >
                {salaryData.map((_, i) => (
                  <Cell
                    key={i}
                    fill={SALARY_COLORS[i]}
                    stroke="white"
                    strokeWidth={2}
                  />
                ))}
              </Pie>

              <Tooltip
                formatter={(value) => value.toLocaleString("vi-VN") + " đ"}
              />
            </PieChart>
          </ResponsiveContainer>

          {/* Center text */}
          <div className="absolute inset-0 flex flex-col items-center justify-center pointer-events-none">
            <p className="text-xs text-gray-500">Đã thanh toán</p>
            <p className="text-2xl font-semibold text-green-600">
              {paidPercent}%
            </p>
          </div>
        </div>
      </div>

      {/* ===== POSTING FREQUENCY ===== */}
      <div className="bg-white rounded-2xl shadow-sm p-5 h-80 flex flex-col">
        {/* Header */}
        <div className="flex items-center justify-between mb-4">
          <div>
            <h3 className="text-base font-semibold text-gray-800">
              Tần suất đăng job
            </h3>
            <p className="text-sm text-gray-500">12 tháng gần nhất</p>
          </div>
        </div>

        {/* Job posting frequency Chart  */}
        <div className="flex-1">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart
              data={postingChartData}
              barCategoryGap={20}
              margin={{ top: 10, right: 10, left: 0, bottom: 0 }}
            >
              <XAxis
                dataKey="time"
                tickLine={false}
                axisLine={false}
                tick={{ fill: "#6b7280", fontSize: 12 }}
              />
              <YAxis
                allowDecimals={false}
                tickLine={false}
                axisLine={false}
                tick={{ fill: "#6b7280", fontSize: 12 }}
              />
              <Tooltip
                cursor={{ fill: "rgba(34,197,94,0.1)" }}
                formatter={(v) => [`${v} job`, "Số lượng"]}
              />
              <Bar
                dataKey="count"
                fill="#22c55e"
                radius={[6, 6, 0, 0]}
                maxBarSize={36}
              />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
};

export default StatisticSection;
