import React from "react";
import { Link } from 'react-router-dom';

const Navbar = () => {
  return ( 
    <nav className="navbar">
      <h1>Integrated Energy Management System</h1>
      {/* <div className="links">
        <Link to="/loginadmin">Login</Link>
        <Link to="/registeradmin">Register</Link>
      </div> */}
    </nav>
   );
}
 
export default Navbar;