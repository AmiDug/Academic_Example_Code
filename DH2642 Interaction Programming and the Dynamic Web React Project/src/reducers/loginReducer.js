import valueReducer from './valueReducer';
import {signin} from '../helper/auth'

export function loginReducer(state={},action){
    return {
        currentUser:valueReducer("SET_CURRENT_USER")(state.currentUser,action),
        credentialsError:valueReducer("SET_CREDENTIALS_ERROR")(state.credentialsError,action),
        mode:valueReducer("SET_MODE",signin)(state.mode,action),
        passType:passType(state.passType,action)
    }
}

function passType(state="password",action){
    if(action.type==="TOGGLE_PASS_VISIBILITY"){
        return state==="password"? "text":"password";
    }
    else{
        return state;
    }
}