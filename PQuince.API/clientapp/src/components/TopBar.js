import React from 'react';

class TopBar extends React.Component{

	handleLogout = () => {
       localStorage.removeItem('keyToken');
       localStorage.removeItem('keyRole');
    }
    
    render(){
        const myStyle = {
            margin: 10
        };
        return(
            <div id="topbar" className="d-none d-lg-flex align-items-center fixed-top">
                <div className="container d-flex">
                    <div className="contact-info mr-auto">
                        <i className="icofont-envelope"></i> <a style={myStyle} href="mailto:quinceit.pquince@gmail.com">quinceit.pquince@gmail.com</a>
                        <a href="#" style={myStyle} className="instagram"><i className="icofont-instagram"></i>PQuince</a>
                        <a href="#" style={myStyle} className="facebook"><i className="icofont-facebook"></i>QuincePharmacy</a>
                    </div>
                    
                    <div className="register-login">
                        <a href="/registration">Register</a>
                        <a href="/login">LogIn</a>
                        <a onClick = {this.handleLogout} href="/login">LogOut</a>
                        <a href="/profile" style={myStyle} className="profile"><i className="icofont-user"></i>Profile</a>
                    </div>
                </div>
            </div>
        )
    }
}

export default TopBar