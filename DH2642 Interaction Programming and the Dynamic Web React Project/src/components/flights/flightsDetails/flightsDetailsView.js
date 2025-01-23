import './FlightsDetailsView.css';

const FlightsDetailsView=({originCountry,destinationCountry,originData,destinationData,trip,hasFlightBeenSaved,addFlight,navback})=>
<div>
  <button id="nav" onClick={addFlight} disabled={hasFlightBeenSaved}>Save to my flights</button>
  <button id="nav" onClick={navback}>Return</button>
  <div className="detailsCard">
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
        {trip.destinationRisk?
        <div className="riskSummary">
            <div>Destination infection level: {trip.destinationRisk}</div>
        </div>:false}
    </div>
  </div>
  <div id="Container">
    
    <div id="Contents">
    <div name="Departure" className="CountryInfo">
      <h1>{originCountry}</h1>
      <div className="TextBox">
        Here is a summary for the country you are <b>departing</b> from.
        You are travelling from {originCountry} which lies in {originData.continent}.
        {originData.cases["active"]?
        <span>
        The amount of active cases of Covid-19 are {originData.cases["active"]}.
        See the statistics for more information.
        </span>
        :
        <span> There is no data for active cases.</span>}
      </div>
      <div className="Statistics">
        <table>
          <thead>
            <tr>
              <th>Name</th><th>Amount</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Population</td>
              <td>{originData.population}</td>
            </tr>
            <tr>
              <td>Infected per million</td>
              <td>{originData.cases["1M_pop"]}</td>
            </tr>
            <tr>
              <td>Deaths per million</td>
              <td>{originData.deaths["1M_pop"]}</td>
            </tr>
            <tr>
              <td>Total infected</td>
              <td>{originData.cases["total"]}</td>
            </tr>
        </tbody>
        </table>
      </div>
    </div>

    <div name="Arrival" className="CountryInfo">
      <h1>{destinationCountry}</h1>
      <div className="TextBox">
        Here is a summary for the country you are <b>arriving</b> to.
        You are travelling from {destinationCountry} which lies in {destinationData.continent}.
        {destinationData.cases["active"]?
        <span>
        The amount of active cases of Covid-19 are {destinationData.cases["active"]}.
        See the statistics for more information.
        </span>
        :
        <span> There is no data for active cases.</span>}
      </div>
      <div className="Statistics">
        <table>
          <thead>
            <tr>
              <th>Type</th><th>Value</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Population</td>
              <td>{destinationData.population}</td>
            </tr>
            <tr>
              <td>Infected per million</td>
              <td>{destinationData.cases["1M_pop"]}</td>
            </tr>
            <tr>
              <td>Deaths per million</td>
              <td>{destinationData.deaths["1M_pop"]}</td>
            </tr>
            <tr>
              <td>Total infected</td>
              <td>{destinationData.cases["total"]}</td>
            </tr>
        </tbody>
        </table>
      </div>
    </div>
  </div>
  </div>
</div>;

export {FlightsDetailsView};
