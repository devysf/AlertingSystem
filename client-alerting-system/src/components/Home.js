import React, { Component } from "react";
import axios from "axios";

export default class Home extends Component {

  componentDidMount() {
    axios
    .get("http://localhost:8080/auth")
    .then(res => {
      console.log(res);
      if(res.data === "anonymousUser"){
        this.props.history.push("/login");      
      }
    });
    
  }

  render() {
    return (
      <div>
        <h1> Home </h1>
      </div>
    );
  }
}