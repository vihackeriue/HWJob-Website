import {FaFileAlt, FaHome, FaLock, FaPen, FaUser} from "react-icons/fa";
import {
    HiOutlineCog,
    HiOutlineCube,
    HiOutlineQuestionMarkCircle,
    HiOutlineViewGrid,
} from "react-icons/hi";

export const NAVBAR_USER_LINKS = [
    {
        key: "home",
        label: "user.navbar.home",
        path: "/",
    },
    {
        key: "jobPost",
        label: "user.navbar.jobPost",
        path: "/job-post",
    },
];
export const NAVBAR_CANDIDATE_LINKS = [
    {
        key: "manageJob",
        label: "user.navbar.manageJobCandidate",
        path: "/candidate/manage-job",
    },
];
export const NAVBAR_RECRUITER_LINKS = [
    {
        key: "jobPost",
        label: "user.navbar.addJobPost",
        path: "/recruiter/add-job-post",
    },
    {
        key: "manageJob",
        label: "user.navbar.manageJobRecruiter",
        path: "/recruiter/manage-job",
    },
];

export const DROPDOWN_USER_LINKS = [
    {
        key: "profile",
        label: "user.navbar.profile",
        path: "/my-profile",
    },
    {
        key: "setting",
        label: "user.navbar.setting",
        path: "/setting",
    },
];

export const PROFILE_USER_MENU = [
    {
        key: "personal",
        label: "Thông tin cá nhân",
        path: "/my-profile/personal",
        icon: <FaUser/>,
    },
    {
        key: "update-info",
        label: "Cập nhật thông tin",
        path: "/my-profile/update-info",
        icon: <FaLock/>,
    },
    {
        key: "cv",
        label: "Quản lý CV",
        path: "", icon: <FaFileAlt/>
    },
];

export const ADMIN_SIDEBAR_LINKS = [
    {
        key: "user",
        label: "admin.sidebar.user",
        path: "/admin/user",
        icon: <HiOutlineCube/>,
    },
    {
        key: "category",
        label: "admin.sidebar.category",
        path: "/admin/category",
        icon: <HiOutlineCube/>,
    },

    {
        key: "badge",
        label: "admin.sidebar.badge",
        path: "/admin/badge",
        icon: <HiOutlineCube/>,
    },
];

export const ADMIN_SIDEBAR_FOOTER_LINKS = [
    {
        key: "settings",
        label: "Settings",
        path: "/admin/settings",
        icon: <HiOutlineCog/>,
    },
    {
        key: "support",
        label: "Support",
        path: "/admin/support",
        icon: <HiOutlineQuestionMarkCircle/>,
    },
];
