import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const NewUser = () => {
  
  const navigate = useNavigate("");
  
  const isAdmin = () => {
    return localStorage.getItem("usertype") === "admin";
  };

  // Check for admin access when the component mounts
  useEffect(() => {
    if (!isAdmin()) {
      // Redirect or display an error message
      alert("Unauthorized access!");
      // Redirect to the home page or another appropriate page
      navigate("/");
    }
  }, []); // Empty dependency array ensures that the effect runs only once

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [age, setAge] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();

    await axios.post("http://localhost:8080/saveUser", {
      firstName: firstName,
      lastName: lastName,
      age: age,
      email: email,
      username: username,
      password: password,
    }, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }); //.then(response => alert(response.data));

    navigate("/Admin");
  }

  return (
    <div className="new-user">
      <h2>New User</h2>
      <br />
      <form onSubmit={handleSubmit}>
        <label>First Name: </label>
        <input
          type="text"
          required
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
        <br />

        <label>Last Name: </label>
        <input
          type="text"
          required
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
        <br />

        <label>Age: </label>
        <input
          type="number"
          required
          min="0"
          value={age}
          onChange={(e) => setAge(e.target.value)}
        />
        <br />

        <label>Email: </label>
        <input
          type="text"
          required
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <br />

        <label>Username: </label>
        <input
          type="text"
          required
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <br />

        <label>Password: </label>
        <input
          type="text"
          required
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <br />

        <button>Add User</button>
      </form>
    </div>
  );
};

export default NewUser;
