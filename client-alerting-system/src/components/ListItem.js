import React, { Component } from "react";

export default class ListItem extends Component {
  constructor(){
    super();

    this.goReportsPage = this.goReportsPage.bind(this);
  }

  goReportsPage(){
    console.log(this.props);
    this.props.history.push(`/lists/${this.props.alert.id}`);
  }

  render() {
    console.log("List item Component")
    console.log(this.props.alert);
    const { id, name, url, http_method, period,createdBy } = this.props.alert;

    
    return (

        <tr onClick={this.goReportsPage}>
          <th scope="row">{name}</th>
          <td>{url}</td>
          <td>{http_method}</td>
          <td>{period}</td>
          <td>{createdBy}</td>

        </tr>    
    );
  }
}