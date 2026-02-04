import Chart from "./Chart";
import { useNavigate } from "react-router";

const TimeOverview = () => {

  const navigate = useNavigate();

    return (
      <div className="container-fluid">

        {/* Overview Card */}
      <div className="card mb-3 p-3">
        <div className="d-flex justify-content-between align-items-center">
          <h5 className="mb-0">Overview</h5>

          <button
            className="btn btn-primary btn-sm"
            onClick={() => navigate("/time/request")}
          >
            + Request
          </button>
        </div>
      </div>

    
        {/* ROW 1 */}
        <div className="row g-4">
  
          {/* Pending Requests */}
          <div className="col-lg-8">
            <div className="card h-100">
              <div className="card-body text-center">
                <h6 className="fw-semibold text-start">Pending Requests</h6>
  
                <div className="my-4">
                  <p className="text-muted">
                    There are no pending requests
                  </p>
                </div>
              </div>
            </div>
          </div>
  
          {/* Clock In */}
          <div className="col-lg-4">
            <div className="card h-100">
              <div className="card-body">
                <h6 className="fw-semibold">Let's Get to Work</h6>
  
                <div className="d-flex justify-content-between mt-3">
                  <span className="text-muted">02 Feb, Monday</span>
                  <strong>00:00:00</strong>
                </div>
  
                <small className="text-muted d-block mt-2">
                  Shift: 10:00 - 19:00
                </small>
  
                <button className="btn btn-primary w-100 mt-4">
                  Clock In
                </button>
              </div>
            </div>
          </div>
  
        </div>
  
        {/* ROW 2 */}
        <div className="row g-4 mt-1">
  
          {/* Attendance Metrics */}
          <div className="col-lg-6">
            <div className="card h-100">
              <div className="card-body"
              style={{
                background: "linear-gradient(to right, #e7f1ff, #f2e9ff)"
              }}
              >
                <h6 className="fw-semibold">Attendance Metrics</h6>
  
                <div className="row text-center mt-2">
                  <div className="col">
                    <h6>09:00</h6>
                    <small className="text-muted">Avg Work Duration</small>
                  </div>
                  <div className="col">
                    <h6>00:00</h6>
                    <small className="text-muted">Avg Late By</small>
                  </div>
                  <div className="col">
                    <h6>00:00</h6>
                    <small className="text-muted">Overtime</small>
                  </div>
                </div>
                  <div>
                  <Chart /> 
                  </div>
  
                <div className="text-muted text-center mt-4">
                  Attendance calendar summary
                </div>
              </div>
            </div>
          </div>
  
          {/* Leave Balances */}
          <div className="col-lg-3">
            <div className="card h-100">
              <div className="card-body">
                <div className="d-flex justify-content-between">
                  <h6 className="fw-semibold">Leave Balances</h6>
                  <small className="text-primary">View All</small>
                </div>
  
                <ul className="list-group list-group-flush mt-3">
                  <li className="list-group-item d-flex justify-content-between">
                    Bereavement <b>3</b>
                  </li>
                  <li className="list-group-item d-flex justify-content-between">
                    Casual <b>2</b>
                  </li>
                  <li className="list-group-item d-flex justify-content-between">
                    Earned <b>1.5</b>
                  </li>
                  <li className="list-group-item d-flex justify-content-between">
                    Election <b>1</b>
                  </li>
                  <li className="list-group-item d-flex justify-content-between">
                    Paternity <b>5</b>
                  </li>
                  <li className="list-group-item d-flex justify-content-between">
                    Unpaid <b>0</b>
                  </li>
                </ul>
              </div>
            </div>
          </div>
  
          {/* Upcoming Holidays */}
          <div className="col-lg-3">
            <div className="card h-100">
              <div className="card-body">
                <div className="d-flex justify-content-between">
                  <h6 className="fw-semibold">Upcoming Time Off</h6>
                  <small className="text-primary">View All</small>
                </div>
  
                <ul className="list-group list-group-flush mt-3">
                  <li className="list-group-item">
                    <b>01 May</b> – May Day
                  </li>
                  <li className="list-group-item">
                    <b>04 Sep</b> – Krishna Janmashtami
                  </li>
                  <li className="list-group-item">
                    <b>20 Oct</b> – Dussehra
                  </li>
                  <li className="list-group-item">
                    <b>09 Nov</b> – Diwali
                  </li>
                </ul>
              </div>
            </div>
          </div>
  
        </div>
      </div>
    );
  };
  
  export default TimeOverview;
  