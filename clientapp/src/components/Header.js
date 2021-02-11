import React, { Component } from "react";
import { Link } from "react-router-dom";

class Header extends Component {
	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

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
								<Link to="/">Home</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_PHARMACIST")}>
								<Link to="/reserved-drug">Drug reservations</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_DERMATHOLOGIST") && !this.hasRole("ROLE_PHARMACIST")}>
								<Link to="/absence">Absence</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_DERMATHOLOGIST") && !this.hasRole("ROLE_PHARMACIST")}>
								<Link to="/patients">Patients</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_PATIENT")}>
								<Link to="/patient-complaint">Make complaint</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_DERMATHOLOGIST") && !this.hasRole("ROLE_PHARMACIST")}>
								<Link to="/calendar">Calendar</Link>
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
										<Link to="/patient-ereceipts">eReciepts</Link>
									</li>
									<li>
										<Link to="/drugs-reservation">Reserved medicines</Link>
									</li>
									<li>
										<Link to="/patient-pharmacies-subscription">Pharmacy subscription</Link>
									</li>
								</ul>
							</li>
							<li hidden={this.hasRole("*")}>
								<Link to="/pharmacies">Pharmacies</Link>
							</li>
							<li hidden={this.hasRole("*")}>
								<Link to="/drugs">Drugs</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_SYSADMIN") && !this.hasRole("ROLE_DERMATHOLOGIST") && !this.hasRole("ROLE_PHARMACIST")}>
								<Link to="/drugs">Drugs</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_SYSADMIN")}>
								<Link to="/pharmacies">Pharmacies</Link>
							</li>
							<li className="drop-down" hidden={!this.hasRole("ROLE_PATIENT")}>
								<a href="#">Services</a>
								<ul>
									<li hidden={!this.hasRole("ROLE_PATIENT")}>
										<Link to="/dermatologists-for-patient">Dermatologist</Link>
									</li>
									<li hidden={!this.hasRole("ROLE_PATIENT")}>
										<Link to="/pharmacist-for-patient">Pharmacist</Link>
									</li>
									<li hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
										<Link to="/pharmacies">Pharmacies</Link>
									</li>
									<li hidden={this.hasRole("ROLE_PHARMACYADMIN")}>
										<Link to="/drugs">Drugs</Link>
									</li>
									<li hidden={!this.hasRole("ROLE_PATIENT")}>
										<Link to="/qr">Get drugs from QR code</Link>
									</li>
								</ul>
							</li>

							<li className="drop-down" hidden={!this.hasRole("ROLE_SYSADMIN")}>
								<a href="#">Register</a>
								<ul>
									<li hidden={!this.hasRole("ROLE_SYSADMIN")}>
										<Link to="/admin-register-staff">Staff</Link>
									</li>
									<li hidden={!this.hasRole("ROLE_SYSADMIN")}>
										<Link to="/admin-register-pharmacies">Pharmacy</Link>
									</li>
									<li hidden={!this.hasRole("ROLE_SYSADMIN")}>
										<Link to="/admin-register-drug">Drug</Link>
									</li>
								</ul>
							</li>

							<li hidden={!this.hasRole("ROLE_SUPPLIER")}>
								<Link to="/offers">Offers</Link>
							</li>

							<li hidden={!this.hasRole("ROLE_SUPPLIER")}>
								<Link to="/orders">Orders</Link>
							</li>

							<li className="drop-down" hidden={!this.hasRole("ROLE_SYSADMIN")}>
								<a href="#">Complaints</a>
								<ul>
									<li hidden={!this.hasRole("ROLE_SYSADMIN")}>
										<Link to="/admin-complaints">Staff</Link>
									</li>
									<li hidden={!this.hasRole("ROLE_SYSADMIN")}>
										<Link to="/admin-complaints-pharmacy">Pharmacy</Link>
									</li>
								</ul>
							</li>

							<li hidden={!this.hasRole("ROLE_SYSADMIN")}>
								<Link to="/loyalty-program">Loyalty program</Link>
							</li>

							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/pharmacy-for-admin">Pharmacy</Link>
							</li>

							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/dermatologists">Dermatologist</Link>
							</li>

							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/pharmacist">Pharmacist</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/drugs-in-pharmacy">Drugs</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/orders-administrator">Orders</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/absence-for-administrator">Absence</Link>
							</li>
							<li hidden={!this.hasRole("ROLE_PHARMACYADMIN")}>
								<Link to="/actions-and-promotions">Action and promotions</Link>
							</li>

							<li className="drop-down" hidden={!this.hasRole("ROLE_PATIENT")}>
								<a href="#" className="appointment-btn scrollto" style={myStyle}>
									Make an Appointment
								</a>
								<ul>
									<li>
										<Link to="/home-dermatologist-reservation">Dermatologist</Link>
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
