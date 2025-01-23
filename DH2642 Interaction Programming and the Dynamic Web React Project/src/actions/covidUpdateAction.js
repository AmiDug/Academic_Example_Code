function covidUpdateAction(dispatch,promise,coronaConcern,userIssues){
    dispatch({type:"SET_COVID_DATA",value:null});
    dispatch({type:"SET_COVID_ERROR",value:null});
    if(!promise){
        return {type:"SET_COVID_PROMISE",value:null};
    }
    promise.then(data=>{
        dispatch({type:"SET_COVID_DATA",value:data});
        const destinationRisk=data.cases["1M_pop"]>30000? 5:
            data.cases["1M_pop"]>20000? 4:
            data.cases["1M_pop"]>10000? 3:
            data.cases["1M_pop"]>5000? 2:1;
        const combinedRisk=destinationRisk+(userIssues*2);
        const concernAdjusted=((coronaConcern-1)/4)*combinedRisk;
        const riskFactor=concernAdjusted>6? "Very High":
            concernAdjusted>4? "High":
            concernAdjusted>2? "Low":
            concernAdjusted>0? "Very Low":null;
        dispatch({type:"SET_RISK_FACTOR",value:{
            riskFactor,
            destinationRisk,
            userIssues,
            coronaConcern
        }});
    })
        .catch(error=>dispatch({type:"SET_COVID_ERROR",value:error}));
    return  {type:"SET_COVID_PROMISE",value:{}};
}

export {covidUpdateAction};