import React from "react";
import PrimaryTitle from "../ui/title/PrimaryTitle";
import JobPostCard from "../ui/cards/JobPostCard";
import {tGlobal} from "../../utils/translator";

const SuggestedJobSection = ({jobPosts}) => {
    const displayPosts = jobPosts.slice(0, 12);

    return (
        <section className="bg-white rounded-2xl p-3 pb-10 dark:bg-stoneBrown-900/50">
            <PrimaryTitle>{tGlobal("common.title.suggestedJob")}</PrimaryTitle>

            {/* Grid 2 cột */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                {displayPosts.map((item) => (
                    <JobPostCard key={item.id} jobPost={item}/>
                ))}
            </div>

            {displayPosts.length === 0 && (
                <div className="text-center py-10 text-gray-500">
                    Chưa có việc làm gợi ý nào.
                </div>
            )}
        </section>
    );
};

export default SuggestedJobSection;
