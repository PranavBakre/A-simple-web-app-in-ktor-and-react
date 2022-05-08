import { useState } from "react";
import { useSelector } from "react-redux";
import css from "./index.module.css";

function Header() {
  const { user } = useSelector((state) => state.auth);
  const [isMenuOpen, setMenuOpen] = useState(false);

  return (
    <div className={css.header}>
      <div className={css.title}>A simple React App</div>
      <div className={css.popper} onClick={() => setMenuOpen(!isMenuOpen)}>
        <img
          className={css.profilePhoto}
          src={user.profilePicture}
          alt=""
          referrerPolicy="no-referrer"
        />
        <div
          className={css.popperBody}
          style={{ visibility: isMenuOpen ? "visible" : "hidden" }}
        >
          <div>
            <div className={css.popperItem}>Profile</div>

            <div className={css.popperItem}>Logout</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Header;
