import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route } from "react-router-dom";

import NavBar from "./components/NavBar";
import Login from "./components/auth/Login";
import Register from "./components/auth/Register";

import Home from "./components/Home";
import AddingAlerts from "./components/AddingAlerts";
import AlertsList from "./components/AlertsList";
import ReportsAlerts from "./components/ReportsAlerts";

import jwt_decode from "jwt-decode";

//localStorage.clear();
if(localStorage.getItem('jwtToken')){
  const token = localStorage.getItem("jwtToken");
  const decoded = jwt_decode(token);

  console.log("Decoded ")
  console.log(decoded);

  // Check for expired token
  const currentTime = Date.now() / 1000;
  if (decoded.exp < currentTime) {
    window.location.href("login");
  }
}

function App() {
  return (
      <Router>
        <div className="App">
          <NavBar />
          
          <Route exact path="/" component={Home} />

          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/adding" component={AddingAlerts} />
          <Route exact path="/lists" component={AlertsList} />
          <Route exact path="/lists/:id" component={ReportsAlerts} />
        </div>
      </Router>

  );
}

export default App;
