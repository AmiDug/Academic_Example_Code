import './login.css';

const LoginView=({credentialsError,signup,signin,passType,passHide,setMode,submitHandler})=>
  <div id="shader">
    <div id="container">
      <div id="loginSpace">
        <div id="header">Safe Travels</div>
        <form id="signupForm" onSubmit={submitHandler}>
          <div className="inputField">
            <div id="emailLabel">Email</div>
            <input type="email" id="signupEmail" placeholder="Your email adress" required />
          </div>
          <div className="inputField">
            <div id="passwordLabel">Password</div>
            <input type={passType} id="signupPassword" placeholder="Your password" required />
            <input type="checkbox" id="checkBoxPass" name="checkBoxPass" onClick={passHide}/>
            <label htmlFor="checkBoxPass">Show Password</label>
          </div>
          <button type="submit" onClick={()=>setMode(signin)} id="login">Login</button>
          <button type="submit" onClick={()=>setMode(signup)} id="signup">Sign Up</button>
        </form>
      </div>
      {credentialsError? <div id="error">{credentialsError}</div>: false}
   </div>
  </div>;
export {LoginView};
