import {useSelector,useDispatch} from 'react-redux';
import {createElement as h} from 'react';
import {FlightsSidebarView} from './flightsSidebarView';

function FlightsSidebar(){
    const coronaConcern = useSelector(state=>state.userReducer.coronaConcern);
    const {searchResultsAirlines,originAirports,destinationAirports,minPrice,maxPrice, currencySymbol, sortingPreference} = useSelector(state=>state.flightsSidebarReducer);
    
    const dispatch = useDispatch();

    return h(FlightsSidebarView,{
        coronaConcern,
        setCoronaConcern: concernValue => dispatch({type:"SET_CONCERN", value: concernValue}),
        
        originAirports,
        setPreferredOriginAirport: airport => dispatch({type:"SET_PREFERRED_ORIGIN_AIRPORT", value:airport}),
        destinationAirports,
        setPreferredDestinationAirport: airport => dispatch({type:"SET_PREFERRED_DESTINATION_AIRPORT",value:airport}),

        searchResultsAirlines,
        setPreferredAirline: airlineName => dispatch({type:"SET_PREFERRED_AIRLINE",value:airlineName}),
        setPassengerQuantity: passengerQuantity => dispatch({type:"SET_PASSENGER_QUANTITY", value:passengerQuantity}),
        setCurrency: newCurrency => dispatch({type:"SET_CURRENCY", value: newCurrency}),
        currencySymbol,
        setCurrencySymbol: newCurrency => dispatch({type:"SET_CURRENCY_SYMBOL", value: newCurrency}),
        minPrice,
        setMinPrice: price=>dispatch({type:"SET_MIN_PRICE",value:price}),
        maxPrice,
        setMaxPrice: price=>dispatch({type:"SET_MAX_PRICE",value:price}),
        sortingPreference,
        setSortingPreference: preference => dispatch({type:"SET_SORTING_PREFERENCE" ,value:preference})
    })
}

export {FlightsSidebar};