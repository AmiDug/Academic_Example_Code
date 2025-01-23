function promiseAction(dispatch,promise,actionType,actionTypeData,actionTypeError){
    dispatch({type:actionTypeData,value:null});
    dispatch({type:actionTypeError,value:null});
    if(!promise){
        return {type:actionType,value:null};
    }
    promise.then(data=>dispatch({type:actionTypeData,value:data}))
        .catch(error=>dispatch({type:actionTypeError,value:error}));
    return  {type:actionType,value:{}};
}

export {promiseAction};