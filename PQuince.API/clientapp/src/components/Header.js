import React, { Component } from "react";

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
						<a href="/">PQuince</a>
					</h1>

					<nav className="nav-menu d-none d-lg-block">
						<ul>
							<li className="active" hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
								<a href="/">Home</a>
							</li>
							<li hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
								<a href="/pharmacies">Pharmacies</a>
							</li>
							<li hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
								<a href="/drugs">Drugs</a>
							</li>
							<li className="drop-down" hidden={!this.hasRole("ROLE_PATIENT")}>
								<a href="#">My record</a>
								<ul>
									<li>
										<a href="/patients-appointments">Dermatologist visits</a>
									</li>
									<li>
										<a href="/observe-consultations">Consultations with a pharmacist</a>
									</li>
									<li>
										<a href="/">eReciepts</a>
									</li>
									<li>
										<a href="/drugs-reservation">Reserved medicines</a>
									</li>
								</ul>
							</li>
							<li>
								<a href="/pharmacy/cafeddee-56cb-11eb-ae93-0242ac130002">Pharmacy</a>
							</li>
							<li hidden={!this.hasRole("ROLE_PATIENT")}>
								<a href="/dermatologists-for-patient">Dermatologist</a>
							</li>
							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<a href="/dermatologists">Dermatologist</a>
							</li>
							
							<li className="drop-down" hidden={!this.hasRole("ROLE_PATIENT")}>
								<a href="#" className="appointment-btn scrollto" style={myStyle}>
									Make an Appointment
								</a>
								<ul>
									<li>
										<a href="/reserve-appointment">Dermatologist</a>
									</li>
									<li>
										<a href="/schedule-consultation">Pharmacist</a>
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
