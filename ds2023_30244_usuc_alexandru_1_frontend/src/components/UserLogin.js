import axios from "axios";
import { useState } from "react";
import React from "react";
import { useNavigate } from "react-router-dom";

const UserLogin = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate("");

  async function login(event) {
    event.preventDefault();

    // console.log("Username: " + username);
    // console.log("Password: " + password);

    await axios.post("http://localhost:8080/userAuthenticate",
    {
      username: username,
      password: password, 
    // }).then(response => alert(response.data)); // metoda din backend
    }).then((response) => {
      // username and password of the user is not displayed in console bc the backend sends a userDTO object in frontend whose username and password fields are empty for sequrity reaqsons.
      // localStorage.setItem("usernameUser", response.data.username);
      // console.log("Username from local storage is: " + localStorage.getItem("usernameUser"));
      localStorage.setItem("username", response.data.username);
      localStorage.setItem("usertype", "user");
      localStorage.setItem("token", response.data.token);
      if(response.data.username === undefined) {
        alert("username or password incorrect");
        // navigate("/");
        window.location.reload();
      } else {
        navigate("/users/" + response.data.id);
      }
      // navigate("/users/" + response.data.id);
    });

  }
  return ( 
    <div className="login">
      <h1>User Login</h1>
      <br></br>
      <form onSubmit={login}>
        <label>Username</label>
        <input
          type="text"
          placeholder="Enter username..."
          value={username} // <-- this is set below
          onChange={(event) => setUsername(event.target.value)} // onChange uses setFirstName function to set the "firstName" value from above
        />

        <label>Password</label>
        <input
          type="text"
          placeholder="Enter password..."
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />

        <button>Login</button>
      </form>
    </div>
   );
}
 
export default UserLogin;