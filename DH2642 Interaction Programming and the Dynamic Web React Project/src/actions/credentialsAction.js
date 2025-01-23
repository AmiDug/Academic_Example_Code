import {db} from '../services/firebase';
import {userUpdateAction} from './';

function credentialsAction(dispatch,email,password,mode){
    mode(email,password).then(user=>{
        dispatch({type:"SET_CURRENT_USER",value:user});
        try{
            db.ref('/settings/'+user.user.uid).once("value",(snapshot)=>{
                if(snapshot.val()){
                    dispatch(userUpdateAction(dispatch,snapshot.val()));
                }
            });
            db.ref('/savedFlights/'+user.user.uid).once("value",(snapshot)=>{
                snapshot.forEach(snap=>{
                    const val=snap.val();
                    dispatch({type:"ADD_FLIGHT",value:val});
                });
            });
        }
        catch(error){
            console.log(error)
        }
    })
        .catch(e=>dispatch({type:"SET_CREDENTIALS_ERROR",value:e.message}));
}

export {credentialsAction};