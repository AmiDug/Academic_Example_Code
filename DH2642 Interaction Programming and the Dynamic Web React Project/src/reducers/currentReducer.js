import valueReducer from './valueReducer';
import {isEqual} from 'lodash';

export function currentReducer(state={},action){
    return{
        originCountry:valueReducer("SET_ORIGIN_COUNTRY")(state.originCountry,action),
        originPromise:valueReducer("SET_ORIGIN_PROMISE")(state.originPromise,action),
        originError:valueReducer("SET_ORIGIN_ERROR")(state.originError,action),
        originCountryCovid:valueReducer("SET_ORIGIN_COVID")(state.originCountryCovid,action),
        destinationCountry:valueReducer("SET_DESTINATION_COUNTRY")(state.destinationCountry,action),
        trip:valueReducer("SET_TRIP")(state.trip,action),
        savedFlights:savedFlights(state.savedFlights,action)
    }
}

function savedFlights(state=[],action){
    if(action.type==="ADD_FLIGHT"){
        if(state.find(existing=>isEqual(existing,action.value))){
            throw Error("Flight has already been added.");
        }
        return [...state,action.value];
    }
    else if(action.type==="REMOVE_FLIGHT"){
        return [...state].filter(existing=>!isEqual(existing,action.value));
    }
    else{
        return state;
    }
}