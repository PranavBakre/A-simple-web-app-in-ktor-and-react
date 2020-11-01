import { applyMiddleware, combineReducers, createStore } from "redux";
import createSagaMiddleware from "redux-saga";
import { all } from "redux-saga/effects";
import { loginReducer } from "./login/reducer";
import logger from "redux-logger";
import LoginSaga from "./login/saga";
import UserSaga from "./user/saga";
import userReducer from "./user/reducer";
import ProfileSaga from "./user/profile/saga";
import { profileReducer } from "./user/profile/reducer";
import addressSaga from "./user/address/saga";
import { persistCombineReducers, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage";
const persistConfig = {
  key: "login",
  storage: storage,
  whitelist: ["login"],
};
const sagaMiddleWare = createSagaMiddleware();
const store = createStore(
  persistCombineReducers(persistConfig, {
    login: loginReducer,
    user: userReducer,
  }),
  applyMiddleware(sagaMiddleWare, logger),
);

const persistor = persistStore(store);
const sagas = function* () {
  yield all([LoginSaga(), UserSaga(), ProfileSaga(), addressSaga()]); //
};
sagaMiddleWare.run(sagas);

export default store;
