import React from 'react'
import { withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import { LoadUser } from 'redux/user/action'
import { Container, Row, Col,Card, CardBody, CardHeader, CardTitle } from 'reactstrap'
class Home extends React.Component {
    constructor(props) {
        super(props)

    }
    componentDidMount() {
        this.props.LoadUser()
    }
    render() {
        if (this.props.user!==null || this.props.user!==undefined){
        return (
            <div>
                <Container className="bg-white">
                    <div class="d-inline">
                        <a href="/profile">Profile</a>
                    </div>
                </Container>
                <Container>
                    <Row>
                        <Col xs={{size:8,offset:2}} sm={{size:7}} md={{size:4,offset:8}} className="justify-content-center">
                            <Card>
                            <CardHeader>
                                <CardTitle>
                                    {this.props.user.name}
                                </CardTitle>
                            </CardHeader>
                            <CardBody>
                                <img src={`${this.props.user.picture}`} width="100%" />
                                {this.props.user.picture}
                            </CardBody>
                            </Card>
                        </Col>
                    </Row>
                </Container>
            </div>
            )
        }
        return (<div></div>)
    }
}
const mapStateToProps = (state) => ({
    user: state.user.user,
    error: state.user.error
})
export default withRouter(connect(mapStateToProps, { LoadUser })(Home))