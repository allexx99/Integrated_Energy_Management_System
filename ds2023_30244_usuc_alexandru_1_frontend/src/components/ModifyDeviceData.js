import React from "react";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { Navigate, useNavigate } from "react-router-dom";
import { Button } from "@material-ui/core";

const ModifyDeviceData = () => {
  const { id } = useParams();

  const [device, setDevice] = useState({});
  const [name, setName] = useState('');
  const [type, setType] = useState('');

  const navigate = useNavigate("");

  async function getDeviceById() {
    await axios
      .get("http://localhost:8081/readDevices/" + id, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        setDevice(response.data);
        setName(response.data.name);
        setType(response.data.type);
      });
  }

  useEffect(() => {
    getDeviceById();
  }, []);

  async function handleUpdateData() {
    await axios
      .put("http://localhost:8081/updateDevice/" + id, {
        name: name,
        type: type
      }, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      // .then((response) => alert(response.data));

    navigate(-1);
    // navigate("/admin");
  }

  return (
    <div className="modify-data">
      <h2>Modify Device Data</h2>
      {device && (
        <form onSubmit={handleUpdateData}>
          <label>Name:</label>
          <input
            type="text"
            defaultValue={name}
            onChange={(event) => setName(event.target.value)}
          />
          <br></br>
          <label>Type:</label>
          <input
            type="text"
            defaultValue={type}
            onChange={(event) => setType(event.target.value)}
          />

          <button>Update</button>
        </form>
      )}
    </div>
  );
};

export default ModifyDeviceData;
