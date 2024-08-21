import axios from "axios";
import { useState } from "react";
import React from "react";
import { useNavigate } from "react-router-dom";

const AdminLogin = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate("");

  async function login(event) {
    event.preventDefault();

    console.log("Username: " + username);
    console.log("Password: " + password);

    await axios.post("http://localhost:8080/adminAuthenticate",
    {
      username: username,
      password: password,
    // }).then(response => alert(response.data)); // metoda din backend
    }).then((response) => {
      localStorage.setItem("username", response.data.username);
      localStorage.setItem("usertype", "admin");
      localStorage.setItem("token", response.data.token);
      if(response.data.username === undefined) {
        alert("username or password incorrect");
        // navigate("/");
        window.location.reload();
      } else {
        navigate("/Admin");
      }
    });

    // await axios
    //   .get("http://localhost:8080/loginadmin", {
    //     username: username,
    //     password: password,
    //   })
    //   .then((response) => {
    //     // localStorage.setItem("username", response.data.username);
    //     // localStorage.setItem("clientId", response.data.id);
    //     // console.log("is in login ", response.data);
    //     // console.log("logged in with id " + localStorage.getItem("clientId"));
    //     // alert(response.data.username);
    //   });

    // alert("Client Registration Successful");
    // navigate("/Admin");
  }

  return ( 
    <div className="login">
      <h1>Admin Login</h1>
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
 
export default AdminLogin;