import { Outlet } from "react-router-dom";
import { useState } from "react";
import Navbar from "../Components/Navbar";
import Sidebar from "../Components/Sidebar";

const MainLayout = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const toggleSidebar = () => {
    setIsSidebarOpen(prev => !prev);
  };

  return (
    <>
      {/* Navbar ALWAYS visible */}
      <Navbar toggleSidebar={toggleSidebar} />

      <div className="d-flex">
        {/* Sidebar toggles */}
        {isSidebarOpen && <Sidebar />}

        {/* Main content */}
        <div className="flex-grow-1 p-4">
          <Outlet />
        </div>
      </div>
    </>
  );
};

export default MainLayout;
