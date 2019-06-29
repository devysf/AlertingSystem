import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route } from "react-router-dom";

import NavBar from "./components/NavBar";

import Home from "./components/Home";
import AddingAlerts from "./components/AddingAlerts";
import ListAlerts from "./components/ListAlerts";
import ReportsAlerts from "./components/ReportsAlerts";


function App() {
  return (
      <Router>
        <div className="App">
          <NavBar />
          
          <Route exact path="/" component={Home} />
          <Route exact path="/adding" component={AddingAlerts} />
          <Route exact path="/lists" component={ListAlerts} />
          <Route exact path="/reports" component={ReportsAlerts} />
        </div>
      </Router>

  );
}

export default App;
