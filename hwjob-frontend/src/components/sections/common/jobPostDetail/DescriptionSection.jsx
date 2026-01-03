import React from "react";
import InfoCard from "../../../ui/cards/InfoCard";
import PrimaryTitle from "../../../ui/title/PrimaryTitle";
import {formatDate} from "../../../../utils/date";
import {SiLevelsdotfyi} from "react-icons/si";
import {FaPeopleCarryBox} from "react-icons/fa6";
import {CiLocationOn} from "react-icons/ci";
import {LiaIndustrySolid} from "react-icons/lia";
import {HiOutlineCalendarDateRange} from "react-icons/hi2";
import {IoEarth} from "react-icons/io5";

const DescriptionSection = ({jobPost}) => {
    return (
        <>
            <PrimaryTitle>Chi tiết việc làm</PrimaryTitle>
            <div
                className="prose max-w-none p-3"
                dangerouslySetInnerHTML={{
                    __html: jobPost.description,
                }}
            />

            {/* Hiển thị danh sách kỹ năng */}
            {jobPost.skills && jobPost.skills.length > 0 && (
                <div className="flex flex-col gap-3 bg-white rounded-2xl p-3 mt-3">
                    <PrimaryTitle>Kỹ năng yêu cầu</PrimaryTitle>
                    <div className="flex flex-wrap gap-2">
                        {jobPost.skills.map((skill) => (
                            <span
                                key={skill.id}
                                className="px-3 py-1 bg-teal-50 text-teal-700 rounded-full text-sm font-medium border border-teal-200"
                            >
                                {skill.name}
                            </span>
                        ))}
                    </div>
                </div>
            )}

            <div className="flex flex-col gap-3 bg-white rounded-2xl p-3 mt-3">
                <PrimaryTitle>Thông tin chung</PrimaryTitle>
                <div className="grid grid-cols-2 gap-3">
                    <InfoCard
                        icon={
                            <HiOutlineCalendarDateRange className="size-12 text-teal-600"/>
                        }
                        label="Ngày đăng"
                        value={formatDate(jobPost.createdAt)}
                    />
                    <InfoCard
                        icon={<IoEarth className="size-12 text-teal-600"/>}
                        label="Trạng thái"
                        value={jobPost.status}
                    />
                    <InfoCard
                        icon={<LiaIndustrySolid className="size-12 text-teal-600"/>}
                        label="Ngành"
                        value={jobPost.industry.name}
                    />
                    <InfoCard
                        icon={<CiLocationOn className="size-12 text-teal-600"/>}
                        label="Khu vực"
                        value={jobPost.region.name}
                    />
                    <InfoCard
                        icon={<SiLevelsdotfyi className="size-12 text-teal-600"/>}
                        label="Cấp độ"
                        value={jobPost.level.name}
                    />
                    <InfoCard
                        icon={<FaPeopleCarryBox className="size-12 text-teal-600"/>}
                        label="Loại công việc"
                        value={jobPost.jobType.name}
                    />
                </div>
            </div>
        </>
    );
};

export default DescriptionSection;
