import {useSelector,useDispatch} from 'react-redux';
import {createElement as h} from 'react';
import {useHistory} from 'react-router-dom';
import {MyFlightsView} from './myFlightsView';
import {db} from '../../services/firebase';
import {isEqual} from 'lodash';
import {countryAction} from '../../actions';
import {placeData} from '../../services/SkyScannerFlightSearch';

function MyFlights(){
    const {savedFlights}=useSelector(state=>state.currentReducer);
    const {sortingMethod,sortOrder}=useSelector(state=>state.sortingReducer);
    const {currentUser}=useSelector(state=>state.loginReducer);
    const history=useHistory();
    const dispatch=useDispatch();

    return h(MyFlightsView,{
        savedFlights,
        sortingMethod,
        sortOrder,
        setSorting:(x)=>{
            dispatch({type:"SET_SORTING_METHOD", value: x});
            if(x===sortingMethod){
                dispatch({type:"TOGGLE_ORDER"});
            }
            else{
                dispatch({type:"DEFAULT_ORDER"});
            }
        },
        removeFlight:flight=>{
            dispatch({type:"REMOVE_FLIGHT",value:flight});
            db.ref('/savedFlights/'+currentUser.user.uid).once("value",(snapshot)=>{
                snapshot.forEach(snap=>{
                    const val=snap.val();
                    if(isEqual(val,flight)){
                        snap.ref.remove();
                    }
                });
            });
        },
        handleRowClick:flight=>{
            dispatch({type:"SET_TRIP",value:flight});
            dispatch(countryAction(dispatch,flight.origin[1],placeData(flight.origin[1]),"SET_FROM","SET_ORIGIN_COUNTRY"));
            dispatch(countryAction(dispatch,flight.destination[1],placeData(flight.destination[1]),"SET_TO","SET_DESTINATION_COUNTRY"));
            history.push({
                pathname:'search/details',
            });
        }
    })
}

export {MyFlights};