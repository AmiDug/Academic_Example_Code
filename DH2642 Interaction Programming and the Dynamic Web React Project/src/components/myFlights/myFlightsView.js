import './myFlights.css';

function compareFlights(a,b,sortingMethod,sortOrder){
	let [ai,bi]=[a[sortingMethod],b[sortingMethod]];
	
	if(Array.isArray(ai)){
		[ai,bi]=[ai[1],bi[1]];
	}

	if(typeof ai==="string"){
		[ai,bi]=[ai.toLowerCase(),bi.toLowerCase()];
	}
	
	if (ai < bi){return -1*sortOrder;}
    else if (ai > bi){return sortOrder;}
    else if (ai === bi){return 0;}
}

const MyFlightsView=({savedFlights, sortingMethod, sortOrder, setSorting, removeFlight, handleRowClick})=>
    <div id="container">
		<span id="title">This is a summary of your saved flights. Use the headers to sort</span>
		<table id="savedFlights">
			<thead>
				<tr>
					<th className="sortButton" onClick={()=> setSorting("departureDate")}>Departure</th>
					<th className="sortButton" onClick={()=> setSorting("origin")}>Origin</th>
					<th className="sortButton" onClick={()=> setSorting("destination")}>Destination</th>
					<th className="sortButton" onClick={()=> setSorting("carrier")}>Carrier</th>
					<th className="sortButton" onClick={()=> setSorting("destinationRisk")}>Destination Risk</th>
					<th className="sortButton" onClick={()=>setSorting("price")}>Price</th>
				</tr>
			</thead>
			<tbody>
			{savedFlights.sort((a,b)=>compareFlights(a,b,sortingMethod,sortOrder)).map((flight,i)=>
				<tr key={i} className="rows" onClick={()=>handleRowClick(flight)}>
					<td>{flight.departureDate}</td>
					<td>{flight.origin[1]}</td>
					<td>{flight.destination[1]}</td>
					<td>{flight.carrier}</td>
					<td>{flight.destinationRisk}</td>
					<td>{flight.price} {flight.currency}</td>
					<td className="removeButton" onClick={e=>{
						e.stopPropagation();
						removeFlight(flight);
						}}><b>x</b></td>
				</tr>)}
			</tbody>

		</table>
    </div>;

export {MyFlightsView};
