import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const NewDevice = () => {
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

  const [name, setName] = useState("");
  const [type, setType] = useState("");
  const [idUser, setIdUser] = useState("");
  const [users, setUsers] = useState([]);

  // console.log(users);

  async function handleSubmit(event) {
    event.preventDefault();

    await axios.post("http://localhost:8081/saveToMyDeviceList/" + idUser, {
      name: name,
      type: type,
    }, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }); //.then(response => alert(response.data));

    navigate("/Admin");
  }

  useEffect(() => {
    // Fetch the posts from an API or any data source
    axios
      .get("http://localhost:8080/readUsers", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        setUsers(response.data);
        // console.log(response.data);
        // setPosts(response.data);
        // setFilteredPosts(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className="new-device">
      <br></br>
      <h2>New Device</h2>
      <br />
      <form onSubmit={handleSubmit}>
        <label>Name: </label>
        <input
          type="text"
          required
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <br />

        <label>Type: </label>
        <input
          type="text"
          required
          value={type}
          onChange={(e) => setType(e.target.value)}
        />
        <br />

        {/* <label>Id User: </label> */}
        {/* <input
          type="number"
          required
          value={idUser}
          onChange={(e) => setIdUser(e.target.value)}
        /> */}
        {users && users.length > 0 && (
          <>
            <label>Select User: </label>
            <select
              value={idUser}
              onChange={(e) => setIdUser(e.target.value)}
              required
            >
              <option value="">Select a user</option>
              {users.map((user) => (
                <option key={user.id} value={user.id}>
                  {user.id} - {user.username}
                </option>
              ))}
            </select>
            <br />
          </>
        )}

        <button>Add Device</button>
      </form>
    </div>
  );
};

export default NewDevice;
