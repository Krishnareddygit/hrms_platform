import Profile from '../Images/OIP.jpg'

import { NavLink } from "react-router-dom";
import { useState } from "react";

const Sidebar = () => {
  const [openMenu, setOpenMenu] = useState("profile");

  const toggleMenu = (menu) => {
    setOpenMenu(openMenu === menu ? null : menu);
  };

  return (
    <div
      className="bg-white border-end"
      style={{ width: "260px", minHeight: "100vh" }}
    >
      {/* TOP PROFILE SUMMARY (UI ONLY) */}
      <div className="text-center py-4 border-bottom px-3 py-3">
        <img
          src={Profile}
          alt="profile"
          className="rounded-circle mb-2"
          width="80"
          height="80"
        />
        <div className="fw-bold text-truncate px-3">
          Nallamilli S G B Rama Krishna Reddy
        </div>
        <div className="text-muted small">Software Engineer</div>
        <div className="text-muted small">Corporate</div>
        <div className="text-muted small">10087</div>
      </div>

      {/* MENU */}
      <div className="px-3 py-3">

        {/* PROFILE */}
        <div
          className="d-flex justify-content-between align-items-center fw-semibold mb-2"
          style={{ cursor: "pointer" }}
          onClick={() => toggleMenu("profile")}
        >
          <div className="d-flex align-items-center gap-2">
            <i className="bi bi-person"></i>
            <span>Profile</span>
          </div>
          <i className={`bi ${openMenu === "profile" ? "bi-chevron-down" : "bi-chevron-right"}`} />
        </div>

        {openMenu === "profile" && (
          <ul className="nav flex-column ms-4 mb-3">
            <NavLink to="/" end className="nav-link p-1 fw-semibold text-primary">
              Overview
            </NavLink>
            <NavLink to="/personal" className="nav-link p-1">
              Personal Details
            </NavLink>
            <NavLink to="/organization" className="nav-link p-1">
              Organization Details
            </NavLink>
          </ul>
        )}

        {/* DOCUMENTS */}
        <NavLink
          to="/documents"
          className="nav-link fw-semibold d-flex align-items-center gap-2 mb-3"
        >
          <i className="bi bi-file-earmark-text"></i>
          Documents
        </NavLink>

        {/* TIME MANAGEMENT */}
        <div
          className="d-flex justify-content-between align-items-center fw-semibold mb-2"
          style={{ cursor: "pointer" }}
          onClick={() => toggleMenu("time")}
        >
          <div className="d-flex align-items-center gap-2">
            <i className="bi bi-clock"></i>
            <span>Time Management</span>
          </div>
          <i className={`bi ${openMenu === "time" ? "bi-chevron-down" : "bi-chevron-right"}`} />
        </div>

        {openMenu === "time" && (
          <ul className="nav flex-column ms-4">
            <NavLink to="/time" end className="nav-link p-1 fw-semibold text-primary">
              Overview
            </NavLink>
            <NavLink to="/time/attendance" className="nav-link p-1">
              Attendance
            </NavLink>
            <NavLink to="/time/leave" className="nav-link p-1">
              Leave
            </NavLink>
          </ul>
        )}
      </div>
    </div>
  );
};

export default Sidebar;
