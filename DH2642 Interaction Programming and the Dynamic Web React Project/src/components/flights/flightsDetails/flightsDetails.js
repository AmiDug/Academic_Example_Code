import {useSelector,useDispatch} from 'react-redux';
import {createElement as h,useEffect} from 'react';
import {useHistory} from 'react-router-dom';
import {FlightsDetailsView} from './flightsDetailsView';
import {promiseAction} from '../../../actions';
import {covidData} from '../../../services/Covid19';
import {promiseNoData} from '../../../components/common';
import {db} from '../../../services/firebase';
import {isEqual} from 'lodash';

function FlightsDetails(){
    const {originCountry,originPromise,originError,originCountryCovid,destinationCountry,trip,savedFlights}=useSelector(state=>state.currentReducer);
    const {cPromise,cData,cError}=useSelector(state=>state.covidReducer);
    const {currentUser}=useSelector(state=>state.loginReducer);

    const history=useHistory();
    const dispatch=useDispatch();

    useEffect(()=>{
        originCountry&&dispatch(promiseAction(dispatch,covidData(originCountry),"SET_ORIGIN_PROMISE","SET_ORIGIN_COVID","SET_ORIGIN_ERROR"));
        destinationCountry&&dispatch(promiseAction(dispatch,covidData(destinationCountry),"SET_COVID_PROMISE","SET_COVID_DATA","SET_COVID_ERROR"));
    },[dispatch,originCountry,destinationCountry]);

    return (
        promiseNoData(originPromise,originCountryCovid,originError)||
        promiseNoData(cPromise,cData,cError)||
        h(FlightsDetailsView,{
            originCountry,
            destinationCountry,
            originData:originCountryCovid,
            destinationData:cData,
            trip,
            hasFlightBeenSaved:savedFlights.find(existing=>isEqual(existing,trip)),
            addFlight:()=>{
                dispatch({type:"ADD_FLIGHT",value:trip});
                db.ref('/savedFlights/'+currentUser.user.uid).push(trip);
                alert("Your trip has been saved to my flights.");
            },
            navback:()=>history.goBack()
        }))
}

export {FlightsDetails};