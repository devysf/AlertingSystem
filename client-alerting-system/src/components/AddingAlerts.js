import React, { Component } from "react";
import axiosApi from "../axios-config/axios";

export default class AddingAlerts extends Component {

  constructor() {
    super();

    this.state = {
      isUpdate:"false",
      id:"",
      name: "",
      url: "",
      http_method: "",
      period: "",
      errors : {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onClick = this.onClick.bind(this);
  }

  componentDidMount(){
    if (!localStorage.getItem("jwtToken")) {
      this.props.history.push('/login');
    }
    
    //Control if user want to add new alerts or update existing alerts
    if(this.props.location.state){
      var alertState = this.props.location.state[0];

     this.setState({
       isUpdate:true,
       id:alertState.id,
       name : alertState.name,
       url : alertState.url,
       http_method : alertState.http_method,
       period : alertState.period
     })
    }
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e){
    e.preventDefault();

    //update existing alerts or add new alerts
    if(this.state.isUpdate == true){    

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

          if(res.data=="success"){
            this.setState({
              name: "",
              url: "",
              http_method: "",
              period: ""}
            );
            window.location.href = "lists";
          }

          this.setState({errors : res.data});

        })
       .catch(err=> {console.log("errroor " + err )})     
    }else{

        const newAlerts ={
          name: this.state.name,
          url : this.state.url,
          http_method : this.state.http_method,
          period : this.state.period
        }

        axiosApi
        .post("http://localhost:8080/api/alerts",newAlerts)
        .then(res => {
           
            if(res.data=="success"){

              this.setState({
                name: "",
                url: "",
                http_method: "",
                period: ""}
              );
              window.location.href = "lists";
            }
            this.setState({errors : res.data});

        })
        .catch(err=> {console.log("errroor " + err )})

      }
  
  }

  onClick(e){
    if(e.target.name == "http")
      this.setState({url : "http://"})
    else
      this.setState({url : "https://"})

  }

  render() {
    const {errors} = this.state;

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
                      {errors.name && <div className="text-danger">{errors.name}</div>}
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
                       {errors.url && <div className="text-danger">{errors.url}</div>}                     
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
                      {errors.http_method && <div className="text-danger">{errors.http_method}</div>}
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
                      {errors.period && <div className="text-danger">{errors.period}</div>}            
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