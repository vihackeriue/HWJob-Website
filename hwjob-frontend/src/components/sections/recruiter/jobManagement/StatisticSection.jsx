import React from "react";
import QuantityStatisticsCard from "../../../ui/cards/QuantityStatisticsCard";
import { CiLocationOn } from "react-icons/ci";
import { useDetail } from "../../../../hooks/useDetail";
import { getJobPostOfRecruiterStats } from "../../../../services/jobPostService";
import Loading from "../../../ui/Loading";

const StatisticSection = () => {
  const { data: stats, loading } = useDetail(getJobPostOfRecruiterStats);

  if (loading) return <Loading />;

  return (
    <div>
      <div className="flex gap-4 w-full bg-dark-100 p-3 rounded-2xl shadow bg-white">
        <QuantityStatisticsCard
          stat={stats.totalJobPosts}
          icon={<CiLocationOn className="size-12 text-teal-600" />}
          title={"Tổng việc làm"}
        />
        <QuantityStatisticsCard
          stat={stats.openingJobPosts}
          icon={<CiLocationOn className="size-12 text-teal-600" />}
          title={"Đang mở tuyển"}
        />
        <QuantityStatisticsCard
          stat={stats.hiddenJobPosts}
          icon={<CiLocationOn className="size-12 text-teal-600" />}
          title={"Đã bị ẩn"}
        />
        <QuantityStatisticsCard
          stat={stats.expiredJobPosts}
          icon={<CiLocationOn className="size-12 text-teal-600" />}
          title={"Hết hạn"}
        />
      </div>
    </div>
  );
};

export default StatisticSection;
