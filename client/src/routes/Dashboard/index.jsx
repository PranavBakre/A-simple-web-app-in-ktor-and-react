import { Routes, Route, NavLink } from "react-router-dom";
import Header from "components/Header";
import css from "./index.module.css";
import Profile from "routes/Modules/Profile";
function Dashboard() {
  return (
    <>
      <Header />
      <div className={css.body}>
        <Routes>
          <Route path="/profile" element={<Profile />} />
        </Routes>
        <NavLink to="/app/profile" className={css.profileButtonParent}>
          <span className="material-icons">manage_accounts</span>
          <div className={css.profileButton}>Profile</div>
        </NavLink>
      </div>
    </>
  );
}

export default Dashboard;
