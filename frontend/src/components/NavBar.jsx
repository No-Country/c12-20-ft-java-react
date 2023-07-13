import React from "react";
import logo from "../assets/logocomplete.png";
import logo_instagram from "../assets/logo_instagram.png";
import logo_facebook from "../assets/logo_facebook.png";
import logo_twitter from "../assets/logo_twitter.png";
import logo_youtube from "../assets/logo_youtube.png";
import { Link } from "react-router-dom";

const NavBar = () => {
  return (
    <div className="navBar">
      <Link to={"/"}>
        <img className="logo" src={logo} />
      </Link>
      <div className="navBarTxt">
        <h3>About us</h3>
        <h3>Our clasess</h3>
        <h3>Contact</h3>
      </div>
      <div className="contIconNavBar">
        <img className="iconNavBar" src={logo_instagram} />
        <img className="iconNavBar" src={logo_twitter} />
        <img className="iconNavBar" src={logo_youtube} />
        <img className="iconNavBar" src={logo_facebook} />
      </div>
    </div>
  );
};

export default NavBar;
