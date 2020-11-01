import LoginAction from "./action.types";

export const LoggingIn = (user) => ({
  type: LoginAction.LOGGING_IN,
  payload: user.accessToken,
});

export const LoginSuccess = (user) => ({
  type: LoginAction.LOGIN_SUCCESS,
  payload: user["access-token"],
});

export const LoginFailure = (error) => ({
  type: LoginAction.LOGIN_FAILED,
  payload: error.message,
});

export const LogoutFailure = (error) => ({
  type: LoginAction.LOGOUT_FAILED,
  payload: error.message,
});
export const LogoutSuccess = () => ({
  type: LoginAction.LOGOUT_SUCCESS,
});
// export const LOGIN=()=>(dispatch)=>{
//     dispatch(LOGGING_IN())

// }
