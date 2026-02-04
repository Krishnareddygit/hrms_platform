import { Routes, Route } from "react-router-dom";
import MainLayout from "../Layout/MainLayout";
import ProfileLayout from "../Pages/Profile/ProfileLayout";
import TimeLayout from "../Layout/FormLayout";
import RequestForm from "../Layout/LeaveRequest";
import Docs from "../Pages/Documents/Docs";

import Overview from '../Pages/Profile/Overview'
import PersonalDetails from "../Pages/Profile/PersonalDetails";
import Organization from "../Pages/Profile/OrganizationChart"


import TimeOverview from "../Pages/TimeManagement/Overview";
import Attendance from "../Pages/TimeManagement/Attendance";
import Leave from "../Pages/TimeManagement/Leave";


const AppRoutes = () => {
  return (
    <Routes>
      <Route element={<MainLayout />}>

        <Route path="/" element={<ProfileLayout />}>
          <Route index element={<Overview />} />
          <Route path="/personal" element={<PersonalDetails />} />
          <Route path="/organization" element={<Organization />} />
        </Route>

        <Route path="/documents" element={<Docs />}></Route>

        <Route path="/time">
        <Route path="/time" element={<TimeLayout />}>
          <Route index element={<TimeOverview />} />
          <Route path="request" element={<RequestForm />} />
        </Route>
          <Route path="attendance" element={<Attendance />} />
          <Route path="leave" element={<Leave />} />
        </Route>

      </Route>
    </Routes>
  );
};

export default AppRoutes;
