import valueReducer from './valueReducer';

export function userReducer(state={},action){
    return {
        dateOfBirth:valueReducer("SET_DATE_OF_BIRTH","2000-01-01")(state.dateOfBirth,action),
        coronaConcern:coronaConcern(state.coronaConcern,action),
        respiratory:valueReducer("SET_RESPIRATORY",false)(state.respiratory,action),
        diabetes:valueReducer("SET_DIABETES",false)(state.diabetes,action),
        heart:valueReducer("SET_HEART",false)(state.heart,action),
        hypertension:valueReducer("SET_HYPERTENSION",false)(state.hypertension,action),
        totalIssues:valueReducer("SET_TOTAL_ISSUES",0)(state.totalIssues,action)
    }
}

function coronaConcern(state="4",action){
    if(action.type==="SET_CONCERN"){
        if(action.value<=0||action.value>5){
            throw Error("Corona concern out of range");
        }
        return action.value;
    }
    else{
        return state;
    }
}