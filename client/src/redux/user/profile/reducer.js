import ProfileConsts from "./action.types";
export const initState={
    profile:{},
    profileLoading:false,
    error:false,

}
export const profileReducer = (state=initState    ,action) => {
    switch(action.type){
        
        case UserConsts.LOGOUT_SUCCESS:
            return {...state,isLoggedIn:false,accessToken:'',errorMsg:null,loggingIn:false}
        case UserConsts.LOGOUT_FAILED:
            return {...state,isLoggedIn:true,errorMsg:action.payload}
        default :
            return state
    }

}