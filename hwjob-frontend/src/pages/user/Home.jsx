import React, { useEffect, useState } from "react";
import BannerSection from "../../components/sections/BannerSection";
import { TopJobSection } from "../../components/sections/TopJobSection";
import { useTranslation } from "react-i18next";

import FeatureSection from "../../components/sections/FeatureSection";
import TopRecruiterSection from "../../components/sections/TopRecruiterSection";
import IntroduceRecruiterSection from "../../components/sections/IntroduceRecruiterSection";
import SuggestedJobSection from "../../components/sections/SuggestedJobSection";
import SearchBar from "../../components/ui/SearchBar";
import { useList } from "../../hooks/useList.jsx";
import {
  getTop10RecommendJobPosts,
  getTopRecruiters,
} from "../../services/homeService.jsx";
import {
  getJobPosts,
  getTop12BoostJobPost,
} from "../../services/jobPostService.jsx";
import useAuth from "../../hooks/useAuth.jsx";
import { ROLES } from "../../constants/roles.jsx";

export default function Home() {
  const { t } = useTranslation();

  const { auth } = useAuth();

  const { data: recruiters } = useList(getTopRecruiters);

  const [recommendJobPosts, setRecommendJobPosts] = useState([]);

  useEffect(() => {
    if (auth?.roles?.includes(ROLES.CANDIDATE)) {
      getTop10RecommendJobPosts().then((res) => {
        setRecommendJobPosts(res.result);
      });
    }
  }, [auth]);

  const jobPosts = useList(getJobPosts);

  const top12BoostJobPost = useList(getTop12BoostJobPost);

  return (
    <div>
      {/* Banner & Search Bar */}
      <div className="w-screen relative left-1/2 right-1/2 -translate-x-1/2">
        <BannerSection />
        <div className="absolute left-1/2 -translate-x-1/2 -translate-y-1/2 shadow-lg hover:shadow-2xl rounded-2xl">
          <SearchBar />
        </div>
      </div>

      {/* Main Content - Xếp theo chiều dọc, mỗi section là một hàng ngang */}
      <div className="flex flex-col gap-10 mt-24 mb-10">
        {/* 2. Tin tuyển dụng nổi bật */}
        <section className="container mx-auto px-4">
          {/* Truyền jobPosts.data thay vì toàn bộ object jobPosts */}
          <TopJobSection jobPosts={top12BoostJobPost.data} t={t} />
        </section>
        {/* 1. Nhà tuyển dụng hàng đầu */}
        <section>
          <TopRecruiterSection recruiters={recruiters} t={t} />
        </section>

        {/* 3. Recommend Job (Chỉ hiển thị cho Candidate) */}
        {auth?.roles?.includes(ROLES.CANDIDATE) && (
          <section className="container mx-auto px-4">
            <SuggestedJobSection t={t} jobPosts={recommendJobPosts} />
          </section>
        )}

        {/* Các phần khác */}
        <IntroduceRecruiterSection t={t} />
        {/* <FeatureSection t={t} /> */}
      </div>
    </div>
  );
}
