import {javascriptToHTML} from '../../../helper/dateParser';
import './flightsSearch.css';

const FlightsSearchFormView=({from,to,onSearch})=>
    <div className="searchBar">
        <form onSubmit={e=>onSearch(e)}>
            <div className="row">
                <input
                    id="from"
                    placeholder="From:"
                    spellCheck="false"
                    defaultValue={from}
                    required/>
                <input
                    id="to"
                    placeholder="To:"
                    spellCheck="false"
                    defaultValue={to}
                    required/>
                <div id="depContainer">
                  <label htmlFor="departure">Departure:</label>
                  <input type="date"
                      id="departure"
                      min={javascriptToHTML(new Date())}/>
                </div>
                <button type="submit">Search!</button>
            </div>
        </form>
    </div>;

export {FlightsSearchFormView};
