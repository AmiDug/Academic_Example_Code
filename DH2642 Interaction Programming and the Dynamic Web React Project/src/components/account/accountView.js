import './account.css';
import {todayXYearsAgo} from '../../helper/dateParser';

const AccountView=({username,dateOfBirth,healthIssues,coronaConcern,handleSubmit,signout})=>
        <div className="accountSettings">
            <h1>Logged in as: {username}</h1>
            <div className="accountSettingsContent">
                <div>
                    <h2>Personal Settings</h2>
                    <form onSubmit={e=>handleSubmit(e)}>
                        <div className="accountSettingsSection">
                            <div className="accountSettingsEntry"><label htmlFor="birth">Date of birth:</label></div>
                            <div className="accountSettingsEntry">
                                <input 
                                    type="date" 
                                    id="birth" 
                                    max={todayXYearsAgo(1)} 
                                    defaultValue={dateOfBirth}/>
                            </div>
                        </div>
                        <div className="accountSettingsSection">
                            <div className="accountSettingsEntry">Check if you suffer from any of the following:</div>
                            {["Respiratory issues","Diabetes","Heart disease","Hypertension"].map((issue,i)=>
                            <div className="accountSettingsEntry" key={issue}>
                                <input type="checkbox" id={issue} defaultChecked={healthIssues[i]}/>
                                <label htmlFor={issue}>{issue}</label>
                            </div>
                            )}
                        </div>
                        <div className="accountSettingsSection">
                            <div className="accountSettingsEntry">
                                <label htmlFor="concern">How worried are you about Covid19?</label>
                                <span className="tooltip" data-tooltip="This affects the sensitivity level of your search results."><b>?</b></span>
                            </div>
                            <div className="accountSettingsEntry">
                                Not at all <input type="range" id="concern" min="1" max="5" defaultValue={coronaConcern}/> Very
                            </div>
                        </div>
                        <button className="submitButton" type="submit">Save Changes</button>
                    </form>
                    <div className="accountSettingsSection">
                        <button className="submitButton" onClick={signout}>Sign Out</button>
                    </div>
                </div>
            </div>
            
        </div>;

export {AccountView};