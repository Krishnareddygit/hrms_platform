const holidays = [
    {
      date: "2026-01-26",
      occasion: "Republic Day",
      day: "Monday",
      type: "National"
    },
    {
      date: "2026-03-08",
      occasion: "Holi",
      day: "Sunday",
      type: "Optional"
    },
    {
      date: "2026-05-01",
      occasion: "May Day",
      day: "Friday",
      type: "Holiday"
    },
    {
      date: "2026-06-07",
      occasion: "Bakrid (Eid al-Adha)",
      day: "Sunday",
      type: "Optional"
    },
    {
      date: "2026-08-15",
      occasion: "Independence Day",
      day: "Saturday",
      type: "National"
    },
    {
      date: "2026-08-27",
      occasion: "Krishna Janmashtami",
      day: "Thursday",
      type: "Optional"
    },
    {
      date: "2026-09-18",
      occasion: "Ganesh Chaturthi",
      day: "Friday",
      type: "Holiday"
    },
    {
      date: "2026-10-22",
      occasion: "Dussehra (Vijaya Dashami)",
      day: "Thursday",
      type: "Holiday"
    },
    {
      date: "2026-11-08",
      occasion: "Diwali",
      day: "Sunday",
      type: "Holiday"
    },
    {
      date: "2026-10-02",
      occasion: "Gandhi Jayanti",
      day: "Friday",
      type: "National"
    },
    {
      date: "2026-12-25",
      occasion: "Christmas",
      day: "Friday",
      type: "National"
    }
  ];  

  const HolidayList = () => {
    return (
      <div className="card mt-3">
        <div className="card-body">
          <h6 className="fw-semibold mb-3">Holiday List</h6>
  
          <div className="table-responsive">
            <table className="table table-bordered table-hover align-middle">
              <thead className="table-light">
                <tr>
                  <th>Date</th>
                  <th>Occasion</th>
                  <th>Day</th>
                  <th>Holiday Type</th>
                </tr>
              </thead>
  
              <tbody>
                {holidays.map((holiday, index) => (
                  <tr key={index}>
                    <td>{holiday.date}</td>
                    <td>{holiday.occasion}</td>
                    <td>{holiday.day}</td>
                    <td>
                      <span
                        className={`badge ${
                          holiday.type === "National"
                            ? "bg-danger"
                            : holiday.type === "Optional"
                            ? "bg-warning text-dark"
                            : "bg-success"
                        }`}
                      >
                        {holiday.type}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
  
            </table>
          </div>
        </div>
      </div>
    );
  };
  
  export default HolidayList;
  