import {useSelector,useDispatch} from 'react-redux';
import {AccountView} from './accountView';
import {createElement as h} from 'react';
import {userUpdateAction} from '../../actions';
import {db} from '../../services/firebase';
import {signout} from '../../helper/auth';

function Account() {
    const {dateOfBirth,respiratory,diabetes,heart,hypertension,coronaConcern}=useSelector(state=>state.userReducer);
    const {currentUser}=useSelector(state=>state.loginReducer);
    const dispatch=useDispatch();

    return h(AccountView, {
        username:currentUser.user.email,
        dateOfBirth,
        healthIssues:[respiratory,diabetes,heart,hypertension],
        coronaConcern,
        handleSubmit:e=>{
            e.preventDefault();
            const formParams={
                dateOfBirth:e.target.birth.value,
                respiratory:e.target["Respiratory issues"].checked,
                diabetes:e.target["Diabetes"].checked,
                heart:e.target["Heart disease"].checked,
                hypertension:e.target["Hypertension"].checked,
                coronaConcern:e.target.concern.value
            };
            dispatch(userUpdateAction(dispatch,formParams));
            db.ref('/settings/'+currentUser.user.uid).set(formParams);
            alert("Your changes have been saved!");
        },
        signout:()=>{
            signout();
            dispatch({type:"USER_LOGOUT"});
        }
    });
}

export {Account};