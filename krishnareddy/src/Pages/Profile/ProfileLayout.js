import { Outlet } from "react-router-dom";
import ProfileHeader from "../../Components/ProfileHeader"


const ProfileLayout = () => {
  return (
    <>
      <ProfileHeader />
      <Outlet />
    </>
  );
};

export default ProfileLayout;
