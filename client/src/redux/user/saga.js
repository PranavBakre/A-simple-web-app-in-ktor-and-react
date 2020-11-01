import { put, takeEvery,select} from 'redux-saga/effects'
import { LoadUserSuccess,LoadUserFailure } from './action'
import UserConsts from './action.types'

function* loadUser(action){
    console.log(action)
    try {
        let token = yield select(state => state.login.accessToken)
    let user=yield fetch("/user",{
        headers:{
            "Authorization":`Bearer ${token}`   
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