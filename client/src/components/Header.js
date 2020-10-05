import React from 'react'
import {Container, Navbar, NavbarBrand} from 'reactstrap'
import {connect} from 'react-redux'
import GoogleLogoutButton from './GoogleLogoutButton'
class Header extends React.Component {
    render () {

        return (
        <header>
            <Navbar light expand="md" color="white" className="border-bottom box-shadow mb-3">
                <Container className="text-center">
                    <NavbarBrand href="/">KTorAssignment</NavbarBrand>
                </Container>
                <GoogleLogoutButton></GoogleLogoutButton>
                </Navbar>
        </header>
        )
    }
}


const mapStateToProps=(state) => ({
    isLoggedIn:state.isLoggedIn
})

export default connect(mapStateToProps) (Header)