import useMyInfo from "../../hooks/user/useMyProfile.jsx";
import {useEffect, useState} from "react";
import {TabGroup, TabPanel, TabPanels} from "@headlessui/react";
import ProfileCard from "../../components/ui/cards/ProfileCard.jsx";
import ProgressBar from "../../components/ui/ProgressBar.jsx";
import MenuTabListVertical from "../../components/ui/MenuTabListVertical.jsx";
import PersonalInfoSection from "../../components/sections/profile/PersonalInfoSection.jsx";
import {PROFILE_USER_MENU} from "../../constants/navigation.jsx";
import UpdateInfoSection from "../../components/sections/profile/UpdateInfoSection.jsx";

const MyProfile = () => {

    const {data, loading, refetch} = useMyInfo();
    const [myInfo, setMyInfo] = useState();
    useEffect(() => {
        if (data) setMyInfo(data);
    }, [data]);
    if (loading || !myInfo) return <div>Đang tải...</div>;

    return (
        <TabGroup>
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 my-5">
                <div className="flex flex-col gap-3 col-span-1">
                    <ProfileCard user={myInfo}/>
                    <div className="bg-white dark:bg-stoneBrown-900 p-2 rounded-2xl">
                        <ProgressBar
                            title={"Mức độ hòa thiện hồ sơ"}
                            value={myInfo.profileCompletion}
                        />
                    </div>

                    <MenuTabListVertical
                        menus={PROFILE_USER_MENU}
                        title={"Quản lý"}
                    />
                </div>
                <TabPanels className="col-span-2  flex flex-col gap-3 ">
                    <TabPanel>
                        <PersonalInfoSection personalInfo={myInfo}/>
                    </TabPanel>
                    <TabPanel>
                        <UpdateInfoSection
                            personalInfo={myInfo}
                            onUpdated={refetch}
                        />
                    </TabPanel>
                    <TabPanel>
                        <h1>Quản lý CV</h1>
                    </TabPanel>
                </TabPanels>
            </div>
        </TabGroup>
    );
}

export default MyProfile;