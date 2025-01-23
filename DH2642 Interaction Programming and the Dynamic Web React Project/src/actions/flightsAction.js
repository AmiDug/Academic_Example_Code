function matchData(arr,prop,id){
    let matched;

    arr.forEach(arrEntry=>{
        if(arrEntry[prop]===id){
            matched=arrEntry;
        }
    })

    return matched;
}

function matchStation(entry,id){
    const matched=matchData(entry.Places,"PlaceId",id);
    const [iata,name]=[matched.IataCode,matched.Name];
    return [iata,name];
}

function matchCarrier(entry,id){
    return matchData(entry.Carriers,"CarrierId",id).Name;
}

function flightsAction(dispatch,promise){
    dispatch({type:"SET_FLIGHT_DATA",value:null});
    dispatch({type:"SET_FLIGHT_ERROR",value:null});
    dispatch({type:"CLEAR_CARRIERS"});
    dispatch({type:"CLEAR_ORIGIN_AIRPORTS"});
    dispatch({type:"CLEAR_DESTINATION_AIRPORTS"});
    if(!promise){
        return {type:"SET_FLIGHT_PROMISE",value:null};
    }
    promise.then(data=>{
        return data.map(entry=>entry.Quotes.map(quote=>{
            const carrier=matchCarrier(entry,quote.OutboundLeg.CarrierIds[0]);
            const origin=matchStation(entry,quote.OutboundLeg.OriginId);
            const destination=matchStation(entry,quote.OutboundLeg.DestinationId);
            dispatch({type:"ADD_CARRIER",value:carrier});
            dispatch({type:"ADD_ORIGIN_AIRPORT",value:origin[0]});
            dispatch({type:"ADD_DESTINATION_AIRPORT",value:destination[0]});
            return {
                departureDate:quote.OutboundLeg.DepartureDate.slice(0,10),
                carrier,
                origin,
                destination,
                price:quote.MinPrice,
                currency:entry.Currencies[0].Symbol
            };
        }))
    }).then(data=>dispatch({type:"SET_FLIGHT_DATA",value:data}))
        .catch(error=>dispatch({type:"SET_FLIGHT_ERROR",value:error}));
    return  {type:"SET_FLIGHT_PROMISE",value:{}};
}

export {flightsAction};