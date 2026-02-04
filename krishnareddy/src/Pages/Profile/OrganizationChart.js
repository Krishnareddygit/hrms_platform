import Profile from "../../Images/OIP.jpg";

const OrgCard = ({ name, role }) => (
  <div className="card shadow-sm p-2 d-flex align-items-center" style={{ width: "220px" }}>
    <img
      src={Profile}
      alt="profile"
      className="rounded-circle me-2"
      width="40"
      height="40"
    />
    <div>
      <div className="fw-semibold">{name}</div>
      <div className="text-muted small">{role}</div>
    </div>
  </div>
);

const OrganizationChart = () => {
  return (
    <div className="card p-4">
      <h5 className="fw-bold mb-4">Organization Chart</h5>

      {/* LEVEL 1 */}
      <div className="d-flex justify-content-center mb-4">
        <OrgCard name="Rama Krishna Reddy" role="Engineering Manager" />
      </div>

      {/* LEVEL 2 */}
      <div className="d-flex justify-content-center gap-5 mb-4">
        <OrgCard name="Nishtha Sinha" role="Tech Lead" />
        <OrgCard name="Abhishek Kumar" role="Tech Lead" />
      </div>

      {/* LEVEL 3 */}
      <div className="d-flex justify-content-center gap-4 flex-wrap">
        <OrgCard name="Employee A" role="Software Engineer" />
        <OrgCard name="Employee B" role="Software Engineer" />
        <OrgCard name="Employee C" role="Software Engineer" />
      </div>
    </div>
  );
};

export default OrganizationChart;
