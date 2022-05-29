import css from "./index.module.css";
import { useSelector } from "react-redux";
import TextField from "components/TextField";
function Profile() {
  const { user } = useSelector((state) => state.auth);
  return (
    <div className={css.content}>
      <div className={css.profileSummary}>
        <img
          src={user.profilePicture}
          className={css.profilePicture}
          alt="Profile"
        />
        <div className={css.profileName}>
          {user.firstName + " " + user.lastName}
        </div>
        <div style={{ backgroundColor: "rgba(255,255,255,0.6)", padding: "1rem", marginTop: "1rem", borderRadius: "5px", 
  boxShadow: "0px 0px 20px rgba(0, 0, 0, 0.8)" }}>
          <div>Contact Info</div>
          <hr />
          <div>Phone: 9029050534</div>
          <div>Address:</div>
          <div>Line1</div>
          <div>Line2</div>
          <div>city</div>
          <div>state</div>
          <div>696969</div>
        </div>
      </div>
      <div className={css.form}>
        <div className={css.warningBoxContainer}>
          <div className={css.warningBox}>
            <div>You have unsaved changes. Click</div><button className={css.saveButton}>Save</button>
          </div>
        </div>
        <div id="name" className={css.nameSubForm}>
          <TextField
            type="text"
            name="firstName"
            groupStyle={{ flex: 1, display: "block" }}
            labelText="First  Name"
            labelStyle={{ display: "block" }}
            inputStyle={{ width: "100%" }}
            value={user.firstName}
          />
          <TextField
            type="text"
            name="lastName"
            groupStyle={{ flex: 1, display: "block" }}
            labelText="Last  Name"
            labelStyle={{ display: "block" }}
            inputStyle={{ width: "100%" }}
            value={user.lastName}
          />
        </div>
        <div className={css.contactDetailsHeading}>
          Contact Details
          <div className={css.buttonGroup}>
            <button className={css.actionButton}>Add</button>
            <button className={css.actionButton}>View All</button>
          </div>
        </div>
        <div id="phones" style={{ display: "flex", width: "100%", padding: "1rem", gap: "1rem", boxSizing: "border-box" }}>
          <div style={{ borderRadius: "5px", backgroundColor: "rgba(255,255,255,0.1)", padding: "1rem", width: "33%", boxShadow: "0px 0px 20px rgb(0, 0, 0, 0.4)", display: "flex", justifyContent: "space-between", alignItems: "center"}}>
            <div>
              9969966969
            </div>
            <div className={css.buttonGroup} style={{ verticalAlign: "middle", justifyContent: "end" }}>
              <button className={css.actionButton} style={{ padding: "0.2rem 0.5rem" }}>Set as default</button>
              <button className={css.actionButton} style={{ padding: "0.2rem" }}>
                <span class="material-icons" style={{ verticalAlign: "middle" }}>
                  delete
                </span>
              </button>
            </div>
          </div>
        </div>
        <div style={{ margin: "0rem 1rem", padding: "0.3rem", fontSize: "large", fontWeight: 600, display: "flex", alignItems: "center", justifyContent: "space-between" }}>
          Address
          <div className={css.buttonGroup}>
            <button className={css.actionButton}>Add</button>
            <button className={css.actionButton}>View All</button>
          </div>
        </div>
        <div id="addresses" style={{ display: "flex", width: "100%", padding: "1rem", gap: "1rem", boxSizing: "border-box" }}>
          <div style={{ borderRadius: "5px", backgroundColor: "rgba(255,255,255,0.1)", padding: "1rem", width: "33%",boxShadow: "0px 0px 20px rgb(0, 0, 0, 0.4)", display: "flex", flexDirection: "column", justifyContent: "space-between" }}>
            <div>
              <div>Line1</div>
              <div>Line2</div>
              <div>city,state</div>
            </div>
            <div className={css.buttonGroup} style={{ verticalAlign: "middle", justifyContent: "end" }}>
              <button className={css.actionButton} style={{ padding: "0.2rem 0.5rem" }}>Set as default</button>
              <button className={css.actionButton} style={{ padding: "0.2rem" }}>
                <span class="material-icons" style={{ verticalAlign: "middle" }}>
                  delete
                </span>
              </button>
            </div>
          </div>
          <div style={{ borderRadius: "5px", backgroundColor: "rgba(255,255,255,0.1)", padding: "1rem", width: "33%", boxShadow: "0px 0px 20px rgb(0, 0, 0, 0.4)", display: "flex", flexDirection: "column", justifyContent: "space-between" }}>
            <div>
              <div>Line1</div>
              <div>Line2</div>
              <div>city,state</div>
              <div>696969</div>
            </div>
            <div style={{ display: "flex", gap: "1rem", verticalAlign: "middle", justifyContent: "end" }}>
              <button className={css.actionButton} style={{ padding: "0.2rem 0.5rem" }}>Set as default</button>
              <button className={css.actionButton} style={{ padding: "0.2rem" }}>
                <span class="material-icons" style={{ verticalAlign: "middle" }}>
                  delete
                </span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
