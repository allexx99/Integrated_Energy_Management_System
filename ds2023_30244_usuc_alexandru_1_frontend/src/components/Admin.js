import { useState, useEffect } from "react";
import axios from "axios";
import UserList from "./UserList";
import { Button } from "@material-ui/core";
import { Link } from "react-router-dom";
import NewDevice from "./NewDevice";
import { useNavigate } from "react-router-dom";

const Admin = () => {
  const username = localStorage.getItem("username");

  const [users, setUsers] = useState([]);

  const navigate = useNavigate("");
  
  const isAdmin = () => {
    return localStorage.getItem("usertype") === "admin";
  };

  // Check for admin access when the component mounts
  // useEffect(() => {
  //   if (!isAdmin()) {
  //     // Redirect or display an error message
  //     alert("Unauthorized access!");
  //     // Redirect to the home page or another appropriate page
  //     navigate("/");
  //   }
  // }, []); // Empty dependency array ensures that the effect runs only once


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

  useEffect(() => {
    users.forEach((userObj) => {
      console.log(userObj.firstName);
    });
  }, [users]); // Runs whenever 'users' state changes

  return (
    <div className="admin">
      <br></br>
      {username !== "" ? <h3>Welcome, {username}</h3> : <br></br>}

      <div className="button-container">
        <Link to="newuser">
          <div className="addUserButton">
            <Button>Add User</Button>
            {/* <Link to="newUser">Add User</Link> */}
          </div>
        </Link>

        <Link to="newdevice">
          <div className="addDeviceButton">
            <Button>Add Device</Button>
          </div>
        </Link>
      </div>

      <Link to="devices">
        <div className="device-list">
          <Button>Device List</Button>
          {/* <Link to="newUser">Add User</Link> */}
        </div>
      </Link>

      {users && <UserList users={users} />}
    </div>
  );
};

export default Admin;