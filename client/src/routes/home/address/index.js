import React from 'react';
import { connect } from 'react-redux'
import { LoadUserAddresses, DeleteUserAddress } from 'redux/user/address/action'
import { Container, Row, Col, Card, CardBody, CardHeader, CardTitle, Button } from 'reactstrap'

import AddressForm from './address-form';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faTrash} from '@fortawesome/free-solid-svg-icons'
class Address extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            addresses: this.props.addresses,
            formHidden: true
        }
    }

    componentDidMount() {
        this.props.LoadUserAddresses()
    }

    componentDidUpdate(prevProps, prevState) {
        if (this.props.addresses != prevProps.addresses)
            this.setState({ addresses: this.props.addresses })
    }
    addressHeader = () => (<>
        <Row className="justify-content-center bg-white pt-3">
            Address
            </Row>
                    
            <AddressForm hidden={this.state.formHidden}>
            </AddressForm>
        
    </>
    )
    render() {
        // 
            console.log(this.state.addresses)
            let addresses =this.state.addresses!=null?this.state.addresses.map((address) => {
                return (
                    <Col key={address.id} xs={{size:10,offset:1}} md={{size:6,offset:0}} className="pt-3" >
                        <Card className="pd-3">
                            <CardHeader>
                                <Row>
                                <Col xs="9">
                            <CardTitle className="m-1">{address.title}</CardTitle>
                            </Col>
                            <Col xs="1">
                            <Button color="link" className="text-dark" onClick={()=>{this.props.DeleteUserAddress(address.id)}}><FontAwesomeIcon icon={faTrash}></FontAwesomeIcon></Button>
                            </Col>
                            </Row>
                            </CardHeader>
                            <CardBody>
                        <div>
                            {address.line1}
                        </div>
                        <div>
                            {address.line2}
                        </div>
                        <div>{address.locality}</div>
                        <div>{address.city}, {address.state}, PinCode:{address.pincode} </div>
                        </CardBody>
                    </Card>
                    </Col>
                    
                )
            }):''
            return (
                <Container style={{maxHeight:"80vh",overflowY:"scroll"}} >
                    {this.addressHeader()}
                    <Row className="justify-content-start bg-white pt-3">
                        {addresses}
                    </Row>
                </Container>
            )

    }
}


const mapStateToProps = (state) => ({
    addresses: state.user.address.addresses
})

export default connect(mapStateToProps, {
    LoadUserAddresses,
    DeleteUserAddress
})(Address)