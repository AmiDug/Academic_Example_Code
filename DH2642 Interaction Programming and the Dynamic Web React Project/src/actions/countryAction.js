function countryAction(dispatch,destination,promise,actionType,actionTypeCountry){
    promise.then(data=>data[0].CountryName).then(country=>{
        switch(country){
            case "United Kingdom":
                return "UK";
            case "United States":
                return "USA";
            case "South Korea":
                return "S-Korea";
            default:
                country = country.replace(/\s+/g, '-');
                return country;
        }
    })
    .then(country=>dispatch({type:actionTypeCountry,value:country}))
    .catch(()=>dispatch({type:actionTypeCountry,value:null}));
    return  {type:actionType,value:destination};
}

export {countryAction};