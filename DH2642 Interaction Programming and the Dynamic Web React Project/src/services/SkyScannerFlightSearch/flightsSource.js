import API_KEY from '../apiConfig';

export {
    findRoute,
    placeData
};

const BASE_URL="https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/";

function apiCall(params){
    return fetch(BASE_URL+params, {
        "method": "GET",
        "headers": {
            "x-rapidapi-key": API_KEY,
            "x-rapidapi-host": "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com"
        }
    })
    .then(response=>{
        if(response.ok){
            return response;
        }
        throw Error(response.statusText);
    })
    .then(response => response.json());
}

async function placeData(city){
    const places=city&&apiCall(`autosuggest/v1.0/UK/GBP/en-GB/?query=${city}`)
    .then(data=>data.Places)
    const resolved=await places;
    return resolved;
}

async function placeId(city){
    const resolved=await(placeData(city));
    const idList=resolved.map(place=>place.PlaceId);
    return idList;
}

async function findRoute(from,to,outbound,currency){
    const fromPlaces=await placeId(from);
    const toPlaces=await placeId(to);
    let cartesian=[];

    fromPlaces.forEach(from=>{
        toPlaces.forEach(to=>{
            cartesian=[...cartesian,[from,to]];
        })
    })

    return Promise.all(cartesian.slice(0,1).map(trip=>apiCall(`browseroutes/v1.0/US/${currency}/en-US/${trip[0]}/${trip[1]}/${outbound}?inboundpartialdate`)));
}