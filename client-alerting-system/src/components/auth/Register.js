import React, { Component } from 'react';
import classnames from 'classnames';
import axios from "axios";

export default class Register extends Component {
  constructor() {
    super();

    this.state = {
      username: '',
      password: '',
      passwordConfirm: '',
      email:"",
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    if (localStorage.getItem("jwtToken")) {
      this.props.history.push('/login');
    }
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();

    const newUser = {
      username: this.state.username,
      password: this.state.password,
      passwordConfirm: this.state.passwordConfirm,
      email: this.state.email
    };
  
    
    axios
    .post("http://localhost:8080/register",newUser)
    .then(res => {
      console.log("Login then")
      console.log(res);
      if(res.data==="success")
        this.props.history.push("/lists");
      this.setState({errors : res.data});

      console.log("axios")
      console.log(res.data);

    })
    .catch(err => console.log(err));
  }

  render() {
    var  errors = this.state.errors;
   
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
                  />

                   {errors.usernameError && <div className="text-danger">{errors.usernameError}</div>}
                </div>

                <div className="form-group">
                  <input
                    className={'form-control form-control-lg'}
                    placeholder="email"
                    name="email"
                    type="email"
                    value={this.state.email}
                    onChange={this.onChange}
                  />

                   {errors.emailError && <div className="text-danger">{errors.emailError}</div>}
                </div>
          

                <div className="form-group">
                  <input
                    className={'form-control form-control-lg'}
                    placeholder="Password"
                    name="password"
                    type="password"
                    value={this.state.password}
                    onChange={this.onChange}
                  />

                  {errors.passwordError && <div className="text-danger">{errors.passwordError}</div>}
                </div>
              
                <div className="form-group">
                  <input
                   className={'form-control form-control-lg'}
                    placeholder="Confirm Password"
                    name="passwordConfirm"
                    type="password"
                    value={this.state.passwordConfirm}
                    onChange={this.onChange}
                  />
                  
                  {errors.passwordConfirmError && <div className="text-danger">{errors.passwordConfirmError}</div>}

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
