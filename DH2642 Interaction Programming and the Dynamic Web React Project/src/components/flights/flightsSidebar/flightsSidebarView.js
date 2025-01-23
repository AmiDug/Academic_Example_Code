import './flightsSidebar.css'

const FlightsSidebarView=({coronaConcern, setCoronaConcern, originAirports, setPreferredOriginAirport, destinationAirports, setPreferredDestinationAirport, searchResultsAirlines, setPreferredAirline, setPassengerQuantity, setCurrency, currencySymbol, setCurrencySymbol, minPrice, setMinPrice,maxPrice,setMaxPrice, sortingPreference, setSortingPreference})=>
<div className="flightsSidebarContents">


        <div className="heading">Filter your search</div>

        {/*Corona-related filter.*/}
        <div className="filterCategory">
            <div className="subHeading">Coronavirus concerns</div>

            <div className="flightsSidebarFilterSection">
                <div><label htmlFor="concern">How worried are you about Covid-19?</label></div>
                <div className="rowFix">
                  <div>Not at all</div>
                  <input type="range" id="concern" min="1" max="5" defaultValue={coronaConcern} onMouseUp={event=>setCoronaConcern(event.target.value)}></input>
                  <div>Very</div>
                </div>
            </div>
        </div>

        {/* Non-corona filters */}
        <div className="filterCategory">
            <div className="subHeading">Flight criteria</div>
 
            <div className="flightsSidebarFilterSection">
                <div className="rowFix">
                    <div className="filterName">Preferred airline:</div>
                        <div className="middleSpaceHolder"></div>
                        <select defaultValue="Any" className="userInputDropdown" id="airlineDropdownList" onChange={event=>setPreferredAirline(event.target.value)}>
                            <option value="Any" key="Any">Any</option>
                            { searchResultsAirlines.map(airlineName=>
                                <option value={airlineName} key={airlineName}>{airlineName}</option>
                            )}
                        </select>
                    </div>
            </div>

            <div className="flightsSidebarFilterSection" id="preferredAirports">
                <div className="subSubheading">Preferred airports</div>
                <div className="rowFix">
                    <div>Takeoff:</div>
                    <div className="middleSpaceHolder"></div>
                    <select defaultValue="Any" className="userInputDropdown" id="preferredOriginAirport" onChange={event=>setPreferredOriginAirport(event.target.value)}>
                        <option value="Any" key="Any">Any</option>
                        {originAirports.map(airport=><option value={airport} key={airport}>{airport}</option>)}
                    </select>
                </div>

                <div className="rowFix">
                <div>Destination:</div>
                        <div className="middleSpaceHolder"></div>
                        <select defaultValue="Any" className="userInputDropdown" id="preferredDestinationAirport" onChange={event=>setPreferredDestinationAirport(event.target.value)}>
                            <option value="Any" key="Any">Any</option>
                            {destinationAirports.map(airport=><option value={airport} key={airport}>{airport}</option>)}
                        </select>
                </div>
            </div>


            <div className="flightsSidebarFilterSection rowFix">
                <div className="filterName">Passengers:</div>
                    <div className="middleSpaceHolder"></div>
                    <img className="flightsSidebarIcon" src="/person_icon_freepik_via_flaticon_1077114.svg" alt="Passenger icon"></img>
                    <input className="userInputDropdown" type="number" id="passengerQuantity"   name="passengerQuantity"   min="1" max="180" defaultValue="1"
                        onInput={event=>setPassengerQuantity(event.target.value)}>
                    </input>
            </div>

            {/* Currency drop-down menu. SEK is default. Hard-coded subset of the API's currencies.
            I Manually checked that it matches what's available in the SkyScanner API. - Oliver
            */}
            <div className="flightsSidebarFilterSection rowFix">
                <div className="filterName">Currency:</div>
                    <div className="middleSpaceHolder"></div>
                    <select defaultValue="SEK" className="userInputDropdown" id="currencyDropdownList" onChange={event=>{setCurrency(event.target.value); setCurrencySymbol(event.target.value)}}>
                        {["AED", "BAM", "BGN", "CHF", "CZK", "DKK", "EUR", "GBP", "GEL", "HRK", "HUF", "ILS", "MKD", "NOK", "PLN", "RON", "RSD", "RUB", "SEK", "UAH", "USD"]
                        .map(currency => <option value={currency} key={currency}>{currency}</option>
                        )}
                    </select>
            </div>

            <div className="flightsSidebarFilterSection">
                <div className="filterName">Price range:</div>
                <div className="priceRangeFilter rowFix">
                    <input type="number" id="minPriceInput" className="userInputNumberfield" min="0" max={maxPrice} defaultValue={minPrice}
                        onChange={event=>setMinPrice(event.target.value)}
                        onBlur={event=>{
                            event.target.stepDown();
                            event.target.stepUp();
                            setMinPrice(event.target.value);
                        }}>
                    </input>
                    <div className="spanningText">to</div>
                    <input type="number" id="maxPriceInput" className="userInputNumberfield" min={minPrice} max="100000" defaultValue={maxPrice}
                        onChange={event=>setMaxPrice(event.target.value)}
                        onBlur={event=>{
                            event.target.stepUp();
                            event.target.stepDown();
                            setMaxPrice(event.target.value);
                        }}>
                    </input>
                    <div className="spanningText">{currencySymbol}</div>
                </div>
            </div>

            <div className="flightsSidebarFilterSection" id="sortingPreference">
            <div className="subSubheading">Sort results</div>
            <div className="rowFix">
                <div className="filterName">Display flights by</div>
                <div className="middleSpaceHolder"></div>
                <select defaultValue={sortingPreference} className="userInputDropdown" id="sortingPreferenceDropdownList" onChange={event=>setSortingPreference(event.target.value)}>{/*NEEDS onCHANGE or similar, to set the prop that tells the actual comparison function which to be.*/}
                    <option value="Date" key="Date">Date</option>
                    <option value="Price" key="Price">Price</option>
                </select>
            </div>
            </div>

        </div>

</div>

export {FlightsSidebarView};
