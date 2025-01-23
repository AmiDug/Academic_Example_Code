import './about.css';

const AboutView=()=>
  <div className="settings">
    <h1>About Us</h1>
    <div className="settingsContent">
      <div className="settingsSection">
        <div className="settingsEntry">
          <p>Welcome to <i>Safe Travels</i>!</p>
           <p>This website was made as part of a project for a university course in interaction programming. <br/>
           It allows you to search for real flights and get risk factor estimations related to the Covid-19 pandemic
           depending on where you wish to travel and how worried you are about falling ill.</p>
           <p>How the app works:</p>
           <p>In your account settings, check each box that corresponds to a health issue you are currently suffering from. <br/>
             For each issue, the app increases a value known as the "user risk level". This value is also affected by
             your age. <br/> Whenever a search is made, the app uses your user risk level as well as the infection rate of the
             destination country of your search, in order to calculate a risk factor for the trip. The sensitivity level
             of the risk factor can be adjusted depending on how concerned you are about the Covid-19 pandemic. At the lowest
             level, the risk factor will not even be displayed. The level of concern can be saved permanently in your account
             settings, but you may also change it temporarily while you search.
           </p>
        </div>

         <div className="attributionText">Some icons made by
           <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a>
           from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a>
         </div>
      </div>
    </div>
  </div>;

export {AboutView};
