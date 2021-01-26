import React, { Component } from "react";

class Header extends Component {
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
							<li className="active">
								<a href="/">Home</a>
							</li>
							<li>
								<a href="/pharmacies">Pharmacies</a>
							</li>
							<li>
								<a href="/drugs">Drugs</a>
							</li>
							<li>
								<a href="/drugs-reservation">Drugs reservations</a>
							</li>
							<li className="drop-down">
								<a href="#">My record</a>
								<ul>
									<li>
										<a href="/dermatologist-history">Dermatologist visits</a>
									</li>
									<li>
										<a href="/">Consultations with a pharmacist</a>
									</li>
									<li>
										<a href="/patients-appointments">Observe appointments</a>
									</li>
									<li>
										<a href="/">eReciepts</a>
									</li>
									<li>
										<a href="/">Reserved medicines</a>
									</li>
								</ul>
							</li>
							<li className="drop-down">
								<a href="#" className="appointment-btn scrollto" style={myStyle}>
									Make an Appointment
								</a>
								<ul>
									<li>
										<a href="/reserve-appointment">Dermatologist</a>
									</li>
									<li>
										<a href="/">Farmaceut</a>
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
