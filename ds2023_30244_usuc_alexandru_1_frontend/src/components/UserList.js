import React from "react";
import { Link } from "react-router-dom";
import { Button } from "@material-ui/core"; //importing material ui component
import axios from "axios";

const UserList = ({ users }) => {

  async function handleDeleteUser(id) {
    await axios
      .delete("http://localhost:8080/deleteUser/" + id, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => alert(response.data));
    window.location.reload();
  }

  return (
    <div className="blog-list">
      {users.map((userObj) => (
        <div className="blog-preview" key={userObj.id}>
          <Link to={`/users/${userObj.id}`}>
            <h3>Id: {userObj.id}</h3>
            <h5>First name: {userObj.firstName}</h5>
            <h5>Last name: {userObj.lastName}</h5>
            {/* <p>Starting bid: { postObj.bid }</p> */}
          </Link>
          <Button onClick={() => handleDeleteUser(userObj.id)}>Delete</Button>
        </div>
      ))}
    </div>
  );
};

export default UserList;
