import { useState } from "react";

const Documents = () => {
  const [showViewer, setShowViewer] = useState(false);
  const [activeDoc, setActiveDoc] = useState(null);
  const [search, setSearch] = useState("");

  const documents = [
    {
      name: "Resume.pdf",
      category: "Personal",
      addedOn: "02 Feb 2026",
      acknowledgedOn: "05 Feb 2026",
      url: "/Resume.pdf"
    },
    {
      name: "Offer_Letter.pdf",
      category: "HR",
      addedOn: "10 Jan 2026",
      acknowledgedOn: "-",
      url: "/Resume.pdf"
    }
  ];

  const filteredDocs = documents.filter((doc) =>
    `${doc.name} ${doc.category} ${doc.addedOn} ${doc.acknowledgedOn}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  return (
    <div className="container-fluid mt-4">

      {/* PAGE HEADER */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h5 className="fw-semibold mb-0">Documents</h5>
        <button className="btn btn-primary btn-sm">
          Upload Document
        </button>
      </div>

      {/* SEARCH BAR */}
      <div className="card mb-2">
        <div className="card-body py-2">
          <input
            type="text"
            className="form-control"
            placeholder="Search documents..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>
      </div>

      {/* DOCUMENT TABLE */}
      <div className="card shadow-sm">
        <div className="card-body p-0">

          <div className="table-responsive">
            <table className="table table-hover align-middle mb-0">
              <thead className="table-light">
                <tr>
                  <th>Document Name</th>
                  <th>Document Category</th>
                  <th>Added On</th>
                  <th>Acknowledged On</th>
                  <th className="text-end">Actions</th>
                </tr>
              </thead>

              <tbody>
                {filteredDocs.length > 0 ? (
                  filteredDocs.map((doc, index) => (
                    <tr key={index}>
                      <td className="fw-semibold">{doc.name}</td>
                      <td>
                        <span className="badge bg-secondary">
                          {doc.category}
                        </span>
                      </td>
                      <td>{doc.addedOn}</td>
                      <td>
                        {doc.acknowledgedOn === "-" ? (
                          <span className="text-muted">Pending</span>
                        ) : (
                          doc.acknowledgedOn
                        )}
                      </td>
                      <td className="text-end">
                        <button
                          className="btn btn-sm btn-outline-primary me-2"
                          onClick={() => {
                            setActiveDoc(doc);
                            setShowViewer(true);
                          }}
                        >
                          View
                        </button>

                        <a
                          href={doc.url}
                          download
                          className="btn btn-sm btn-outline-success"
                        >
                          Download
                        </a>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td
                      colSpan="5"
                      className="text-center text-muted py-4"
                    >
                      No documents found
                    </td>
                  </tr>
                )}
              </tbody>

            </table>
          </div>

        </div>
      </div>

      {/* OFFCANVAS VIEWER */}
      {activeDoc && (
        <div
          className={`offcanvas offcanvas-end ${showViewer ? "show" : ""}`}
          style={{
            visibility: showViewer ? "visible" : "hidden",
            width: "60%"
          }}
        >
          <div className="offcanvas-header">
            <h6 className="offcanvas-title">
              {activeDoc.name}
            </h6>
            <button
              className="btn-close"
              onClick={() => setShowViewer(false)}
            ></button>
          </div>

          <div className="offcanvas-body p-0">
            <iframe
              src={activeDoc.url}
              title="PDF Viewer"
              width="100%"
              height="100%"
              style={{ border: "none" }}
            />
          </div>
        </div>
      )}

      {/* BACKDROP */}
      {showViewer && (
        <div
          className="offcanvas-backdrop fade show"
          onClick={() => setShowViewer(false)}
        ></div>
      )}
    </div>
  );
};

export default Documents;
