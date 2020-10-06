import React from 'react'
import { connect } from 'react-redux'
import { Form, FormGroup, Input, Label, Button, Row, Col } from 'reactstrap'
import { InsertUserAddress } from 'redux/user/address/action'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus, faMinus } from '@fortawesome/free-solid-svg-icons'
class AddressForm extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            address: {
                title: '',
                line1: '',
                line2: '',
                locality: '',
                city: '',
                state: '',
                pincode: ''
            },
            error: null,
            hidden:true
        }
    }
    componentDidMount() {
        console.log(this.state)
    }

    handleFormSubmit = (event) => {
        event.preventDefault()
        this.props.InsertUserAddress(this.state.address)
        this.setState({ hidden: true })
    }

    handleInputChange = (event) => {
        this.setState({ address: { ...this.state.address, [event.target.name]: event.target.value } })
    }

    render() {
        return (
            <Row className="justify-content-center bg-white pt-3">
                <Col xs="12" className="py-3">
                    <Button onClick={() => { this.setState({ hidden: !this.state.hidden }) }}><FontAwesomeIcon icon={this.state.hidden ? faPlus : faMinus}></FontAwesomeIcon></Button>

                </Col>
                
                <Form id="addressForm" className="col-12" hidden={this.state.hidden} onSubmit={this.handleFormSubmit}>
                    <FormGroup className="col-md-8 offset-md-2">
                        <Label>Title</Label>
                        <Input type="text" id="title" name="title" value={this.state.address.title}
                            onChange={this.handleInputChange} />
                    </FormGroup>
                    <FormGroup className="col-md-8 offset-md-2">
                        <Label>Line 1</Label>
                        <Input type="text" id="line1" name="line1" value={this.state.address.line1}
                            onChange={this.handleInputChange} />
                    </FormGroup>
                    <FormGroup className="col-md-8 offset-md-2">
                        <Label>Line 2</Label>
                        <Input type="text" id="line2" name="line2" value={this.state.address.line2}
                            onChange={this.handleInputChange} />
                    </FormGroup>
                    <FormGroup className="col-md-8 offset-md-2">
                        <Row>
                        <Col xs="6" sm="4" >
                            <Label>Locality</Label>
                            <Input type="text" id="locality" name="locality" value={this.state.address.locality}
                                onChange={this.handleInputChange} />
                        </Col>
                        <Col xs="6" sm="4" >
                            <Label>City</Label>
                            <Input type="text" id="city" name="city" value={this.state.address.city}
                                onChange={this.handleInputChange} />
                        </Col>
                        <Col xs="12" sm="4" >
                            <Label>State</Label>
                            <Input type="text" id="state" name="state" value={this.state.address.state}
                                onChange={this.handleInputChange} />
                        </Col>
                        </Row>
                    </FormGroup>
                    <FormGroup className="col-6 offset-2 col-md-3">
                        <Label>Pincode</Label>
                        <Input type="text" id="pincode" name="pincode" value={this.state.address.pincode}
                            onChange={this.handleInputChange} />
                    </FormGroup>
                    <FormGroup>
                        <Col xs={{ size: 10, offset: 2 }}>
                            <Button color="primary" type="submit">Submit</Button>
                        </Col>
                    </FormGroup>
                </Form>
            </Row>
        )
    }
}
const mapStateToProps = (state) => ({
    error: state.user.address.error
})
export default connect(mapStateToProps, { InsertUserAddress })(AddressForm)