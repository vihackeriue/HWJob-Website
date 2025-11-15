import { FaFileAlt, FaHome, FaLock, FaPen, FaUser } from "react-icons/fa";
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

  {
    key: "recruiter",
    label: "user.navbar.recruiter",
    path: "/recruiter",
  },
];

export const NAVBAR_RECRUITER_LINKS = [
  {
    key: "jobPost",
    label: "user.navbar.addJobPost",
    path: "/add-job-post",
  },
];

export const DROPDOWN_USER_LINKS = [
  {
    key: "profile",
    label: "user.navbar.profile",
    path: "/profile",
  },
  {
    key: "setting",
    label: "user.navbar.setting",
    path: "/setting",
  },
];

export const PROFILE_USER_MENUS = [
  {
    key: "overview",
    label: "Tổng quan",
    path: "/my-profile",
    icon: <FaHome />,
  },
  {
    key: "personal",
    label: "Thông tin cá nhân",
    path: "/my-profile/personal",
    icon: <FaUser />,
  },
  {
    key: "security",
    label: "Thông tin bảo mật",
    path: "/my-profile/security",
    icon: <FaLock />,
  },
  {
    key: "summary",
    label: "Tóm tắt bản thân",
    path: "/my-profile/edit-summary",
    icon: <FaPen />,
  },
  { key: "cv", label: "CV", path: "", icon: <FaFileAlt /> },
];

export const ADMIN_SIDEBAR_LINKS = [
  {
    key: "user",
    label: "admin.sidebar.user",
    path: "/admin/user",
    icon: <HiOutlineCube />,
  },
  {
    key: "category",
    label: "admin.sidebar.category",
    path: "/admin/category",
    icon: <HiOutlineCube />,
  },

  {
    key: "badge",
    label: "admin.sidebar.badge",
    path: "/admin/badge",
    icon: <HiOutlineCube />,
  },
];

export const ADMIN_SIDEBAR_FOOTER_LINKS = [
  {
    key: "settings",
    label: "Settings",
    path: "/admin/settings",
    icon: <HiOutlineCog />,
  },
  {
    key: "support",
    label: "Support",
    path: "/admin/support",
    icon: <HiOutlineQuestionMarkCircle />,
  },
];
