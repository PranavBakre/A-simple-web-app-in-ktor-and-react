import LoginConsts from "./action.types";
export const initState={
    isLoggedIn:false,
    accessToken:'',
    errorMsg:null,
    loggingIn:false
}
export const loginReducer = (state=initState    ,action) => {
    switch(action.type){
        case LoginConsts.LOGIN_SUCCESS:
            return {...state,isLoggedIn:true, accessToken:action.payload,errorMsg:null,loggingIn:false}
        case LoginConsts.LOGIN_FAILED:
            return {...state,isLoggedIn:false,accessToken:'',errorMsg:action.payload,loggingIn:false}
        case LoginConsts.LOGGING_IN:
            return {...state,isLoggedIn:false, accessToken:'',errorMsg:null,loggingIn:true}
        case LoginConsts.LOGOUT_SUCCESS:
            return {...state,isLoggedIn:false,accessToken:'',errorMsg:null,loggingIn:false}
        case LoginConsts.LOGOUT_FAILED:
            return {...state,isLoggedIn:true,errorMsg:action.payload}
        default :
            return state
    }

}