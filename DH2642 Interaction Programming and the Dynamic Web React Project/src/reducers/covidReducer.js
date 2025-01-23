import valueReducer from './valueReducer';

export function covidReducer(state={},action){
    return{
        cPromise:valueReducer("SET_COVID_PROMISE")(state.cPromise,action),
        cData:valueReducer("SET_COVID_DATA")(state.cData,action),
        cError:valueReducer("SET_COVID_ERROR")(state.cError,action),
        riskFactor:valueReducer("SET_RISK_FACTOR")(state.riskFactor,action)
    }
}