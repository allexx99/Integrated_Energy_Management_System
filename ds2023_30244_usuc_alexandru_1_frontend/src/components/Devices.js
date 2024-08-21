import { useState, useEffect } from "react";
import axios from "axios";
import UserList from "./UserList";
import { Button } from "@material-ui/core";
import { Link } from "react-router-dom";
import DeviceList from "./DeviceList";

const Devices = () => {
  // const usernameAdmin = localStorage.getItem("usernameAdmin");

  const [devices, setDevices] = useState([]);

  useEffect(() => {
    // Fetch the posts from an API or any data source
    axios
      .get("http://localhost:8081/readDevices", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        setDevices(response.data);

        // console.log(response.data);
        // setPosts(response.data);
        // setFilteredPosts(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    devices.forEach((deviceObj) => {
      console.log(deviceObj.name);
    });
  }, [devices]); // Runs whenever 'devices' state changes

  return (
    <div className="devices">
      {devices && <DeviceList devices={devices} />}
    </div>
  );
};

export default Devices;
