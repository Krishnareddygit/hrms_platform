import { useState } from "react";
import LeaveBalance from './LeaveBalance';
import HolidaysList from './HolidaysList';
import LeaveHistory from './LeaveHistory';
import { useNavigate } from "react-router";

const Leave = () => {
  const [activeTab, setActiveTab] = useState("balance");
  const navigate = useNavigate();

  return (
    <div className="container-fluid">

      {/* ================= HEADER ================= */}
      <div className="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
        <h5 className="mb-0 fw-semibold">Leave</h5>

        <button
            className="btn btn-primary btn-sm"
            onClick={() => navigate("/time/request")}
          >
            + Request
          </button>
      </div>

      {/* ================= TABS ================= */}
      <ul className="nav nav-tabs mb-3">
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "balance" ? "active" : ""}`}
            onClick={() => setActiveTab("balance")}
          >
            Leave Balance
          </button>
        </li>

        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "holidays" ? "active" : ""}`}
            onClick={() => setActiveTab("holidays")}
          >
            Holidays List
          </button>
        </li>

        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "history" ? "active" : ""}`}
            onClick={() => setActiveTab("history")}
          >
            History
          </button>
        </li>
      </ul>

      {/* ================= TAB CONTENT ================= */}
      <div>
        {activeTab === "balance" && <LeaveBalance />}
        {activeTab === "holidays" && <HolidaysList />}
        {activeTab === "history" && <LeaveHistory />}
      </div>

    </div>
  );
};

export default Leave;
