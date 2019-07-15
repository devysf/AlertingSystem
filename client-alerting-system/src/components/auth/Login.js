import React, { Component } from 'react';
import classnames from 'classnames';
import axios from "axios";

export default class Login extends Component {
  constructor() {
    super();
    this.state = {
      username: '',
      password: '',
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onSubmit(e) {
    e.preventDefault();

    const userData = {
      username: this.state.username,
      password: this.state.password
    };
    console.log(userData);
    axios
      .post("http://localhost:8080/authenticate",userData)
      .then(res => {
        console.log("Login then")
        console.log(res);
        if(!res.data.usernameError || !res.data.passwordError){
          sessionStorage.setItem("jwtToken", 'Bearer ' + res.data);

          axios.interceptors.request.use(
            (config) => {
                config.headers.authorization =  res.data
                return config
            }
          )
            
          this.props.history.push("/lists");
        }
        this.setState({errors : res.data});
        

      })
      .catch(error => {
        console.log("Login error")
        console.log(error.response)

        if(error.response.data == "INVALID_CREDENTIALS")
        {
          console.log("Invalid Credi")
          var errors = {
            usernameError : "Username or Password are incorrect.2"
          }
          this.setState({errors : errors});
        }
    });

  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render() {
    const { errors } = this.state;

    return (
      <div className="login">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Log In</h1>
              <p className="lead text-center">
                Sign in to your account
              </p>
              <form onSubmit={this.onSubmit}>
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
                    placeholder="password"
                    name="password"
                    type="password"
                    value={this.state.password}
                    onChange={this.onChange}
                    error={errors.password}
                  />
                  {errors.passwordError && <div className="text-danger">{errors.passwordError}</div>}
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
