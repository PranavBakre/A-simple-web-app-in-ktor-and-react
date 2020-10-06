import { combineReducers } from 'redux'
import { actionChannel } from 'redux-saga/effects'
import UserConsts from './action.types'
import { addressReducer } from './address/reducer'
import { profileReducer } from './profile/reducer'

const initState={
    user:null,
    isUserLoading:false,
    error:null
}

const userReducer=(state=initState,action) => {
    switch(action.type){
        case UserConsts.LOAD_USER_SUCCESS:
            return {...state,isUserLoading:true, user:action.payload,error:null}
        case UserConsts.LOAD_USER_FAILED:
            return {...state,isUserLoading:false,error:action.payload,user:null}
        case UserConsts.LOADING_USER:
            return {...state,isUserLoading:true, error:null,user:null}

        default:
            return state
    }
}

export default combineReducers( {user:userReducer,profile:profileReducer,address:addressReducer})