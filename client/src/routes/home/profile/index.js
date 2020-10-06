import React from 'react'
import { withRouter } from 'react-router-dom'
import {LoadUserProfile,UpdateUserProfile} from 'redux/user/profile/action'
import {connect} from 'react-redux'
import { Button, Col, Container, Form, FormGroup, Input, Label, Row } from 'reactstrap'


class Profile extends React.Component {
    constructor(props) {
        super(props)
        this.state={
            profileChange:false,
            profile:this.props.profile
        }
    }

    componentDidMount(){
        this.props.LoadUserProfile()
    }

    componentDidUpdate(prevProps,prevState){
        if(this.props.profile!=prevProps.profile)
            this.setState({profile:this.props.profile})
    }

    handleProfileChange=(event) =>{
        this.setState({profileChange:true, profile:{...this.state.profile,[event.target.name]:event.target.value}},()=>{console.log(this.state)})
        
    }

    handleProfileSubmit=(event)=>{
        event.preventDefault()
        this.props.UpdateUserProfile(this.state.profile)
    }

    render () {
        if (this.props.profile!==undefined && this.props.profile!==null){
            return(
                    <Container>
                    
                    <Row>
                       
                        <Col className="justify-content-center bg-white ">
                            Profile
                            <Form className="mt-3 container" id="formProfile" onSubmit={this.handleProfileSubmit}>
                                <Row className="justify-content-center">
                                    <FormGroup className="col-12 col-md-5 justify-content-center">
                                        <Label for="name">Name</Label>
                                        <Input type="text" id="name" className="form-control" value={`${this.state.profile.name}`} disabled
                                            />
                                    </FormGroup>
                            
                                    <FormGroup className="col-12 col-md-5 offset-md-1  justify-content-center">
                                        <Label for="email">Email</Label>
                                        <Input type="email" id="email" className="form-control " value={`${this.state.profile.email}`} disabled/>
                                    </FormGroup>
                                </Row>
                                <Row className="justify-content-center">
                                    <FormGroup className="form-group col-12 col-md-5  justify-content-center">
                                        <Label for="username">Username</Label>
                                        <Input type="text" name="username" id="username" className="form-control" onChange={this.handleProfileChange}
                                            value={`${this.state.profile.username}`} disabled={this.state.profile['profile-lock']}/>
                                    </FormGroup>      
                                    <FormGroup className="form-group col-12 col-md-5 offset-md-1  justify-content-center">
                                        <Label for="mobile-number">Mobile Number</Label>
                                        <Input type="tel" name="mobile-number" id="mobile-number" className="form-control" onChange={this.handleProfileChange}
                                            value={`${this.state.profile['mobile-number']}`}/>
                                    </FormGroup>
                                </Row>      
                                <Row className="justify-content-center  mb-2" hidden={!this.state.profileChange} id="profileSubmit">
                                    <Button color="primary" type="submit">Submit</Button>
                                </Row>
                            </Form>
                        </Col>
                    </Row>
                    </Container>
            )
        }   
        return (<div></div>)

    }
}

const mapStateToProps = (state) => ({
    profile:state.user.profile.profile
})

export default withRouter(connect(mapStateToProps,{LoadUserProfile, UpdateUserProfile})(Profile))