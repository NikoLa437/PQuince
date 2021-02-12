import React from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { Link } from "react-router-dom";

class HomePage extends React.Component {
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
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<section id="hero" className="d-flex align-items-center">
					<div className="container" hidden={this.hasRole("ROLE_DERMATHOLOGIST") || this.hasRole("ROLE_PHARMACIST")}>
						<h1>Welcome to PQuince</h1>
						<Link hidden={this.hasRole("*")} to="/registration" className="btn-get-started scrollto">
							Register
						</Link>
					</div>
					<div hidden={!this.hasRole("ROLE_DERMATHOLOGIST")}>
					<h2>Start examination through calendar or by searching for patient</h2>
					<h5>You can start examination for appointments in the next 24 hours <br/> As dermatologist you interact with appointments for pharmacy you work in current hours</h5>
					</div>
					<div hidden={!this.hasRole("ROLE_PHARMACIST")}>
					<h2>Start consultation through calendar or by searching for patient</h2>
					<h5>You can start consultation for appointments in the next 24 hours</h5>
					</div>
				</section>

				<a href="#" className="back-to-top">
					<i className="icofont-simple-up"></i>
				</a>
			</React.Fragment>
		);
	}
}

export default HomePage;
