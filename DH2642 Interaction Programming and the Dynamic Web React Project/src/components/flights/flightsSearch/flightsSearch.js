import {useSelector,useDispatch} from 'react-redux';
import {createElement as h,Fragment,useEffect} from 'react';
import {FlightsSearchFormView} from './flightsSearchFormView';
import {FlightsSearchResultsView} from './flightsSearchResultsView';
import {findRoute,placeData} from '../../../services/SkyScannerFlightSearch';
import {covidData} from '../../../services/Covid19';
import {flightsAction,covidUpdateAction,countryAction} from '../../../actions';
import {promiseNoData} from '../../common';

function FlightsSearch({path}){
    const {from,to,departure,currency,promise,data,error}=useSelector(state=>state.flightReducer);
    const {cPromise,cData,cError,riskFactor}=useSelector(state=>state.covidReducer);
    const {coronaConcern,totalIssues}=useSelector(state=>state.userReducer);
    const {destinationCountry}=useSelector(state=>state.currentReducer);
    const {preferredAirline,preferredOriginAirport,preferredDestinationAirport,passengerQuantities,minPrice,maxPrice,sortingPreference}=useSelector(state=>state.flightsSidebarReducer);
    
    const dispatch=useDispatch();

    useEffect(()=>{
        destinationCountry&&dispatch(covidUpdateAction(dispatch,covidData(destinationCountry),coronaConcern,totalIssues));
    },[dispatch,from,to,coronaConcern,totalIssues,destinationCountry]);

    useEffect(()=>{
        from&&to&&dispatch(flightsAction(dispatch,findRoute(from,to,departure?departure:"anytime",currency)));
    },[dispatch,from,to,departure,currency]);

    return h(Fragment,{},
        h(FlightsSearchFormView,{
            from,
            to,
            onSearch: e=>{
                e.preventDefault();
                dispatch(countryAction(dispatch,e.target.from.value,placeData(e.target.from.value),"SET_FROM","SET_ORIGIN_COUNTRY"));
                dispatch(countryAction(dispatch,e.target.to.value,placeData(e.target.to.value),"SET_TO","SET_DESTINATION_COUNTRY"));
                dispatch({type:"SET_DEPARTURE",value:e.target.departure.value});
            },
        }),
        promiseNoData(promise,data,error,"flightSearch")||
        promiseNoData(cPromise,cData,cError,"covidSearch")||
            h(FlightsSearchResultsView,{
                searchResults:data,
                riskFactor,
                userPreferences:{
                    airline:preferredAirline,
                    originAirport:preferredOriginAirport,
                    destinationAirport:preferredDestinationAirport,
                    minPrice,
                    maxPrice,
                    sortingPreference
                },
                passengers:passengerQuantities,
                setTrip:trip=>dispatch({type:"SET_TRIP",value:trip}),
                path
            })
    );
}

export {FlightsSearch};