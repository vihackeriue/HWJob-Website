import React from "react";
import BannerSection from "../../components/sections/BannerSection";
import { BestJobSection } from "../../components/sections/BestJobSection";
import TopIndustrySection from "../../components/sections/TopIndustrySection";
import ResumeIntroductionSection from "../../components/sections/ResumeIntroductionSection";
import { useTranslation } from "react-i18next";
import BadgeIntroductionSection from "../../components/sections/BadgeIntroductionSection";

export default function Home() {
  const { t } = useTranslation();
  return (
    <div>
      <div className="w-screen relative left-1/2 right-1/2 -translate-x-1/2">
        <BannerSection />
        <div className="w-1/2 bg-amber-600 absolute left-1/2 -translate-x-1/2 -translate-y-1/2 p-3 rounded-full text-white font-semibold shadow-lg hover:shadow-2xl cursor-pointer">
          Search
        </div>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-5 justify-between mt-10">
        <BestJobSection />
        <TopIndustrySection />
      </div>

      <div>Top recruiters</div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-5 justify-between mt-10">
        <ResumeIntroductionSection content={t} />
        <BadgeIntroductionSection content={t} />
      </div>
    </div>
  );
}
