import React from 'react'
import {Container, Navbar, NavbarBrand} from 'reactstrap'
class Header extends React.Component {
    render () {
        <header>
            <Navbar light expand="md" color="white" className="border-bottom box-shadow mb-3">
                <Container className="text-center">
                    <NavbarBrand href="/">KTorAssignment</NavbarBrand>
                </Container>
                </Navbar>
        </header>
    }
}

export default Header