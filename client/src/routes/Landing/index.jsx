import { useEffect, useRef } from "react";
import { useDispatch } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { login } from "redux/auth";
import css from "./index.module.css"

const authorizationEndpoint = "https://accounts.google.com/o/oauth2/v2/auth";
const params= new URLSearchParams();
params.append("client_id", process.env.REACT_APP_GOOGLE_CLIENT_ID);
params.append("redirect_uri",window.location.origin);
params.append("response_type","code");
params.append("scope",["profile", "email"].join(" "));
params.append("access_type","offline");

const loginUrl = authorizationEndpoint + `?${params.toString()}`

function Landing() {
    const [params] = useSearchParams();
    const dispatch = useDispatch();
    const dispatchCalled = useRef(false)
    useEffect(() => {
        if(!dispatchCalled.current && params && params.has("code")) {
            dispatch(login(params.get("code")))
            dispatchCalled.current = true
        }
    },[params,dispatch])

    return (
        <>
        <div className={css.header}>
            <div className={`${css.item} ${css.title}`}>A simple React App</div>    
        </div>
        <div className={css.landing}>
            <div className={css.sideView}>
                <a href={loginUrl} className={css.item} style={{textDecoration: "none",color: "#00002D"}}>Login With Google</a>
            </div>
            
            
            
        </div>
        </>
    )
}

export default Landing;