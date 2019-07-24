import React, { Component } from "react";

import axiosApi from "../axios-config/axios";
import whoami from "../util/whoami";

export default class ListItem extends Component {
  constructor(){
    super();

    this.goReportsPage = this.goReportsPage.bind(this);
    this.goProfilePage = this.goProfilePage.bind(this);
  }

  goProfilePage(){
    console.log(this.props);
    this.props.history.push(`/profile/${this.props.alert.createdBy}`);
  }

  goReportsPage(){
    console.log(this.props);
    this.props.history.push(`/lists/${this.props.alert.id}`,[this.props.alert]);
  }

  onDeleteClick (){
    axiosApi
      .delete(`http://localhost:8080/api/alerts/${this.props.alert.id}`)
      .then(res => {
          if(res.data=="success")
            window.location.href = "lists";
      })
      .catch(err => {
        console.log("error occured in delete");
        console.log(err);
      })

    }

    onUpdateClick (){
      this.props.history.push("/adding",[this.props.alert])
    }

  render() {
    const whoisloggedin = whoami();
    const { id, name, url, http_method, period,createdBy } = this.props.alert;

    const deleteUpdateButton = (
      <div>
        <button
          onClick={this.onUpdateClick.bind(this)}
          className="btn btn-success"
        >
          Update
        </button>

        <button
          onClick={this.onDeleteClick.bind(this)}
          className="btn btn-danger"
        >
          Delete
        </button>
      </div>
    )
    

    return (

        <tr >
          <th scope="row" onClick={this.goReportsPage}>{name}</th>
          <td onClick={this.goReportsPage} >{url}</td>
          <td onClick={this.goReportsPage} >{http_method}</td>
          <td onClick={this.goReportsPage} >{period}</td>
          <td onClick={this.goProfilePage} >{createdBy}</td>

          {whoisloggedin==createdBy?deleteUpdateButton : null}
          
        </tr>    
    );
  }
}