import React, { Component } from "react";
import { Link } from "react-router-dom";

class Header extends Component {
	hasRole = (reqRole) => {
		
		let roles = JSON.parse(localStorage.getItem("keyRole"));
		console.log(roles);

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

	render() {
		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
			<header id="header" className="fixed-top">
				<div className="container d-flex align-items-center">
					<h1 className="logo mr-auto">
						<Link to="/">PQuince</Link>
					</h1>

					<nav className="nav-menu d-none d-lg-block">
						<ul>
							<li className="active" hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
								<a to="/">Home</a>
							</li>
							<li hidden={!this.hasRole("ROLE_DERMATHOLOGIST")}>
								<Link to="/patients">Patients</Link>
							</li>
							<li hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/pharmacies">Pharmacies</Link>
							</li>
							<li hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/drugs">Drugs</Link>

							</li>
							<li className="drop-down" hidden={!this.hasRole("ROLE_PATIENT")}>
								<a href="#">My record</a>
								<ul>
									<li>
										<Link to="/patients-appointments">Dermatologist visits</Link>
									</li>
									<li>
										<Link to="/observe-consultations">Consultations with a pharmacist</Link>
									</li>
									<li>
										<Link to="/">eReciepts</Link>
									</li>
									<li>
										<Link to="/drugs-reservation">Reserved medicines</Link>
									</li>
								</ul>
							</li>
							<li className="drop-down">
								<a href="#">Pharmacy</a>
								<ul>
									<li>
										<Link to="/pharmacy/cafeddee-56cb-11eb-ae93-0242ac130002">Profile</Link>
									</li>
									<li>
										<Link to="/dermatologists">Dermatologist</Link>
									</li>
								</ul>
							</li>
							
							<li className="drop-down" hidden={!this.hasRole("ROLE_PATIENT")}>
								<a href="#" className="appointment-btn scrollto" style={myStyle}>
									Make an Appointment
								</a>
								<ul>
									<li>
										<Link to="/reserve-appointment">Dermatologist</Link>
									</li>
									<li>
										<Link to="/schedule-consultation">Pharmacist</Link>
									</li>
								</ul>
							</li>
						</ul>
					</nav>
				</div>
			</header>
		);
	}
}

export default Header;
