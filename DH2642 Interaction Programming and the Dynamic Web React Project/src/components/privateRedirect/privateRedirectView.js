import {Redirect} from 'react-router-dom';

const PrivateRedirectView=({isNewUser})=>
    <Redirect to={isNewUser?"/account":"/search"}/>

export {PrivateRedirectView};