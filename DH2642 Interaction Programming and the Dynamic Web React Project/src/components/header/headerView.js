import {Link} from 'react-router-dom';
import './header.css';
const HeaderView=()=>
        <div>
            <ul>
                <img id="logo" alt="logo" src="/logo-01.png"/>
                <li><Link to="/account">Account</Link></li>
                <li><Link to="/about">About</Link></li>
                <li><Link to="/myflights">My Flights</Link></li>
                <li><Link to="/search">Search</Link></li>
            </ul>
            <div className="dummyHeader"></div>
        </div>;

export {HeaderView};
