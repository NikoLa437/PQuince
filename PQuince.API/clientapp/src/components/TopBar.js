import React from 'react';

class TopBar extends React.Component{
    constructor(){
        super();


    }

    render(){
        const myStyle = {
            margin: 10
        };
        return(
            <div id="topbar" className="d-none d-lg-flex align-items-center fixed-top">
                <div className="container d-flex">
                    <div className="contact-info mr-auto">
                        <i className="icofont-envelope"></i> <a style={myStyle} href="mailto:contact@example.com">contact@pquince.com</a>
                        <a href="#" style={myStyle} className="instagram"><i className="icofont-instagram"></i>PQuince</a>
                        <a href="#" style={myStyle} className="facebook"><i className="icofont-facebook"></i>QuincePharmacy</a>
                    </div>
                    
                    <div className="register-login">
                        <a href="/registration">Register</a>
                        <a href="/login">LogIn</a>
                        <a href="/profile" style={myStyle} className="profile"><i className="icofont-user"></i>Profile</a>
                    </div>
                </div>
            </div>
        )
    }
}

export default TopBar