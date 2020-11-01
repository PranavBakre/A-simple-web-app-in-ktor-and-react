import ProfileConsts from "./action.types";

export const LoadUserProfile = () => ({
  type: ProfileConsts.LOAD_USER_PROFILE,
});

export const LoadUserProfileSuccess = (profile) => ({
  type: ProfileConsts.LOAD_USER_PROFILE_SUCCESS,
  payload: profile,
});

export const LoadUserProfileFailure = (error) => ({
  type: ProfileConsts.LOAD_USER_PROFILE_FAILURE,
  payload: error.message,
});
export const UpdateUserProfile = (profile) => ({
  type: ProfileConsts.UPDATE_USER_PROFILE,
  payload: profile,
});
export const UpdateUserProfileSuccess = (profile) => ({
  type: ProfileConsts.UPDATE_USER_PROFILE_SUCCESS,
  payload: profile,
});
export const UpdateUserProfileFailure = (error) => ({
  type: ProfileConsts.UPDATE_USER_PROFILE_FAILED,
  payload: error.message,
});
