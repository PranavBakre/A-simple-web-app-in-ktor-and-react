import React from 'react'
import { Redirect, withRouter } from 'react-router-dom';
import { Container, Row, Col } from 'reactstrap';
import GoogleLoginButton from 'components/google-login-button';
import { connect } from 'react-redux';

class Index extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        if (!this.props.isLoggedIn)
        return (
            <Container>
                <Row>
                    <Col className="pt-3" xs={{ size: 8, offset: 2 }} sm={7} md={{ size: 4, offset: 8 }} style={{ textAlign: "center", backgroundColor: "white", height: "300px" }}>
                        <GoogleLoginButton></GoogleLoginButton>
                    </Col>
                </Row>
            </Container>
        )
        return (<Redirect to="/home"></Redirect>)
    }
}

const mapStateToProps = (state)=> ({
    isLoggedIn: state.login.isLoggedIn
})

export default withRouter(connect(mapStateToProps)(Index))