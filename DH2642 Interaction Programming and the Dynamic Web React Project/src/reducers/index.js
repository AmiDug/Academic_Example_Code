import { combineReducers } from "@reduxjs/toolkit";
import {flightReducer} from './flightReducer';
import {covidReducer} from './covidReducer';
import {currentReducer} from './currentReducer';
import {userReducer} from './userReducer';
import {flightsSidebarReducer} from './flightsSidebarReducer';
import {loginReducer} from './loginReducer';
import {sortingReducer} from './sortingReducer';

const appReducer=combineReducers({
    flightReducer,
    covidReducer,
    currentReducer,
    userReducer,
    flightsSidebarReducer,
    loginReducer,
    sortingReducer
});

const initialState = appReducer({}, {});

const rootReducer=(state,action)=>{
    if(action.type==="USER_LOGOUT"){
        return initialState;
    }
    else{
        return appReducer(state,action);
    }
}

export default rootReducer;