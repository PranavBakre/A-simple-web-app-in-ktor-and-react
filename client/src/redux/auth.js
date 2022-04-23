import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

export const login = createAsyncThunk("auth/login", async (authorizationCode, thunkAPI) => {
    const response = await fetch(`${process.env.REACT_APP_BACKEND_URL}/auth/login`,{
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            code: authorizationCode,
            url: window.location.origin
        }),
        signal: thunkAPI.signal
    });
    const json = await response.json()
    if (!response.ok) {
        thunkAPI.rejectWithValue(json)
    }
    return json
  })

export const authSlice = createSlice({
    name: "auth",
    initialState: {
        isLoggedIn: false,
        user: undefined
    },
    extraReducers: {
        [login.pending]: () => {
            return {
                isLoading: true,
                isLoggedIn: false,
                user: undefined
            }
        },
        [login.fulfilled]: (_,action) => {
            return {
                isLoading: false,
                isLoggedIn: true,
                user: action.payload
            }
        },
        [login.rejected]: (_,action) => {
            return {
                isLoading: false,
                isLoggedIn: false,
                user: undefined
            }
        }
    }
})

export default authSlice.reducer