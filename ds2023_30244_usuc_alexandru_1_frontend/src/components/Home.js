import { Button } from "@material-ui/core";
import { useEffect } from "react";
import { Link } from "react-router-dom";

const Home = () => {

  useEffect(() => {
    localStorage.setItem("username","");
    localStorage.setItem("usertype","");
    localStorage.setItem("token","");
  }, []);

  return (
    <div className="home-page">
      <h1>Welcome to our website!</h1>
        <div className="tiles">
          <div className="tile" id="adminTile">
            <h2>Admin</h2>
            <br></br>
            <Link to="/registeradmin">
              <button>Register</button>
            </Link>
            <Link to="/loginadmin">
              <button>Login</button>
            </Link>
          </div>
          <div className="tile" id="userTile">
            <h2>User</h2>
            <br></br>
            <Link to="/registeruser">
              <button>Register</button>
            </Link>
            <Link to="/loginuser">
              <button>Login</button>
            </Link>
          </div>
      </div>
    </div>
  );
};

export default Home;
