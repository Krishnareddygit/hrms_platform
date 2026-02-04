import { useNavigate } from "react-router-dom";

const RequestForm = () => {
  const navigate = useNavigate();

  return (
    <form>
      <div className="mb-3">
        <label className="form-label">Leave Type</label>
        <select className="form-select">
            <option>Unpaid</option>
          <option>Casual Leave</option>
          <option>Bereavement</option>
          <option>Earned</option>
          <option>Election</option>
          <option>Paternity</option>
        </select>
      </div>

      <div className="mb-3">
      <label className="form-label">From Date</label>
      <input
        type="date"
        className="form-control"
      />
    </div>

    {/* To Date */}
    <div className="mb-3">
      <label className="form-label">To Date</label>
      <input
        type="date"
        className="form-control"
      />
    </div>
    <div className="mb-3">
      <label className="form-label">Half day Leave</label>
        <select className="form-select">
            <option>NA</option>
            <option>Yes</option>
        </select>
        </div>
      <div className="mb-3">
        <label className="form-label">Reason</label>
        <textarea className="form-control" rows="3"></textarea>
      </div>

      <button
        type="button"
        className="btn btn-success w-100"
        onClick={() => navigate("/time")}
      >
        Submit
      </button>
    </form>
  );
};

export default RequestForm;
