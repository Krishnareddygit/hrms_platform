import Profile from '../Images/OIP.jpg'

const ProfileHeader = () => {
  return (
    <div>
      <div className="card">

        <div className="card-body d-flex align-items-center"
                  style={{
                    background: "linear-gradient(to right, #e7f1ff, #f2e9ff)"
                  }}>
          <img
            src={Profile}
            alt="profile"
            className="rounded-circle me-3"
            style={{ width: "100px", height: "100px", objectFit: "cover" }}
          />

          <div>
            <h5 className="mb-1">Nallamilli S G B Rama Krishna Reddy</h5>
            <small className="text-muted">
              Software Engineer   |   Corporate
            </small>
          </div>
        </div>
      </div>


      <div className="card mb-3">
        <div className="card-body d-flex justify-content-between align-items-left">
        <p className="mb-0 d-flex align-items-center">
          <i className="bi bi-envelope me-2"></i> nallamilli.reddy@bounteous.com 
        </p>

        <p className="mb-0 d-flex align-items-center">
          <i className="bi bi-geo-alt-fill me-2 text-danger"></i>
          Bengaluru, Karnataka, India (APAC)
        </p>
        
        <p className="mb-0 d-flex align-items-center">
          <i className="bi bi-person-circle me-2 text-success"></i>
          Nishtha Sinha
        </p>

        </div>
      </div>
    </div>

  );
};

export default ProfileHeader;