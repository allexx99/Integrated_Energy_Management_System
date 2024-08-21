import React from "react";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { Navigate, useNavigate } from "react-router-dom";
import { Button } from "@material-ui/core";

const ModifyUserData = () => {

  const { id } = useParams();
  
  const [user, setUser] = useState({});
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [age, setAge] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate('');

  async function getUserById() {
    await axios.get("http://localhost:8080/readUsers/" + id, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
    .then((response) => {
      setUser(response.data);
      setFirstName(response.data.firstName);
      setLastName(response.data.lastName);
      setEmail(response.data.email);
      setAge(response.data.age);
      setUsername(response.data.username);
      setPassword(response.data.password);
      // console.log(response.data.email);
    })
  }

  useEffect(() => {
    getUserById();
  }, [])

  async function handleUpdateData() {

    console.log("new first name: " + firstName);
    await axios.put("http://localhost:8080/updateUser/" + id, {
        firstName: firstName,
        lastName: lastName,
        username: username,
        password: password,
        email: email,
        age: age
    }, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })// .then(response => alert(response.data))

    navigate(-1);
    // navigate("/admin");

    // setTimeout(() => {
    //   navigate(-1);
    // }, 0);
  }

  return ( 
    <div className="modify-data">
      <h2>Modify User Data</h2>
      { user && (
        <form onSubmit={handleUpdateData}>
          <label>First Name:</label>
          <input 
            type="text"
            defaultValue={firstName}
            onChange={(event) => setFirstName(event.target.value)}
          />
          <br></br>
          <label>Last Name:</label>
          <input
          type="text"
          defaultValue={ user.lastName }
          onChange={(event) => setLastName(event.target.value)}
          />
          <br></br>
          <label>Age:</label>
          <input
          type="number"
          defaultValue={ user.age }
          onChange={(event) => setAge(event.target.value)}
          />
          <br></br>
          <label>Username:</label>
          <input
            type="text"
            defaultValue={ user.username }
            onChange={(event) => {
              setUsername(event.target.value)
              // localStorage.setItem("username", user.username)
            }}
          />
          <br></br>   
          <label>Email:</label>
          <input
            type="text"
            defaultValue={ user.email }
            onChange={(event) => setEmail(event.target.value)}
          />
          <br></br>
          <label>Password:</label>
          <input
            type="text"
            readOnly
            defaultValue={ user.password }
            onChange={(event) => setPassword(event.target.value)}
          />
          <br></br>
          <button>Update</button>
        </form>
        
      ) }
    </div>
   );
}
 
export default ModifyUserData;