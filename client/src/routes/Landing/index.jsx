import { useEffect, useRef } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useSearchParams, Navigate } from "react-router-dom";
import { login } from "redux/auth";
import css from "./index.module.css";

const authorizationEndpoint = "https://accounts.google.com/o/oauth2/v2/auth";
const params = new URLSearchParams();
params.append("client_id", process.env.REACT_APP_GOOGLE_CLIENT_ID);
params.append("redirect_uri", window.location.origin);
params.append("response_type", "code");
params.append("scope", ["profile", "email"].join(" "));
params.append("access_type", "offline");
params.append("prompt", "consent");

const loginUrl = authorizationEndpoint + `?${params.toString()}`;

function Landing() {
  const [params] = useSearchParams();
  const dispatch = useDispatch();
  const { isLoading, isLoggedIn } = useSelector((state) => state.auth);
  const dispatchCalled = useRef(false);
  useEffect(() => {
    if (!dispatchCalled.current && params && params.has("code")) {
      dispatch(login(params.get("code")));
      dispatchCalled.current = true;
    }
  }, [params, dispatch]);
  if (isLoggedIn) {
    return <Navigate to="/app" />;
  }
  return (
    <>
      <div className={css.header}>
        <div className={css.title}>A simple React App</div>
      </div>
      {isLoading ? (
        <div className={css.loading}>
          <div className={css["lds-dual-ring"]} />
        </div>
      ) : (
        <div className={css.landing}>
          <div className={css.sideView}>
            <a
              href={loginUrl}
              className={css.item}
              style={{ textDecoration: "none", color: "#00002D" }}
            >
              Login With Google
            </a>
          </div>
        </div>
      )}
    </>
  );
}

export default Landing;
