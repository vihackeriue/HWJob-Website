import React from "react";
import PrimaryTitle from "../ui/title/PrimaryTitle";
import JobPostCard from "../ui/cards/JobPostCard";
import {tGlobal} from "../../utils/translator";

export const TopJobSection = ({jobPosts}) => {
    // Lấy tối đa 12 tin (2 cột * 6 hàng)
    const displayPosts = jobPosts.slice(0, 12);

    return (
        <section className="p-3 bg-white dark:bg-stoneBrown-900/50 rounded-2xl">
            <PrimaryTitle>{tGlobal("common.title.topJob")}</PrimaryTitle>
            
            {/* Grid 2 cột */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                {displayPosts.map((item) => (
                    <JobPostCard key={item.id} jobPost={item}/>
                ))}
            </div>
            
            {displayPosts.length === 0 && (
                <div className="text-center py-10 text-gray-500">
                    Chưa có tin tuyển dụng nào.
                </div>
            )}
        </section>
    );
};
