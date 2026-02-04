import Profile from '../Images/OIP.jpg';
import BounteousLogo from '../Images/bounteous.jpg'

const Navbar = ({ toggleSidebar }) => {
  return (
    <nav className="navbar navbar-dark bg-dark px-4 py-3 d-flex align-items-center justify-content-between">

      {/* LEFT: Sidebar + Brand + Search */}
      <div className="d-flex align-items-center gap-3 w-75">

        {/* Sidebar Icon */}
        <button
          className="btn btn-outline-light"
          onClick={toggleSidebar}
        >
          ☰
        </button>

        {/* Bounteous Logo */}
        <div
          className="rounded px-2 py-1"
          style={{ backgroundColor: "#ffffff" }}
        >
          <img
            src={BounteousLogo}
            alt="Bounteous"
            style={{
              height: "30px",
              objectFit: "contain"
            }}
          />
        </div>

        {/* Search */}
        <input
          className="form-control"
          placeholder="Search by Employee Name, Job Level..."
        />
      </div>

      {/* RIGHT: Profile Image */}
      <img
        src={Profile}
        alt="Profile"
        className="rounded-circle"
        style={{
          width: "40px",
          height: "40px",
          objectFit: "cover"
        }}
      />

    </nav>
  );
};

export default Navbar;
