import { call,takeEvery,put} from 'redux-saga/effects'
import { LoggingIn, LoginFailure, LoginSuccess } from './action'
import LoginConsts from './action.types'


function* loginWithServer(action){
        
        try {
        let loginToken=yield fetch("http://localhost:8080/login",
        {
          method:"post",
          headers:{
              "Content-Type":"application/json"
          },
          body:JSON.stringify({"access-token":action.payload})
        }).then(response => response.json())
        .catch((error)=>{
            throw error
        })
        localStorage.setItem("AuthorizationJWT",loginToken["access-token"])
        yield put (LoginSuccess(loginToken))
        }catch(error) {
            yield put(LoginFailure(error))
        }

    
}

function* LoginWithServer () {
    yield takeEvery(LoginConsts.LOGGING_IN,loginWithServer)
}

export default LoginWithServer