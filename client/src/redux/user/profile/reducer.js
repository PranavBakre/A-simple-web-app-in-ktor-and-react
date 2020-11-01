import ProfileConsts from "./action.types";
export const initState = {
  profile: {},
  profileLoading: false,
  profileUpdating: false,
  error: false,
};
export const profileReducer = (state = initState, action) => {
  switch (action.type) {
    case ProfileConsts.LOAD_USER_PROFILE:
      return {
        ...state,
        profileLoading: true,
        profileUpdating: false,
        error: null,
        profile: {},
      };
    case ProfileConsts.LOAD_USER_PROFILE_SUCCESS:
      return {
        ...state,
        profile: action.payload,
        profileLoading: true,
        profileUpdating: false,
        error: null,
      };
    case ProfileConsts.LOAD_USER_PROFILE_FAILURE:
      return {
        ...state,
        profile: {},
        profileLoading: true,
        profileUpdating: false,
        error: action.payload,
      };
    case ProfileConsts.UPDATE_USER_PROFILE_SUCCESS:
      return {
        ...state,
        profileUpdating: false,
        profile: action.payload,
        error: null,
        profileLoading: false,
      };
    case ProfileConsts.UPDATE_USER_PROFILE_FAILED:
      return {
        ...state,
        profileUpdating: false,
        error: action.payload,
        profileLoading: false,
      };
    case ProfileConsts.UPDATE_USER_PROFILE:
      return {
        ...state,
        profileUpdating: true,
        error: null,
        profileLoading: false,
      };
    default:
      return state;
  }
};
