import React from "react";
import {Link} from "react-router-dom";

const RecruiterCard = ({recruiter}) => {
    return (
        <div className="flex flex-col gap-1 items-center">
            <img
                src={recruiter.imageUrl}
                alt="logo"
                className="size-32 border border-gray-50 dark:border-gray-700 rounded-xl shadow-lg"
            />
            <Link
                to={"/profile/" + recruiter.id}
                className="text-lg font-semibold hover:text-brightOrange"
            >
                {recruiter.fullName}
            </Link>
            {/*<div className="flex gap-3 text-md">*/}
            {/*    <p>*/}
            {/*        {tGlobal("common.recruiter.post")}:{" "}*/}
            {/*        <span className="font-semibold">{*/}
            {/*            recruiter.jobPostingNumber}</span>*/}
            {/*    </p>*/}
            {/*    <p>*/}
            {/*        {tGlobal("common.recruiter.recruiting")}:{" "}*/}
            {/*        <span className="text-brightOrange font-semibold">*/}
            {/*            {recruiter.followerNumber}*/}
            {/*        </span>*/}
            {/*    </p>*/}
            {/*</div>*/}
            <div className="flex gap-3 text-md">
                <p>
                    {/*{tGlobal("common.recruiter.post")}:{" "}*/}
                    Bài đăng: {" "}
                    <span className="font-semibold">{
                        recruiter.jobPostingNumber}</span>
                </p>
                <p>
                    {/*{tGlobal("common.recruiter.recruiting")}:{" "}*/}
                    Đang theo dõi: {" "}
                    <span className="text-brightOrange font-semibold">
                        {recruiter.followerNumber}
                    </span>
                </p>
            </div>
        </div>
    );
};

export default RecruiterCard;
