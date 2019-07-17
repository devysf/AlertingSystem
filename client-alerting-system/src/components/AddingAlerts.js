import React, { Component } from "react";
import axiosApi from "../axios-config/axios";
import jwt_decode from "jwt-decode";

export default class AddingAlerts extends Component {

  constructor() {
    super();

    this.state = {
      isUpdate:"false",
      id:"",
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

    
 
    if(this.props.location.state){
      var alertState = this.props.location.state[0];

     this.setState({
       isUpdate:true,
       id:alertState.id,
       name : alertState.name,
       url : alertState.url,
       http_method : alertState.url,
       period : alertState.period
     })
    }

   
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e){
    e.preventDefault();

    if(this.state.isUpdate == true){
      console.log("isUpdateTrue");
      
      const updateAlerts ={
        id:this.state.id,
        name: this.state.name,
        url : this.state.url,
        http_method : this.state.http_method,
        period : this.state.period
      }

      axiosApi
        .put("http://localhost:8080/api/alerts",updateAlerts)
        .then(res => {
          console.log("axios put updating alerts")
          ;

          window.location.href = "lists";

        })
       .catch(err=> {console.log("errroor " + err )})
      
    }else{
        console.log("isUpdateFalse")

        const newAlerts ={
          name: this.state.name,
          url : this.state.url,
          http_method : this.state.http_method,
          period : this.state.period
                }
        axiosApi
        .post("http://localhost:8080/api/alerts",newAlerts)
        .then(res => {
            console.log("axios post adding alerts");
            window.location.href = "lists";

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
  


  }

  onClick(e){
    if(e.target.name == "http")
      this.setState({url : "http://"})
    else
      this.setState({url : "https://"})

  }

  render() {

    console.log("Update");
    console.log(this.state)

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