import React, {useState} from "react";

import PrimaryButton from "../../components/ui/button/PrimaryButton";

import {useList} from "../../hooks/useList";
import {HiOutlineSearch} from "react-icons/hi";
import {getJobPosts} from "../../services/jobPostService";
import {JobPostListSection} from "../../components/sections/JobPostListSection";
import {JobPostFilterSection} from "../../components/sections/JobPostFilterSection";

const JobPostList = () => {
    const jobPosts = useList(getJobPosts);

    const [formFilter, setFormFilter] = useState({});

    const handleFilter = () => {
        jobPosts.setParams(formFilter);
        jobPosts.setPage(1);
    };
    return (
        <>
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 mt-3">
                <JobPostFilterSection
                    formFilter={formFilter}
                    setFormFilter={setFormFilter}
                    onFilter={handleFilter}
                />
                <div className="flex flex-col gap-3 col-span-2 ">
                    <div className="flex gap-3 bg-white dark:bg-stoneBrown-900/50 rounded-2xl p-3 ">
                        <div className="relative ">
                            <HiOutlineSearch
                                size={18}
                                className="absolute left-3 top-2.5 text-gray-400"
                            />
                            <input
                                type="text"
                                placeholder="Vị trí tuyển dụng, tên công ty,..."
                                className="text-lg focus:outline-none h-10 w-[24rem] rounded-xl pl-9 pr-4 transition "
                            />
                        </div>
                        <PrimaryButton>Tìm kiếm</PrimaryButton>
                    </div>

                    <JobPostListSection
                        jobPosts={jobPosts.data}
                        pagination={{
                            page: jobPosts.page,
                            totalPages: jobPosts.totalPages,
                            setPage: jobPosts.setPage,
                        }}
                    />
                </div>
            </div>
        </>
    );
};

export default JobPostList;
