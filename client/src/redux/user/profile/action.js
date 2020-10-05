
export const UpdateUserProfile=(profile) => ({
    type: UserConsts.UPDATE_USER_PROFILE,
    payload:profile

})
export const UpdateUserProfileSuccess=(profile) => ({
    type: UserConsts.UPDATE_USER_PROFILE_SUCCESS,
    payload:profile
})
export const UpdateUserProfileFailure=() => ({
    type:UserConsts.UPDATE_USER_PROFILE_FAILED,
    payload:error.error
})