import {createElement as h} from 'react';
import {useRouteMatch} from 'react-router-dom';
import {FlightsView} from './flightsView';

function Flights(){
    const {path}=useRouteMatch();

    return h(FlightsView,{
        path,
    })
}

export {Flights};