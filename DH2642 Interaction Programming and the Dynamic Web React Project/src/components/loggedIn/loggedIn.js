import {useSelector} from 'react-redux';

function LoggedIn({value,children}){
    const {currentUser}=useSelector(state=>state.loginReducer);

    return (currentUser&&value)||(!currentUser&&!value)? children:false;
}

export {LoggedIn};