import React, { Component } from "react";
import axiosApi from "../axios-config/axios";

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
    this.onClick = this.onClick.bind(this);

  }
  componentDidMount(){
    if (!localStorage.getItem("jwtToken")) {
      this.props.history.push('/login');
    }
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
    axiosApi
    .post("http://localhost:8080/api/alerts",newAlerts)
    .then(res => {
        console.log("axios post adding alerts");
    })
    .catch(err=> {console.log("errroor " + err )})
    
    console.log(newAlerts);

    this.setState({
      name: "",
      url: "",
      http_method: "",
      period: ""}
    );


  }

  onClick(e){
    if(e.target.name == "http")
      this.setState({url : "http://"})
    else
      this.setState({url : "https://"})

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
                    <br></br>
                    <div className="btn-group"  role="group" aria-label="Basic   example">
                      <button onClick={this.onClick} name="http" type="button" className="btn btn-secondary">http://</button>
                      <button onClick={this.onClick} name="https" type="button" className="btn btn-secondary">https://</button>
                      
                    </div>

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