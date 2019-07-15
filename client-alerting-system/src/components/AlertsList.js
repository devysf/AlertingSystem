import React, { Component } from "react";
import axios from "axios";

import ListItem from "./ListItem";


export default class AlertsList extends Component {
  constructor() {
    super();

    this.state = {
     alerts : []
    };

   
  }

  componentDidMount(){
    axios.interceptors.request.use(
      (config) => {
        
            config.headers.authorization =  sessionStorage.getItem("jwtToken");
          
          return config
      }
    )

    axios
      .get("http://localhost:8080/api/alerts")
      .then(res => {
          console.log("ress");
          console.log( res.data);
          this.setState({alerts : res.data});
        })
        .catch(err=> {
          console.log("err");
          console.log(err);          
        });
        
  }

  render() {

    return (
      <div>
        <h1 className="display-4 mb-2">
           Alerts List
        </h1>
        <table className="table table-hover">
            <thead>
              <tr>
                <th scope="col">Name</th>
                <th scope="col">URL</th>
                <th scope="col">Http Method</th>
                <th scope="col">Control Period</th>

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