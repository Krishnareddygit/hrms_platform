import { Outlet, useLocation, useNavigate } from "react-router-dom";

const TimeLayout = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const showOffcanvas = location.pathname.endsWith("/request");

  return (
    <div className="position-relative">

      {/* Main Page */}
      <Outlet />

      {/* Offcanvas */}
      <div
        className={`offcanvas offcanvas-end ${showOffcanvas ? "show" : ""}`}
        style={{
          visibility: showOffcanvas ? "visible" : "hidden",
          width: "400px",
          backgroundColor: "#fff"
        }}
      >
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">Request</h5>
          <button
            className="btn-close"
            onClick={() => navigate("/time")}
          ></button>
        </div>

        <div className="offcanvas-body">
          <Outlet />
        </div>
      </div>

      {/* Backdrop */}
      {showOffcanvas && (
        <div
          className="offcanvas-backdrop fade show"
          onClick={() => navigate("/time")}
        ></div>
      )}
    </div>
  );
};

export default TimeLayout;
