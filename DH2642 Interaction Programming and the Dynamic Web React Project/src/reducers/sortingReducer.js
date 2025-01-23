import valueReducer from './valueReducer';

export function sortingReducer(state={},action){
    return{
        sortingMethod:valueReducer("SET_SORTING_METHOD","departureDate")(state.sortingMethod,action),
        sortOrder:sortOrder(state.sortOrder,action)
    }
}

function sortOrder(state=1,action){
    if(action.type==="TOGGLE_ORDER"){
        return -1*state;
    }
    else if(action.type==="DEFAULT_ORDER"){
        return 1;
    }
    else{
        return state;
    }
}