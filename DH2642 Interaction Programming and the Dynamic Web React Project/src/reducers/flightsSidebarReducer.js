import valueReducer from './valueReducer';

export function flightsSidebarReducer(state={}, action){
    return{
        // No reducer for Corona concern level was put here because that one is already managed in a different reducer ("userReducer.js").
        searchResultsAirlines: searchResultsAirlines(state.searchResultsAirlines,action),
        preferredAirline: valueReducer("SET_PREFERRED_AIRLINE","Any")(state.preferredAirline, action),

        originAirports:originAirports(state.originAirports,action),
        preferredOriginAirport:valueReducer("SET_PREFERRED_ORIGIN_AIRPORT","Any")(state.preferredOriginAirport, action),
        destinationAirports:destinationAirports(state.destinationAirports,action),
        preferredDestinationAirport:valueReducer("SET_PREFERRED_DESTINATION_AIRPORT","Any")(state.preferredDestinationAirport, action),

        passengerQuantities: valueReducer("SET_PASSENGER_QUANTITY",1)(state.passengerQuantities, action),
        // No reducer Currency was put here for the because that one is already managed in a different reducer ("flightReducer.js").
        currencySymbol: currencySymbol(state.currencySymbol,action),
        minPrice:valueReducer("SET_MIN_PRICE",0)(state.minPrice, action),
        maxPrice:valueReducer("SET_MAX_PRICE",100000)(state.maxPrice,action),
        sortingPreference:valueReducer("SET_SORTING_PREFERENCE","Date")(state.sortingPreference, action)
    }
}

function searchResultsAirlines(state=[],action){
    if(action.type==="ADD_CARRIER"){
        if(!state.includes(action.value)){
            return [action.value,...state];
        }
        return state;
    }
    else if(action.type==="CLEAR_CARRIERS"){
        return [];
    }
    else{
        return state;
    }
}

function originAirports(state=[],action){
    if(action.type==="ADD_ORIGIN_AIRPORT"){
        if(!state.includes(action.value)){
            return [action.value,...state];
        }
        return state;
    }
    else if(action.type==="CLEAR_ORIGIN_AIRPORTS"){
        return [];
    }
    else{
        return state;
    }
}

function destinationAirports(state=[],action){
    if(action.type==="ADD_DESTINATION_AIRPORT"){
        if(!state.includes(action.value)){
            return [action.value,...state];
        }
        return state;
    }
    else if(action.type==="CLEAR_DESTINATION_AIRPORTS"){
        return [];
    }
    else{
        return state;
    }
}

function currencySymbol(state="SEK", action){
    if(action.type==="SET_CURRENCY_SYMBOL"){
        if(action.value==="AED"){
            return "AED";
        }
        else if(action.value==="BAM"){
            return "KM";
        }
        else if(action.value==="BAM"){
            return "KM";
        }
        else if(action.value==="BGN"){
            return "лв.";
        }
        else if(action.value==="CHF"){
            return "CHF";
        }
        else if(action.value==="CZK"){
            return "Kč";
        }
        else if(action.value==="DKK"){
            return "kr.";
        }
        else if(action.value==="EUR"){
            return "€";
        }
        else if(action.value==="GBP"){
            return "£";
        }
        else if(action.value==="GEL"){
            return "₾";
        }
        else if(action.value==="HRK"){
            return "kn";
        }
        else if(action.value==="HUF"){
            return "Ft";
        }
        else if(action.value==="ILS"){
            return "₪";
        }
        else if(action.value==="MKD"){
            return "ден";
        }
        else if(action.value==="NOK"){
            return "kr";
        }
        else if(action.value==="PLN"){
            return "zł";
        }
        else if(action.value==="RON"){
            return "lei";
        }
        else if(action.value==="RSD"){
            return "Дин";
        }
        else if(action.value==="RUB"){
            return "₽";
        }
        else if(action.value==="SEK"){
            return "SEK";
        }
        else if(action.value==="UAH"){
            return "грн";
        }
        else if(action.value==="USD"){
            return "$";
        }
    }
    return state;
}