import React from "react";
import { Link, Switch, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { LoadUser } from "redux/user/action";
import {
  Card,
  CardBody,
  CardHeader,
  CardTitle,
  Col,
  Container,
  Row,
} from "reactstrap";
import AuthenticatedRoute from "components/authenticated-route";
import Profile from "./profile";
import Address from "./address";
class Home extends React.Component {
  constructor(props) {
    super(props);
  }
  componentDidMount() {
    console.log("hi");
    this.props.LoadUser();
  }
  render() {
    console.log(this.props.user);
    if (this.props.user !== null && this.props.user !== undefined) {
      return (
        <div>
          <Container className="bg-white">
            <Row>
              <Col xs="auto"><Link to="/home/profile">Profile</Link></Col>
              <Col xs="auto"><Link to="/home/address">Address</Link></Col>
            </Row>

            {/* <a href="/profile">Profile</a> */}
          </Container>
          <Container>
            <Row>
              <Col className="justify-content-center pt-3">
                <Switch>
                  <AuthenticatedRoute path="/home/profile" component={Profile}>
                  </AuthenticatedRoute>
                  <AuthenticatedRoute path="/home/address" component={Address}>
                  </AuthenticatedRoute>
                </Switch>
              </Col>
              <Col
                md={{ size: 4, order: "end" }}
                className="justify-content-center pt-3 d-none d-md-block"
              >
                <Card>
                  <CardHeader>
                    <CardTitle>
                      {this.props.user.name}
                    </CardTitle>
                  </CardHeader>
                  <CardBody>
                    <img src={`${this.props.user.picture}`} width="100%" />
                  </CardBody>
                </Card>
              </Col>
            </Row>
          </Container>
        </div>
      );
    }
    return (<div></div>);
  }
}
const mapStateToProps = (state) => ({
  user: state.user.user.user,
  error: state.user.error,
});
export default withRouter(connect(mapStateToProps, { LoadUser })(Home));
