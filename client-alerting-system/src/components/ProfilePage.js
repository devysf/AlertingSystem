import React, { Component } from "react";
import axiosApi from "../axios-config/axios";

import ListItem from "./ListItem";

export default class ProfilePage extends Component {
  constructor() {
    super();

    this.state = {
     alerts : []
    };

   
  }

  componentDidMount(){
    console.log("asdasd");
   
    axiosApi
      .get(`http://localhost:8080/api/alertsByUsername/${this.props.match.params.id}`)
      .then(res=> {
        console.log("Get Alerts by username")
        console.log(res.data);
        this.setState({alerts : res.data});
      })
      .catch(err =>{
        console.log("errorrrrr")
        console.log(err);
      })

  }

  render() {
    console.log("Profile page");
    console.log(this.props.match.params.id);
    const username = this.props.match.params.id
    return (
      <div>
        <h1> ProfilePage </h1>
        <h3> {username}'s Alerts </h3>

        <table className="table table-hover">
            <thead>
              <tr>
                <th scope="col">Name</th>
                <th scope="col">URL</th>
                <th scope="col">Http Method</th>
                <th scope="col">Control Period</th>
                <th scope="col">Created By</th>

              </tr>
            </thead>
            
            <tbody>
              { this.state.alerts.map(alert => (
                  <ListItem key={alert.id} alert={alert} history={this.props.history}/>
              ))}

            </tbody>
        </table>

      </div>
    );
  }
}