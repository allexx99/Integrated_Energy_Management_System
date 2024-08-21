import { Link, useNavigate, useParams } from "react-router-dom";
import useFetch from "./UseFetch";
import { Button } from "@material-ui/core";
import ModifyUserData from "./ModifyUserData";
import { useState, useEffect } from "react";
import axios from "axios";

import Stomp from "stompjs";
import SockJS from "sockjs-client";
import { over } from "stompjs";

import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

var stompClientChat = null;
const UserDetails = () => {
  const { id } = useParams();

  const currentUser = localStorage.getItem("username");
  const [privateChats, setPrivateChats] = useState(new Map());
  const [publicChats, setPublicChats] = useState([]);
  // const [users, setUsers] = useState([]);
  const [tab, setTab] = useState("CHATROOM");
  const [userData, setUserData] = useState({
    username: "",
    receivername: "",
    connected: false,
    message: "",
  });
  // State to control chat visibility
  const [showChat, setShowChat] = useState(false);
  // Add these state variables at the beginning of your component function
  const [isTyping, setIsTyping] = useState(false);
  const [lastSeen, setLastSeen] = useState(null);

  // const [messages, setMessages] = useState([]);
  // const [message, setMessage] = useState('');
  // const [stompClient, setStompClient] = useState(null);

  // useEffect(()=>{
  //   const socket = new SockJS('http://localhost:8082/ws');
  //   const client = Stomp.over(socket);

  //   client.connect({}, () => {
  //     client.subscribe('topic/notification', (message) => {
  //       const receivedMessage = JSON.parse(message.body);
  //       setMessages((prevMessages) => [...prevMessages,receivedMessage]);
  //       setMessage(message);
  //       // console.log(message);
  //     });
  //   });

  //   setStompClient(client);

  //   return () => {
  //     client.disconnect();
  //   };

  // }, []);

  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    const initializeWebSocket = () => {
      // In the `useEffect` hook, it defines a function `initializeWebSocket` that sets up a WebSocket connection
      // using the `SockJS` library and the `Stomp` over `WebSocket`.
      const socket = new SockJS("http://localhost:8082/ws");
      const client = Stomp.over(socket);

      // It connects to the WebSocket server when the connection is established.
      // The connect method takes an empty object (headers) and a callback function to handle the connection acknowledgment.
      client.connect(
        {},
        (frame) => {
          console.log("Connected to server:", frame);
          setStompClient(client);

          // It subscribes to a specific topic (/topic/notification) on the WebSocket server.
          // When a message is received on this topic, it attempts to parse the JSON content of the message body.
          // If the message has the expected structure, it calls the showToast function with the message.
          client.subscribe("/topic/notification/" + id, (message) => {
            try {
              const receivedMessage = JSON.parse(message.body);

              // Check if the received message has the expected structure
              if (receivedMessage && receivedMessage.message) {
                // Handle the valid message
                // alert("Device " + receivedMessage.deviceId + " alert: " + receivedMessage.message);
                showToast(receivedMessage.message);
                // alert(receivedMessage.message);
              } else {
                console.warn("Invalid message format:", receivedMessage);
              }
            } catch (error) {
              console.error("Error parsing JSON:", error);
            }
          });
        },
        (error) => {
          console.error("WebSocket connection error:", error);
        }
      );
    };

    // The showToast function uses the react-toastify library to display a toast notification.
    // It dismisses any existing toasts and then shows a new toast with the received message.
    const showToast = (message) => {
      // Clear any existing toasts
      toast.dismiss();
      // Create and show the toast notification
      toast.info(message, {
        position: toast.POSITION.TOP_CENTER,
        autoClose: 10000,
        className: "custom-toast",
      });
    };

    initializeWebSocket();

    // The return statement in the useEffect hook defines a cleanup function. When the component is about to unmount,
    // it disconnects from the WebSocket server and sets the stompClient state to null.
    return () => {
      // Cleanup when component unmounts
      if (stompClient) {
        console.log("Disconnecting from server...");
        stompClient.disconnect();
        setStompClient(null);
      }
    };
  }, []); // Empty dependency array ensures this effect runs only once when component mounts

  const navigateChat = useNavigate();
  const navigate = useNavigate("");
  const {
    data: user,
    isPending,
    error,
  } = useFetch("http://localhost:8080/readUsers/" + id);

  const [devices, setDevices] = useState([]);

  useEffect(() => {
    // Fetch the posts from an API or any data source
    axios
      .get("http://localhost:8081/getUserDevices/" + id, {
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

  console.log(id);

  /////////////////////////////////////////// CHAT ///////////////////////////////////////////

  useEffect(() => {
    // This will run when the component unmounts
    return () => {
      if (stompClientChat && stompClientChat.connected) {
        stompClientChat.disconnect();
        console.log("Disconnected!");
      }
    };
  }, []);

  // useEffect(() => {
  //   // Fetch the posts from an API or any data source
  //   axios
  //     .get("http://localhost:8080/readUsers")
  //     .then((response) => {
  //       setUsers(response.data);
  //       // console.log(response.data);
  //       // setPosts(response.data);
  //       // setFilteredPosts(response.data);
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //     });
  // }, []);

  // useEffect(() => {
  //   if (stompClientChat && stompClientChat.connected) {
  //     console.log("Already connected.");
  //     return;
  //   }

  //   let Sock = new SockJS("http://localhost:8083/ws");
  //   stompClientChat = over(Sock);
  //   stompClientChat.connect({}, onConnected, onError);
  // }, []);

  // useEffect to run once when the component mounts
  useEffect(() => {
    // Retrieve username from localStorage
    const storedUsername = localStorage.getItem("username");

    // If a username is found in localStorage, update userData
    if (storedUsername) {
      setUserData((prevUserData) => ({
        ...prevUserData,
        username: storedUsername,
      }));
    }
  }, []); // Empty dependency array ensures it runs only once on mount

  const connect = () => {
    if (stompClientChat && stompClientChat.connected) {
      console.log("Already connected.");
      return;
    }
    // setUserData({ ...userData, username: currentUser });
    console.log("sender: " + userData.username);
    console.log("sender: " + currentUser);
    let Sock = new SockJS("http://localhost:8083/ws");
    stompClientChat = over(Sock);
    stompClientChat.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    setUserData({ ...userData, connected: true });
    stompClientChat.subscribe("/chatroom/public", onMessageReceived);
    stompClientChat.subscribe(
      "/user/" + userData.username + "/private",
      onPrivateMessage
    );
    // Show the chat interface
    setShowChat(true);
    userJoin();
  };

  const onError = (err) => {
    console.log(err);
  };

  const userJoin = () => {
    var chatMessage = {
      senderName: userData.username,
      status: "JOIN",
    };

    stompClientChat.send("/app/message", {}, JSON.stringify(chatMessage));
  };
  // const userJoin = () => {
  //   if (stompClientChat && stompClientChat.connected) {
  //     var chatMessage = {
  //       senderName: currentUser,
  //       status: "JOIN",
  //     };
  //     stompClientChat.send("/app/message", {}, JSON.stringify(chatMessage));
  //   } else {
  //     console.log("WebSocket connection is not established.");
  //   }
  // };

  const onMessageReceived = (payload) => {
    var payloadData = JSON.parse(payload.body);
    switch (payloadData.status) {
      case "JOIN":
        if (!privateChats.get(payloadData.senderName)) {
          privateChats.set(payloadData.senderName, []);
          setPrivateChats(new Map(privateChats));
        }
        break;
      case "MESSAGE":
        publicChats.push(payloadData);
        setPublicChats([...publicChats]);
        break;
    }
  };

  // const onPrivateMessage = (payload) => {
  //   console.log(payload);
  //   var payloadData = JSON.parse(payload.body);

  //   if (payloadData.status === "TYPING" && payloadData.senderName === tab) {
  //     setIsTyping(true);
  //     setTimeout(() => setIsTyping(false), 3000);
  //   } else if (payloadData.status !== "TYPING") {
  //     if (privateChats.get(payloadData.senderName)) {
  //       privateChats.get(payloadData.senderName).push(payloadData);
  //       setPrivateChats(new Map(privateChats));
  //     } else {
  //       let list = [];
  //       list.push(payloadData);
  //       privateChats.set(payloadData.senderName, list);
  //       setPrivateChats(new Map(privateChats));
  //     }
  //   }
  // };

  const onPrivateMessage = (payload) => {
    var payloadData = JSON.parse(payload.body);
    if (privateChats.get(payloadData.senderName)) {
      privateChats.get(payloadData.senderName).push(payloadData);
      setPrivateChats(new Map(privateChats));
    } else {
      let list = [];
      list.push(payloadData);
      privateChats.set(payloadData.senderName, list);
      setPrivateChats(new Map(privateChats));
    }
  };

  const handleMessage = (event) => {
    const { value } = event.target;
    setUserData({ ...userData, message: value });
  };

  const sendValue = () => {
    if (stompClientChat) {
      var chatMessage = {
        senderName: userData.username,
        message: userData.message,
        status: "MESSAGE",
      };
      console.log(chatMessage);
      stompClientChat.send("/app/message", {}, JSON.stringify(chatMessage));
      setUserData({ ...userData, message: "" });
    }
  };

  const sendPrivateValue = () => {
    if (stompClientChat) {
      var chatMessage = {
        senderName: userData.username,
        receiverName: tab,
        message: userData.message,
        status: "MESSAGE",
      };

      if (userData.username !== tab) {
        privateChats.get(tab).push(chatMessage);
        setPrivateChats(new Map(privateChats));
      }
      stompClientChat.send(
        "/app/private-message",
        {},
        JSON.stringify(chatMessage)
      );
      setUserData({ ...userData, message: "" });
    }
  };

  /////////////////////////////////////////// CHAT ///////////////////////////////////////////

  return (
    <div className="user-details">
      <br></br>
      <h2>User Details</h2>
      <br></br>
      {user && (
        <article>
          <h4>First Name: {user.firstName}</h4>
          <h4>Last Name: {user.lastName}</h4>
          <h4>Email: {user.email}</h4>
          <h4>Age: {user.age}</h4>
          <h4>Username: {user.username}</h4>
          <h4>Password: {user.password}</h4>
        </article>
      )}
      <Link to={`/modifydata/${id}`}>
        <Button>Edit</Button>
      </Link>

      <br></br>
      <br></br>
      <br></br>
      <h2>Devices</h2>
      {devices.map((deviceObj) => (
        <div className="blog-preview" key={deviceObj.id}>
          <Link to={`/devices/${deviceObj.id}`}>
            <h3>Id: {deviceObj.id}</h3>
            <h5>Name: {deviceObj.name}</h5>
            <h5>Type: {deviceObj.type}</h5>
            {/* <p>Starting bid: { postObj.bid }</p> */}
          </Link>
          <Button onClick={() => handleDeleteDevice(deviceObj.id)}>
            Delete
          </Button>
        </div>
      ))}
      <ToastContainer />
      <button className="open-chat" onClick={connect}>
        Chat
      </button>

      {showChat && (
        <div className="container">
          {userData.connected ? (
            <div className="chat-box">
              <div className="member-list">
                <ul>
                  <li
                    onClick={() => {
                      setTab("CHATROOM");
                    }}
                    className={`member ${tab === "CHATROOM" && "active"}`}
                  >
                    Chatroom
                  </li>
                  {[...privateChats.keys()].map((name, index) => (
                    <li
                      onClick={() => {
                        setTab(name);
                      }}
                      className={`member ${tab === name && "active"}`}
                      key={index}
                    >
                      {name}
                    </li>
                  ))}
                </ul>
              </div>
              {tab === "CHATROOM" && (
                <div className="chat-content">
                  <ul className="chat-messages">
                    {publicChats.map((chat, index) => (
                      <li
                        className={`message ${
                          chat.senderName === userData.username && "self"
                        }`}
                        key={index}
                      >
                        {chat.senderName !== userData.username && (
                          <div className="avatar">{chat.senderName}</div>
                        )}
                        <div className="message-data">{chat.message}</div>
                        {chat.senderName === userData.username && (
                          <div className="avatar self">{chat.senderName}</div>
                        )}
                      </li>
                    ))}
                  </ul>

                  <div className="send-message">
                    <input
                      type="text"
                      className="input-message"
                      placeholder="enter the message"
                      value={userData.message}
                      onChange={handleMessage}
                    />
                    <button
                      type="button"
                      className="send-button"
                      onClick={sendValue}
                    >
                      send
                    </button>
                  </div>
                </div>
              )}
              {tab !== "CHATROOM" && (
                <div className="chat-content">
                  <ul className="chat-messages">
                    {[...privateChats.get(tab)].map((chat, index) => (
                      <li
                        className={`message ${
                          chat.senderName === userData.username && "self"
                        }`}
                        key={index}
                      >
                        {chat.senderName !== userData.username && (
                          <div className="avatar">{chat.senderName}</div>
                        )}
                        <div className="message-data">{chat.message}</div>
                        {chat.senderName === userData.username && (
                          <div className="avatar self">{chat.senderName}</div>
                        )}
                      </li>
                    ))}
                  </ul>

                  <div className="send-message">
                    <input
                      type="text"
                      className="input-message"
                      placeholder="enter the message"
                      value={userData.message}
                      onChange={handleMessage}
                    />
                    <button
                      type="button"
                      className="send-button"
                      onClick={sendPrivateValue}
                    >
                      send
                    </button>
                  </div>
                </div>
              )}
            </div>
          ) : (
            <div />
          )}
        </div>
      )}
    </div>
  );
};

export default UserDetails;
