import React, {useEffect, useState} from "react";
import BannerSection from "../../components/sections/BannerSection";
import {TopJobSection} from "../../components/sections/TopJobSection";

import {useTranslation} from "react-i18next";

import FeatureSection from "../../components/sections/FeatureSection";
import TopRecruiterSection from "../../components/sections/TopRecruiterSection";
import IntroduceRecruiterSection from "../../components/sections/IntroduceRecruiterSection";
import SuggestedJobSection from "../../components/sections/SuggestedJobSection";
import SearchBar from "../../components/ui/SearchBar";
import {useList} from "../../hooks/useList.jsx";
import {getTop10RecommendJobPosts, getTopRecruiters} from "../../services/homeService.jsx";
import {getJobPosts} from "../../services/jobPostService.jsx";
import useAuth from "../../hooks/useAuth.jsx";
import {ROLES} from "../../constants/roles.jsx";

export default function Home() {
    const {t} = useTranslation();

    const {auth} = useAuth();

    const {data: recruiters} = useList(getTopRecruiters);
    // const {data: recommendJobPosts} =useList()
    // const recommendJobPosts = useList(getTop10RecommendJobPosts);

    const [recommendJobPosts, setRecommendJobPosts] = useState([]);

    useEffect(() => {
        if (auth?.roles?.includes(ROLES.CANDIDATE)) {
            getTop10RecommendJobPosts().then((res) => {
                setRecommendJobPosts(res.result);
            });
        }
    }, [auth]);

    const jobPosts = useList(getJobPosts);

    // const industries = [];

    return (
        <div>
            <div className="w-screen relative left-1/2 right-1/2 -translate-x-1/2">
                <BannerSection/>
                <div
                    className="absolute left-1/2 -translate-x-1/2 -translate-y-1/2 shadow-lg hover:shadow-2xl rounded-2xl">
                    <SearchBar/>
                </div>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-5 justify-between mt-20">
                <div className="flex flex-col gap-3">
                    {/*<TopIndustrySection t={t} industries={industries}/>*/}
                    {auth?.roles?.includes(ROLES.CANDIDATE) && (
                        <SuggestedJobSection t={t} jobPosts={recommendJobPosts}/>
                    )}
                </div>
                <TopJobSection jobPosts={jobPosts.data} t={t}/>
            </div>
            <IntroduceRecruiterSection t={t}/>
            <TopRecruiterSection recruiters={recruiters} t={t}/>
            <FeatureSection t={t}/>
            <div>hotline</div>
        </div>
    );
}
