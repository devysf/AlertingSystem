import React, { Component } from "react";

import axiosApi from "../axios-config/axios";

export default class ListItem extends Component {
  constructor(){
    super();

    this.goReportsPage = this.goReportsPage.bind(this);
  }

  goReportsPage(){
    console.log(this.props);
    this.props.history.push(`/lists/${this.props.alert.id}`,[this.props.alert]);
  }
  onDeleteClick (){
    console.log("Delete button pressed")

    axiosApi
      .delete(`http://localhost:8080/api/alerts/${this.props.alert.id}`)

      window.location.href = "lists";
    }

    onUpdateClick (){
      console.log("Update button pressed")
      this.props.history.push("/adding",[this.props.alert])
      }

  render() {
    console.log("List item Component")
    console.log(this.props.alert);
    const { id, name, url, http_method, period,createdBy } = this.props.alert;

    
    return (

        <tr >
          <th scope="row" onClick={this.goReportsPage}>{name}</th>
          <td onClick={this.goReportsPage} >{url}</td>
          <td onClick={this.goReportsPage} >{http_method}</td>
          <td onClick={this.goReportsPage} >{period}</td>
          <td onClick={this.goReportsPage} >{createdBy}</td>

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

        </tr>    
    );
  }
}