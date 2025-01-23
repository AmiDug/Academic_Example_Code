import {useSelector} from 'react-redux';
import {createElement as h} from 'react';
import {PrivateRedirectView} from './privateRedirectView';

function PrivateRedirect(){
    const {currentUser}=useSelector(state=>state.loginReducer);

    return h(PrivateRedirectView,{
        isNewUser:currentUser.additionalUserInfo.isNewUser
    })
}

export {PrivateRedirect};