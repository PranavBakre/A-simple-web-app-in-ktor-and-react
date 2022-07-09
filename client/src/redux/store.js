import { configureStore } from "@reduxjs/toolkit";
import logger from "redux-logger";
import authReducer from "./auth";
import {profileReducer, profileMiddleware} from "modules/Profile/redux/profile"

export const store = configureStore({
  reducer: {
    auth: authReducer,
    profile: profileReducer
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([...profileMiddleware, logger]),
});
