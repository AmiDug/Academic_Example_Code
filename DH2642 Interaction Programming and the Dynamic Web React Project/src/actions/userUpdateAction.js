import {birthdateToAge} from '../helper/dateParser';

function userUpdateAction(dispatch,{dateOfBirth,respiratory,diabetes,heart,hypertension,coronaConcern}){
    let userRisk=0;

    dateOfBirth&&dispatch({type:"SET_DATE_OF_BIRTH",value:dateOfBirth});
    dispatch({type:"SET_RESPIRATORY",value:respiratory});
    dispatch({type:"SET_DIABETES",value:diabetes});
    dispatch({type:"SET_HEART",value:heart});
    dispatch({type:"SET_HYPERTENSION",value:hypertension});
    dispatch({type:"SET_CONCERN",value:coronaConcern});

    dateOfBirth&&birthdateToAge(dateOfBirth)>60&&userRisk++;
    respiratory&&userRisk++;
    diabetes&&userRisk++;
    heart&&userRisk++;
    hypertension&&userRisk++;

    return  {type:"SET_TOTAL_ISSUES",value:userRisk};
}

export {userUpdateAction};