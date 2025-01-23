import {Fragment} from 'react';
import {FlightsSearch} from './flightsSearch';
import {FlightsSidebar} from './flightsSidebar';
import {FlightsDetails} from './flightsDetails';
import { Redirect, Route, Switch} from 'react-router-dom';

const FlightsView=({path})=>
    <Fragment>
        <div className="dummySidebar"></div>
        <div className="sidebar"><FlightsSidebar/></div>
        <div className="mainContent">
            <Switch>
                <Route exact path={path}>
                    <FlightsSearch path={path}/>
                </Route>
                <Route path={`${path}/details`}>
                    <FlightsDetails/>
                </Route>
                <Redirect to={path}/>
            </Switch>
        </div>
    </Fragment>;

export {FlightsView};