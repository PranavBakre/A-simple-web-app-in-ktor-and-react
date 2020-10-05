import { actionChannel } from 'redux-saga/effects'
import UserConsts from './action.types'

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
            return {...state,isUserLoading:false,error:action.payload,user:''}
        case UserConsts.LOADING_USER:
            return {...state,isUserLoading:true, error:null,user:''}

        default:
            return state
    }
}

export default userReducer