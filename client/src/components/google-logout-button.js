import React from "react";
import { GoogleLogout, useGoogleLogout } from "react-google-login";
import { Button, NavLink } from "reactstrap";
import { LogoutFailure, LogoutSuccess } from "../redux/login/action";
import { CLIENT_ID } from "../constants";
import { connect } from "react-redux";
// const { signout } = useGoogleLogout({
//     clientId: CLIENT_ID,
//     onLogoutSuccess: LOGOUT_SUCCESS,
//     onFailure: LOGOUTFAILURE
// })
class GoogleLogoutButton extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoggedIn: props.isLoggedIn,
    };
  }
  logout = (response) => {
    console.log(response);
    this.props.LogoutSuccess();
  };

  logoutfail = () => {
    this.props.LogoutFailure();
  };

  render() {
    if (this.props.isLoggedIn) {
      return (
        <GoogleLogout
          clientId={CLIENT_ID}
          buttonText="Logout"
          onLogoutSuccess={this.logout}
          onFailure={this.logoutfail}
          render={(
            renderProps,
          ) => (<NavLink
            onClick={renderProps.onClick}
            disabled={renderProps.disabled}
          >
            Logout
          </NavLink>)}
        >
        </GoogleLogout>
      );
    }
    return (<></>);
  }
}

const mapStateToProps = (state) => ({
  isLoggedIn: state.login.isLoggedIn,
});

export default connect(mapStateToProps, { LogoutSuccess, LogoutFailure })(
  GoogleLogoutButton,
);
