import {call, put, takeEvery} from 'redux-saga/effects'
import { LoadUserSuccess,LoadUserFailure } from './action'
import UserConsts from './action.types'

function* loadUser(action){
    try {
        console.log(action)
    let user=yield fetch("/user",{
        headers:{
            "Authorization":`Bearer ${localStorage.getItem("AuthorizationJWT").toString()}`   
        },
        method:"get"
    }).then(response=> response.json())
    .catch(error=> {
        throw error
    })
    yield put (LoadUserSuccess(user))
    }
    catch(error) {
        yield put (LoadUserFailure(error))
    }
}

function* LoadUser(){
    yield takeEvery(UserConsts.LOADING_USER,loadUser)
}

export default LoadUser