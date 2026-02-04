const LeaveBalance = () => {
    const balances = [
      {
        name: "Bereavement",
        available: 3,
        accrued: 2
      },
      {
        name: "Casual Leave",
        available: 2,
        accrued: 1.5
      },
      {
        name: "Earned Leave",
        available: 1.5,
        accrued: 1
      },
      {
        name: "Election Leave",
        available: 1,
        accrued: 1
      },
      {
        name: "Paternity Leave",
        available: 5,
        accrued: 3
      },
      {
        name: "Unpaid Leave",
        available: "-",
        accrued: "-"
      }
    ];
  
    return (
      <div className="container-fluid p-0">
  
        {/* HEADER */}
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h6 className="fw-semibold mb-0">Leave Balances</h6>
          <button className="btn btn-link btn-sm text-decoration-none">
            View All
          </button>
        </div>
  
        {/* LEAVE CARDS */}
        <div className="row g-3">
          {balances.map((item, index) => (
            <div
              className="col-xl-2 col-lg-3 col-md-4 col-sm-6"
              key={index}
            >
              <div className="card h-100 shadow-sm">
                <div className="card-body text-center">
  
                  {/* LEAVE TYPE */}
                  <p className="fw-semibold mb-3">
                    {item.name}
                  </p>
  
                  {/* AVAILABLE */}
                  <div className="mb-2">
                    <h2 className="fw-bold mb-0">
                      {item.available}
                    </h2>
                    <small className="text-muted">
                      Available
                    </small>
                  </div>
  
                  {/* ACCRUED */}
                  <div>
                    <small className="text-muted">
                      Accrued so far:{" "}
                      <strong>{item.accrued}</strong>
                    </small>
                  </div>
  
                </div>
              </div>
            </div>
          ))}
        </div>
  
      </div>
    );
  };
  
  export default LeaveBalance;
  