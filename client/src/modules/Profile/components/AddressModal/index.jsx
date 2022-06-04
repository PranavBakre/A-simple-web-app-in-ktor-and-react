import { useEffect, useRef } from "react";
import TextField from "components/TextField"
import css from "./index.module.css"
function AddressModal() {
  const dialogRef = useRef()
  const closeDialog = () => {
      dialogRef.current.close()
  }
  useEffect(()=> {
      const addressDialog = dialogRef.current
      addressDialog.showModal();
      return () => {
        addressDialog.close()
      }
  },[])
  
  return(
    <dialog ref={dialogRef} className={css.dialog}>
        <div className={css.dialogHeading}>
            Add a new Address
        </div>
        <hr className={css.horizontalRule}/>
        <div className={css.dialogContent}>
            <div>
            <TextField
                type="text"
                name="line 1"
                groupStyle={{ flex: 1, display: "block", paddingTop: "0.5rem", paddingBottom: "0" }}
                labelText="Line1"
                labelStyle={{ display: "block", fontSize: "1.1rem" }}
                inputStyle={{  padding: "0.4rem", width: "100%" }}
                value={""}
            />
            </div>
            <div>
            <TextField
                type="text"
                name="line2"
                groupStyle={{ flex: 1, display: "block", paddingTop: "0.5rem", paddingBottom: "0" }}
                labelText="Line 2"
                labelStyle={{ display: "block", fontSize: "1.1rem" }}
                inputStyle={{  padding: "0.4rem", width: "100%" }}
                value={""}
            /></div>
            <div style={{display:"flex"}}>
            <TextField
                type="text"
                name="city"
                groupStyle={{ flex: 1.5, display: "block", paddingTop: "0.5rem", paddingBottom: "0" }}
                labelText="City"
                labelStyle={{ display: "block", fontSize: "1.1rem" }}
                inputStyle={{  padding: "0.4rem", width: "100%" }}
                value={""}
            />
            <TextField
                type="text"
                name="state"
                groupStyle={{ flex: 1.5, display: "block", paddingTop: "0.5rem", paddingBottom: "0" }}
                labelText="State"
                labelStyle={{ display: "block", fontSize: "1.1rem" }}
                inputStyle={{  padding: "0.4rem", width: "100%" }}
                value={""}
            />
            <TextField
                type="text"
                name="postalCode"
                groupStyle={{ flex: 1, display: "block", paddingTop: "0.5rem", paddingBottom: "0" }}
                labelText="Postal Code"
                labelStyle={{ display: "block", fontSize: "1.1rem" }}
                inputStyle={{ padding: "0.4rem", width: "100%" }}
                value={""}
            /></div>
            <div style={{display: "flex", justifyContent:"center", padding: "1rem"}}>
                <button className={css.saveButton}>Submit</button>
            </div>
        </div>
    </dialog>
  );
}

export default AddressModal;
