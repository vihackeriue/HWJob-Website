import React, {Suspense, lazy} from "react";
import {Route, Routes} from "react-router-dom";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "react-toastify/dist/ReactToastify.css";
import {ToastContainer} from "react-toastify";

import UserLayout from "./components/layouts/user/UserLayout";
import AdminLayout from "./components/layouts/admin/AdminLayout";
import PrivateRoute from "./components/common/PrivateRoute";
import ChatFloatingUI from "./components/ui/ChatFloatingUI.jsx";
import {ROLES} from "./constants/roles";

// Lazy load pages
const Login = lazy(() => import("./pages/auth/Login"));
const Register = lazy(() => import("./pages/auth/Register"));
const Home = lazy(() => import("./pages/user/Home"));
const JobPostList = lazy(() => import("./pages/user/JobPostList"));
const JobPostDetail = lazy(() => import("./pages/user/JobPostDetail"));
const PublicProfile = lazy(() => import("./pages/user/./RecruiterPublicProfile"));
const MyProfile = lazy(() => import("./pages/user/shared/MyProfile.jsx"));
const TransactionManagement = lazy(() => import("./pages/user/shared/TransactionManagement.jsx"));
const PaymentResult = lazy(() => import("./pages/user/shared/PaymentResult.jsx"));

// Recruiter Pages
const AddJobPost = lazy(() => import("./pages/user/recruiter/AddJobPost"));
const RecruiterJobManagement = lazy(() => import("./pages/user/recruiter/RecruiterJobManagement"));
const RecruiterJobPostDetail = lazy(() => import("./pages/user/recruiter/RecruiterJobPostDetail"));

// Candidate Pages
const CandidateJobManagement = lazy(() => import("./pages/user/candidate/CandidateJobManagement"));

// Admin Pages
const Dashboard = lazy(() => import("./pages/admin/Dashboard"));
const CategoryManagement = lazy(() => import("./pages/admin/CategoryManagement"));
const BadgeManagement = lazy(() => import("./pages/admin/BadgeManagement"));
const UserManagement = lazy(() => import("./pages/admin/UserManagement"));

// Loading Component
const LoadingFallback = () => (
    <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-teal-600"></div>
    </div>
);

function App() {
    return (
        <>
            <Suspense fallback={<LoadingFallback/>}>
                <Routes>
                    <Route path="login" element={<Login/>}/>
                    <Route path="register" element={<Register/>}/>

                    <Route path="/" element={<UserLayout/>}>
                        <Route index element={<Home/>}/>
                        <Route path="job-post" element={<JobPostList/>}/>
                        <Route path="job-post/:id" element={<JobPostDetail/>}/>
                        <Route path="profile/:id" element={<PublicProfile/>}/>
                        <Route
                            element={
                                <PrivateRoute allowedRoles={[ROLES.RECRUITER, ROLES.CANDIDATE]}/>
                            }
                        >
                            <Route path="my-profile" element={<MyProfile/>}/>
                            <Route path="transaction" element={<TransactionManagement/>}/>
                            <Route path="payment-result" element={<PaymentResult/>}/>
                        </Route>
                        {/* Role Recruiter */}
                        <Route
                            path="recruiter"
                            element={<PrivateRoute allowedRoles={ROLES.RECRUITER}/>}
                        >
                            <Route path="add-job-post" element={<AddJobPost/>}/>
                            <Route path="manage-job" element={<RecruiterJobManagement/>}/>
                            <Route path="job-post/:id" element={<RecruiterJobPostDetail/>}/>
                        </Route>
                        {/* Role Candidate */}
                        <Route
                            path="candidate"
                            element={<PrivateRoute allowedRoles={ROLES.CANDIDATE}/>}
                        >
                            <Route path="manage-job" element={<CandidateJobManagement/>}/>
                        </Route>
                    </Route>

                    <Route path="admin" element={<AdminLayout/>}>
                        <Route index element={<Dashboard/>}/>
                        <Route path="category" element={<CategoryManagement/>}/>
                        <Route path="badge" element={<BadgeManagement/>}/>
                        <Route path="user" element={<UserManagement/>}/>
                    </Route>

                    <Route element={<PrivateRoute allowedRoles={ROLES.ADMIN}/>}></Route>
                </Routes>
            </Suspense>

            <ToastContainer
                position="top-right"
                autoClose={2000}
                hideProgressBar={false}
                closeOnClick
                pauseOnHover
                draggable
                stacked
                newestOnTop={false}
                limit={5}
            />
            <ChatFloatingUI/>
        </>
    );
}

export default App;
