import React, { Component } from "react";
import axios from "axios";

export default class AddingAlerts extends Component {

  constructor() {
    super();

    this.state = {
      name: "",
      url: "",
      http_method: "",
      period: ""
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e){
    e.preventDefault();

    const newAlerts ={
      name: this.state.name,
      url : this.state.url,
      http_method : this.state.http_method,
      period : this.state.period,
      result:"0,0,0,0,0,0"
    }
    axios
    .post("http://localhost:8080/api/alerts",newAlerts)
    .then(res => {
        console.log("axios post adding alerts");
    })
    
    console.log(newAlerts);

    this.setState({
      name: "",
      url: "",
      http_method: "",
      period: ""}
    );


  }

  render() {
    return (
      <div>
        <h1> AddingAlerts </h1>
        <div className="container">
            <form onSubmit={this.onSubmit}>
                <div className="form-group">
                    <label htmlFor="name">
                        Name:
                    </label>
                    <input 
                      type="name"
                      className="form-control"
                      id="name"
                      name="name"
                      placeholder="Enter a name"
                      value={this.state.name}
                      onChange={this.onChange}
                      />
                </div>

                <div className="form-group">
                    <label htmlFor="url">
                        URL:
                    </label>
                    <input 
                      type="name"
                      className="form-control"
                      id="url"
                      name="url"
                      placeholder="Enter a URL"
                      value={this.state.url}
                      onChange={this.onChange}
                      />
                </div>

                <div className="form-group">
                    <label htmlFor="http_method">
                        http_method:
                    </label>
                    <input 
                      type="http_method"
                      className="form-control"
                      id="http_method"
                      name="http_method"
                      placeholder="Enter a http_method"
                      value={this.state.http_method}
                      onChange={this.onChange}
                      />
                </div>

                <div className="form-group">
                    <label htmlFor="period">
                        period:
                    </label>
                    <input 
                      type="period"
                      className="form-control"
                      id="period"
                      name="period"
                      placeholder="Enter a period"
                      value={this.state.period}
                      onChange={this.onChange}
                      />
                </div>

                <button type="submit" className="btn    btn-primary">
                    Submit
                </button>

            </form>
        </div>
      </div>
    );
  }
}