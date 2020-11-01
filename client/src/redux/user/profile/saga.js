import { all, put, select, takeEvery } from "redux-saga/effects";
import {
  LoadUserProfile,
  LoadUserProfileFailure,
  LoadUserProfileSuccess,
  UpdateUserProfileFailure,
  UpdateUserProfileSuccess,
} from "./action";
import ProfileConsts from "./action.types";

function* updateProfile(action) {
  try {
    let token = yield select((state) => state.login.accessToken);
    let profile = yield fetch("/profile", {
      method: "post",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(action.payload),
    }).then((response) => response.json())
      .catch((error) => {
        throw (error);
      });
    yield put(UpdateUserProfileSuccess(profile));
  } catch (error) {
    yield put(UpdateUserProfileFailure(error));
  }
}

function* UpdateProfile() {
  yield takeEvery(ProfileConsts.UPDATE_USER_PROFILE, updateProfile);
}

function* getProfile(action) {
  try {
    let token = yield select((state) => state.login.accessToken);
    let profile = yield fetch("/profile", {
      method: "get",
      headers: {
        "Authorization": `Bearer ${token}`,
      },
    }).then((response) => response.json())
      .catch((error) => {
        throw (error);
      });
    yield put(LoadUserProfileSuccess(profile));
  } catch (error) {
    yield put(LoadUserProfileFailure(error));
  }
}

function* GetProfile() {
  yield takeEvery(ProfileConsts.LOAD_USER_PROFILE, getProfile);
}

function* ProfileSaga() {
  yield all([GetProfile(), UpdateProfile()]);
}

export default ProfileSaga;
