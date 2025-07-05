import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'; // Import your CSS for the navbar

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark stylish-navbar fixed-top shadow">
      <div className="container">
        <Link to="/" className="navbar-brand d-flex align-items-center">
          <img src="/images/bus-icon.png" alt="Logo" width="40" height="40" className="d-inline-block align-top mr-2" />
          <span className="brand-text">GoReserve</span>
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarMenu"
          aria-controls="navbarMenu"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse justify-content-end" id="navbarMenu">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item mx-2">
              <Link to="/showRoute" className="btn nav-btn">Show Route</Link>
            </li>
            <li className="nav-item mx-2">
              <Link to="/bookingHistory" className="btn nav-btn nav-btn-secondary">Booking History</Link>
            </li>
            <li className="nav-item mx-2">
              <Link to="/logout" className="btn nav-btn nav-btn-danger">Logout</Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
