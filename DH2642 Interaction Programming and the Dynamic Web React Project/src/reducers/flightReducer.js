import valueReducer from './valueReducer';

export function flightReducer(state={},action){
    return{
        promise:valueReducer("SET_FLIGHT_PROMISE")(state.promise,action),
        data:valueReducer("SET_FLIGHT_DATA")(state.data,action),
        error:valueReducer("SET_FLIGHT_ERROR")(state.error,action),
        from:valueReducer("SET_FROM")(state.from,action),
        to:valueReducer("SET_TO")(state.to,action),
        departure:valueReducer("SET_DEPARTURE")(state.departure,action),
        currency:valueReducer("SET_CURRENCY","SEK")(state.currency,action)
    }
}