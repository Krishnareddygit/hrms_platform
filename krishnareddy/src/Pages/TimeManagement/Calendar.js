import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";

const AttendanceCalendar = () => {
  const events = [
    {
      title: "Present",
      date: "2026-02-03",
      backgroundColor: "#0d6efd",
      textColor: "#fff",
    },
    {
      title: "Holiday - Republic Day",
      date: "2026-01-26",
      backgroundColor: "#f8d7da",
      textColor: "#842029",
      borderColor: "#dc3545",
    },
  ];

  return (
    <div className="card">
      <div className="card-body">
        <h6 className="fw-semibold mb-3">Attendance</h6>

        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin]}
          initialView="dayGridMonth"
          headerToolbar={{
            left: "prev,next today",
            center: "title",
            right: "",
          }}
          height="auto"
          events={events}

          /* 👇 THIS IS THE KEY PART */
          dayCellDidMount={(info) => {
            const day = info.date.getDay();

            // Saturday (6) & Sunday (0)
            if (day === 0 || day === 6) {
              info.el.classList.add("bg-light");
            }
          }}
        />

        {/* Legend */}
        <div className="d-flex gap-3 mt-3 flex-wrap">
          <span className="badge bg-primary">Present</span>
          <span className="badge bg-danger">Holiday</span>
          <span className="badge bg-secondary">Weekend</span>
        </div>
      </div>
    </div>
  );
};

export default AttendanceCalendar;
