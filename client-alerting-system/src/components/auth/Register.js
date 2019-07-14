import React, { Component } from 'react';
import classnames from 'classnames';
import axios from "axios";

export default class Register extends Component {
  constructor() {
    super();

    this.state = {
      username: '',
      email: '',
      password: '',
      password2: '',
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    axios
    .get("http://localhost:8080/auth")
    .then(res => {
      console.log(res);
      if(res.data !== "anonymousUser"){
        this.props.history.push("/");      
      }
    });

   
  }

  componentWillReceiveProps(nextProps) {

    /*
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    */
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();

    const newUser = {
      name: this.state.name,
      email: this.state.email,
      password: this.state.password,
      password2: this.state.password2
    };
    
    axios
      .post("http://localhost:8080/login",newUser)
      .then(res => {
        console.log("Login Process")
        console.log(res);
      })
    /*
    this.props.registerUser(newUser, this.props.history);
    */
  }

  render() {
    const { errors } = this.state;

    return (
      <div className="register">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Sign Up</h1>
              <p className="lead text-center">
                Create your account
              </p>
              <form noValidate onSubmit={this.onSubmit}>
                
                <div className="form-group">
                  <input
                    className={'form-control form-control-lg'}
                    placeholder="username"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChange}
                    error={errors.username}
                  />
                </div>
                <div className="form-group">
                  <input
                   className={'form-control form-control-lg'}
                    placeholder="Email"
                    name="email"
                    type="email"
                    value={this.state.email}
                    onChange={this.onChange}
                    error={errors.email}
                    info="This site uses Gravatar so if you want a profile image, use a Gravatar email"
                  />
                </div>

                <div className="form-group">
                  <input
                    className={'form-control form-control-lg'}
                    placeholder="Password"
                    name="password"
                    type="password"
                    value={this.state.password}
                    onChange={this.onChange}
                    error={errors.password}
                  />
                </div>
              
                <div className="form-group">
                  <input
                   className={'form-control form-control-lg'}
                    placeholder="Confirm Password"
                    name="password2"
                    type="password"
                    value={this.state.password2}
                    onChange={this.onChange}
                    error={errors.password2}
                  />
                </div>
               
                <input type="submit" className="btn btn-info btn-block mt-4" />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
