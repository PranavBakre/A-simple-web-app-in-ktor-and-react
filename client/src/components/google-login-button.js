import React from "react";
import { GoogleLogin } from "react-google-login";
import { CLIENT_ID } from "../constants";
import { LoggingIn, LoginFailure } from "../redux/login/action";
import { connect } from "react-redux";
import { Button } from "reactstrap";

class GoogleLoginButton extends React.Component {
  constructor(props) {
    super(props);
  }
  login = (response) => {
    this.props.LoggingIn(response);
    console.log(this.props.isLoggedIn);
  };

  handleLoginFailure = (response) => {
    console.log(response);
    this.props.LoginFailure(response);
  };

  render() {
    if (!this.props.isLoggedIn) {
      return (
        <GoogleLogin
          clientId={CLIENT_ID}
          buttonText="Login"
          onSuccess={this.login}
          onFailure={this.handleLoginFailure}
          //cookiePolicy={ 'single_host_origin' }
          render={(
            renderProps,
          ) => (<Button
            color="primary"
            outline
            style={{ width: "100%" }}
            onClick={renderProps.onClick}
            disabled={renderProps.disabled}
          >
            Sign In with Google
          </Button>)}
        />
      );
    }
    return null;
  }
}

const mapStateToProps = (state) => {
  return { isLoggedIn: state.login.isLoggedIn };
};
const mapDispatchToProps = (dispatch) => ({
  LoggingIn: (response) => {
    dispatch(LoggingIn(response));
  },
  LoginFailure: (error) => {
    dispatch(LoginFailure(error));
  },
});

export default connect(mapStateToProps, mapDispatchToProps)(GoogleLoginButton);
