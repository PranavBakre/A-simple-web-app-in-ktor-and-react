import { combineReducers } from "@reduxjs/toolkit";
import { addressApi } from "./address";

export const profileReducer = combineReducers({
    [addressApi.reducerPath]: addressApi.reducer
});

export const profileMiddleware = [
    addressApi.middleware
]