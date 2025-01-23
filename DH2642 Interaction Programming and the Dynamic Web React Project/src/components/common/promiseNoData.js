function promiseNoData(promise, data, error,errorType){  
    return  (!promise? " ":
            error&&errorType==="flightSearch"? <h1>Please try again later.</h1>:
            error? <h1>{error.message}</h1>:
            !data? <div className="loadingAnimation"><img src="/animated_inkling_spinner.gif" className="loadingAnimation" alt="loading"/></div>:
            false
    );
}

export {promiseNoData};