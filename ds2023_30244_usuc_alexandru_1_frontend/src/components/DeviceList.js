import React from "react";
import { Link } from "react-router-dom";
import { Button } from "@material-ui/core"; //importing material ui component
import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const DeviceList = ({ devices }) => {

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
  
  async function handleDeleteDevice(id) {
    await axios
      .delete("http://localhost:8081/deleteDevice/" + id, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => alert(response.data));
    window.location.reload();
  }

  return ( 
    <div className="blog-list">
      <br></br>
      <h2>Device List</h2>

      {devices.map((deviceObj) => (
        <div className="blog-preview" key={deviceObj.id}>
          <Link to={`/devices/${deviceObj.id}`}>
            <h3>Id: {deviceObj.id}</h3>
            <h5>Name: {deviceObj.name}</h5>
            <h5>Type: {deviceObj.type}</h5>
            {/* <p>Starting bid: { postObj.bid }</p> */}
          </Link>
          <Button onClick={() => handleDeleteDevice(deviceObj.id)}>Delete</Button>
        </div>
      ))}
    </div>
   );
}
 
export default DeviceList;