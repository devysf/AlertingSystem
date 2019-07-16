import React, { Component } from "react";
import { Link } from 'react-router-dom';

export default class NavBar extends Component {
  
  constructor(arg){
    super(arg);
    this.state = {
      isAuthenticate : false
    }
  }
  componentDidMount(){
    if (localStorage.getItem("jwtToken")) {
      this.setState({
        isAuthenticate : true
      })
    }
  }


  logoutClick(e){
    console.log("Logput")
    localStorage.removeItem('jwtToken');

  }


  render() {
    const {isAuthenticate} = this.state;

    const authLinks = (

      <ul className="navbar-nav">
      <li className="nav-item">
          <a className="nav-link" href="/adding">Add Alerts</a>
        </li>
        <li className="nav-item">
          <a className="nav-link" href="/lists">List Alerts</a>
        </li>
        <li className="nav-item">
          <a className="nav-link" onClick={this.logoutClick} href="/login">Logout</a>
        </li>
   </ul>
      
  );

  const guestLinks = (
    <div>
       <ul className="navbar-nav ml-auto">
        <li className="nav-item">
          <Link className="nav-link" to="/register">
          Sign Up
          </Link>
        </li>
        <li className="nav-item">
          <Link className="nav-link" to="/login">
          Login
          </Link>
        </li>
      </ul>         
      </div>
  )

    return (
  
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="navbar-brand" href="#">Alerts</a>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
    
        <div className="collapse navbar-collapse"   id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item active">
              <a className="nav-link" href="/">Home <span className="sr-only">(current)</span></a>
            </li>
          </ul>

         
          {this.state.isAuthenticate? authLinks :guestLinks}
        </div>
    </nav>

    );
  }
}