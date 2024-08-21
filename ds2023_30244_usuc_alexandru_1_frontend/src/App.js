import "./App.css";
import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import React from "react";
import AdminRegistration from "./components/AdminRegistration";
import Admin from "./components/Admin";
import AdminLogin from "./components/AdminLogin";
import NewUser from "./components/NewUser";
import UserDetails from "./components/UserDetails";
import ModifyUserData from "./components/ModifyUserData";
import NewDevice from "./components/NewDevice";
import ModifyDeviceData from "./components/ModifyDeviceData";
import Devices from "./components/Devices";
import DeviceDetails from "./components/DeviceDetails";
import Home from "./components/Home";
import UserRegistration from "./components/UserRegistration";
import UserLogin from "./components/UserLogin";
import ChatRoomTest from "./components/ChatRoomTest";
import ChatRoom from "./components/ChatRoom";

function App() {
  // const navigate = useNavigate("");
  // Check if the user is an admin
  const isAdmin = () => {
    return localStorage.getItem("usertype") === "admin";
  };

  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/registeradmin" element={<AdminRegistration />} />
            <Route path="/registeruser" element={<UserRegistration />} />
            <Route path="/loginadmin" element={<AdminLogin />} />
            <Route path="/loginuser" element={<UserLogin />} />

            {/* Admin pages */}
            <Route path="/admin" element={<Admin />} />
            <Route path="/admin/newuser" element={<NewUser />} />
            <Route path="/admin/newdevice" element={<NewDevice />} />
            <Route path="/admin/devices" element={<Devices />} />

            {/* {isAdmin() ? (
              <>
              <Route path="/admin" element={<Admin />} />
              <Route path="/admin/newuser" element={<NewUser />} />
              <Route path="/admin/newdevice" element={<NewDevice />} />
              <Route path="/admin/devices" element={<Devices />} />
              </>
            ) : (
              // console.log("Unauthorized access!")
              // navigate("/")
              alert("Unauthorized access!")
            )} */}

            <Route path="/users/:id" element={<UserDetails />} />
            <Route path="/devices/:id" element={<DeviceDetails />} />
            <Route path="/modifydata/:id" element={<ModifyUserData />} />
            <Route
              path="/modifydevicedata/:id"
              element={<ModifyDeviceData />}
            />
            {/* Chat */}
            {/* <Route path="/chattest" element={<ChatRoomTest />} />
            <Route path="/chat" element={<ChatRoom />} /> */}
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
