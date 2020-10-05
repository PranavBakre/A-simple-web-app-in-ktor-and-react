import {createStore,combineReducers, applyMiddleware} from 'redux';
import createSagaMiddleware from 'redux-saga'
import {all} from 'redux-saga/effects'
import {loginReducer} from './login/reducer'
import logger from 'redux-logger'
import LoginSaga from './login/saga'
import UserSaga from './user/saga'
import userReducer from './user/reducer';

const sagaMiddleWare = createSagaMiddleware()
const store= createStore(
    combineReducers({
        login:loginReducer,
        user: userReducer
    }),
    applyMiddleware(sagaMiddleWare,logger)
)
const sagas=function* () {
    yield all ([LoginSaga(),
    UserSaga()])
}
sagaMiddleWare.run(sagas)

export default store