import React from 'react';
import {Redirect, Route,Switch} from 'react-router-dom';
import {Login} from './components/login';
import {Header} from './components/header';
import {Flights} from './components/flights';
import {Account} from './components/account';
import {About} from './components/about';
import {MyFlights} from './components/myFlights';
import {LoggedIn} from './components/loggedIn';
import {PrivateRedirect} from './components/privateRedirect';
import './App.css';

function App() {
  return (
    <div className="App">
      <LoggedIn value={true}>
        <div>
          <Header/>
          <div className="flexParent">
            <Switch>
              <Route path="/search">
                  <Flights/>
              </Route>
              <Route path="/myflights">
                  <MyFlights/>
              </Route>
              <Route path="/about">
                <About/>
              </Route>
              <Route path="/account">
                  <Account/>
              </Route>
              <PrivateRedirect/>
            </Switch>
          </div>
        </div>
      </LoggedIn>
      <LoggedIn value={false}>
        <Switch>
          <Route exact path="/">
            <Login/>
          </Route>
          <Redirect to="/"/>
        </Switch>
      </LoggedIn>
    </div>
  );
}
export default App;
