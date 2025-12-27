import { Route, Routes } from "react-router-dom";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "react-toastify/dist/ReactToastify.css";

import Login from "./pages/auth/Login";
import UserLayout from "./components/layouts/user/UserLayout";
import Home from "./pages/user/Home";
import Register from "./pages/auth/Register";
import PrivateRoute from "./components/common/PrivateRoute";
import Dashboard from "./pages/admin/Dashboard";
import AdminLayout from "./components/layouts/admin/AdminLayout";

import CategoryManagement from "./pages/admin/CategoryManagement";
import BadgeManagement from "./pages/admin/BadgeManagement";
import UserManagement from "./pages/admin/UserManagement";
import AddJobPost from "./pages/user/recruiter/AddJobPost";
import JobPostList from "./pages/user/JobPostList";
import JobPostDetail from "./pages/user/JobPostDetail";

import CandidateJobManagement from "./pages/user/candidate/CandidateJobManagement";
import { ToastContainer } from "react-toastify";
import RecruiterJobManagement from "./pages/user/recruiter/RecruiterJobManagement";
import RecruiterJobPostDetail from "./pages/user/recruiter/RecruiterJobPostDetail";

import { ROLES } from "./constants/roles";
import PaymentResult from "./pages/user/test/PaymentResult.jsx";
import MyProfile from "./pages/user/shared/MyProfile.jsx";
import TransactionManagement from "./pages/user/shared/TransactionManagement.jsx";

function App() {
  return (
    <>
      <Routes>
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />

        <Route path="/" element={<UserLayout />}>
          <Route index element={<Home />} />
          <Route path="job-post" element={<JobPostList />} />
          <Route path="job-post/:id" element={<JobPostDetail />} />
          <Route
            element={
              <PrivateRoute allowedRoles={[ROLES.RECRUITER, ROLES.CANDIDATE]} />
            }
          >
            <Route path="my-profile" element={<MyProfile />} />
            <Route path="transaction" element={<TransactionManagement />} />
            <Route path="payment-result" element={<PaymentResult />} />
          </Route>
          {/* Role Recruiter */}
          <Route
            path="recruiter"
            element={<PrivateRoute allowedRoles={ROLES.RECRUITER} />}
          >
            <Route path="add-job-post" element={<AddJobPost />} />
            <Route path="manage-job" element={<RecruiterJobManagement />} />
            <Route path="job-post/:id" element={<RecruiterJobPostDetail />} />
          </Route>
          {/* Role Candidate */}
          <Route
            path="candidate"
            element={<PrivateRoute allowedRoles={ROLES.CANDIDATE} />}
          >
            <Route path="manage-job" element={<CandidateJobManagement />} />
          </Route>
        </Route>

        <Route path="admin" element={<AdminLayout />}>
          <Route index element={<Dashboard />} />
          <Route path="category" element={<CategoryManagement />} />
          <Route path="badge" element={<BadgeManagement />} />
          <Route path="user" element={<UserManagement />} />
        </Route>

        <Route element={<PrivateRoute allowedRoles={ROLES.ADMIN} />}></Route>
      </Routes>
      <ToastContainer
        position="top-right"
        autoClose={2000}
        hideProgressBar={false}
        closeOnClick
        pauseOnHover
        draggable
        stacked // <--- Toast xếp chồng đẹp
        newestOnTop={false} // <--- Toast mới nằm trên cùng
        limit={5} // <--- Giới hạn 5 toast một lúc
      />
    </>
  );
}

export default App;
