import UserConsts from './action.types'
export const LoadUser=() => ({
    type:UserConsts.LOADING_USER
})
export const LoadUserSuccess=(user) => ({
    type:UserConsts.LOAD_USER_SUCCESS,
    payload:user
})
export const LoadUserFailure=(error) => ({
    type:UserConsts.LOAD_USER_FAILED,
    payload:error.message
})