import {useSelector,useDispatch} from 'react-redux';
import {LoginView} from './loginView';
import {createElement as h} from 'react';
import {credentialsAction} from '../../actions';
import {signup,signin} from '../../helper/auth';

function Login() {
    const {credentialsError,mode,passType}=useSelector(state=>state.loginReducer);
    const dispatch = useDispatch();

    return h(LoginView, {
        credentialsError,
        signup,
        signin,
        passType,
        passHide:()=>dispatch({type:"TOGGLE_PASS_VISIBILITY"}),
        setMode:mode=>dispatch({type:"SET_MODE",value:mode}),
        submitHandler:e=>{
            e.preventDefault();
            credentialsAction(dispatch,e.target.signupEmail.value,e.target.signupPassword.value,mode);
        }
    });
}

export {Login};