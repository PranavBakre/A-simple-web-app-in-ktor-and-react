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
      </div>
      <div className={css.form}>
        <div id="name" style={{ display: "flex", width: "100%" }}>
          <TextField
            type="text"
            name="firstName"
            groupStyle={{ flex: 1, display: "block" }}
            labelText="First  Name"
            labelStyle={{ display: "block" }}
            inputStyle={{ width: "100%" }}
            placeholder={user.firstName}
          />
          <TextField 
            type="text"
            name="lastName"
            groupStyle={{ flex: 1, display: "block" }}
            labelText="Last  Name"
            labelStyle={{ display: "block" }}
            inputStyle={{ width: "100%" }}
            placeholder={user.lastName}/>
          
        </div>
        <div style={{ display: "flex", width: "100%", justifyContent:"center", paddingTop: "1rem" }}>
          <button style={{padding:"0.5rem 1rem"}}>Submit</button>
        </div>
        
      </div>
    </div>
  );
}
// style={{width:"100%", height:"100%", backgroundColor:"rgba(245, 245, 245, 0.4)"}}
export default Profile;
