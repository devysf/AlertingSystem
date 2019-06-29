import React, { Component } from "react";

export default class ListItem extends Component {
  render() {
    const { id, name, url, http_method, period } = this.props.alert;

    return (
          <tr>
            <th scope="row">{name}</th>
            <td>{url}</td>
            <td>{http_method}</td>
            <td>{period}</td>
          </tr>
    );
  }
}