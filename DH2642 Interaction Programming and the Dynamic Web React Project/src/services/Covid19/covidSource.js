import API_KEY from '../apiConfig';

export {
    covidData
};

function covidData(country){
    return fetch(`https://covid-193.p.rapidapi.com/statistics?country=${country}`, {
        "method": "GET",
        "headers": {
            "x-rapidapi-key": API_KEY,
            "x-rapidapi-host": "covid-193.p.rapidapi.com"
        }
    })
    .then(response=>{
        if(response.ok){
            return response;
        }
        throw Error(response.statusText);
    })
    .then(response => response.json())
    .then(response=>response.response[0]);
}