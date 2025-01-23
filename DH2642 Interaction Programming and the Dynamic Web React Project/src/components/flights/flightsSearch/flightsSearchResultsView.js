import {Link} from 'react-router-dom';
import './flightsSearch.css';

function matcher(value,preferredValue){
    if(value===preferredValue||preferredValue==="Any"){
        return true;
    }
}

function resultsFilter(trip,{airline,originAirport,destinationAirport,minPrice,maxPrice},passengers){
    const airlineMatch=matcher(trip.carrier,airline);
    const originAirportMatch=matcher(trip.origin[0],originAirport);
    const destinationAirportMatch=matcher(trip.destination[0],destinationAirport);
    let priceMatch=false;

    if((trip.price*passengers)>=minPrice&&(trip.price*passengers)<=maxPrice){
        priceMatch=true;
    }

    return airlineMatch&&originAirportMatch&&destinationAirportMatch&&priceMatch;
}

function peopleText(passengers){
    if (passengers > 1)
        return " people ";

    return " person ";
}

// Checks what's in the reducer that stores the sorting preference for the search results
// and returns the callback to the apropriate sorting function
function getCompareFunction(sortingPreference){
    if (sortingPreference==="Price")
        return comparePrices;
    else // if sortingPreference==="Date"
        return compareDates;
}

function compareDates(date1Source, date2Source){
    if(date1Source.departureDate < date2Source.departureDate)
        return -1;
    else if(date1Source.departureDate > date2Source.departureDate)
        return 1;
    else
        return 0;
}

function comparePrices(price1Source, price2Source){
    if(price1Source.price < price2Source.price)
        return -1;
    else if(price1Source.price > price2Source.price)
        return 1;
    else
        return 0;
}

const FlightsSearchResultsView=({searchResults,riskFactor,userPreferences,passengers,setTrip,path})=>
    <div>
        {searchResults[0]&&searchResults[0][0]?
        searchResults.map(entry=> entry.sort(getCompareFunction(userPreferences.sortingPreference)).map((trip,i)=>
            <div key={i}>
                {resultsFilter(trip,userPreferences,passengers)?
                 <div className="card">
                    <Link to={`${path}/details`} onClick={()=>setTrip({...trip,destinationRisk:riskFactor.destinationRisk})}>
                        <div>Departure: {trip.departureDate}</div>
                        <div className="content">
                            <span className="largeText">{trip.carrier}</span>
                            <div>
                                <div>{trip.origin[1]}</div>
                                <div className="largeText"><b>{trip.origin[0]}</b></div>
                            </div>
                            <span><hr/></span>
                            <div>
                                <div>{trip.destination[1]}</div>
                                <div className="largeText"><b>{trip.destination[0]}</b></div>
                            </div>
                        </div>
                        <div className="content">
                            {riskFactor&&riskFactor.riskFactor?
                            <div className="riskSummary">
                                <div>Destination infection level: {riskFactor.destinationRisk}</div>
                                <div>User risk level: {riskFactor.userIssues}</div>
                                <div>Result sensitivity level: {riskFactor.coronaConcern}</div>
                                <div className={riskFactor.riskFactor.replace(/\s+/g, '-')}>
                                    <b>Risk Factor: {riskFactor.riskFactor}</b>
                                    {riskFactor.riskFactor==="High"||riskFactor.riskFactor==="Very High"?<img src="/Warning_20x20.png" alt="Warning"/>:
                                    riskFactor.riskFactor==="Low"?<img src="/Caution_20x20.png" alt="Caution"/>:
                                    riskFactor.riskFactor==="Very Low"?<img src="/OK_20x20.png" alt="OK"/>:false}
                                </div>
                            </div>:false}
                            <div className="largeText">
                                <b>{passengers + peopleText(passengers) + " - " + (trip.price*passengers).toFixed(2)} {trip.currency}</b>
                            </div>
                        </div>
                    </Link>
                </div>:false}
            </div> ))
            :
            <div className="card">
                <p className="largeText">
                    No flights available :(
                </p>
            </div>
        }
    </div>;

export {FlightsSearchResultsView};
