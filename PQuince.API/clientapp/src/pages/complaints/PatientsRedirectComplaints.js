import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import PharmacyLogo from "../../static/pharmacyLogo.png";
import PharmacistLogo from "../../static/pharmacistLogo.png";
import DermatologistLogo from "../../static/dermatologistLogo.png";

import { Redirect } from "react-router-dom";

class PatientsRedirectComplaints extends Component {
	state = {
		redirectToPharmacy: false,
		redirectToConsultation: false,
		redirectToAppointment: false,
		redirectToPharmacyOver: false,
		redirectToConsultationOver: false,
		redirectToAppointmentOver: false,
		unauthorizedRedirect: false,
	};

	componentDidMount() {
		if (localStorage.getItem("keyToken") === null) this.setState({ unauthorizedRedirect: true });
	}

	handleImgClick = (imgNum) => {
		if (imgNum === 1) this.setState({ redirectToConsultation: true });
		else if (imgNum === 2) this.setState({ redirectToPharmacy: true });
		else this.setState({ redirectToAppointment: true });
	};

	handleImgMouseOver = (imgNum) => {
		if (imgNum === 1) this.setState({ redirectToConsultationOver: true });
		else if (imgNum === 2) this.setState({ redirectToPharmacyOver: true });
		else this.setState({ redirectToAppointmentOver: true });
	};

	handleImgMouseOut = (imgNum) => {
		if (imgNum === 1) this.setState({ redirectToConsultationOver: false });
		else if (imgNum === 2) this.setState({ redirectToPharmacyOver: false });
		else this.setState({ redirectToAppointmentOver: false });
	};

	render() {
		if (this.state.redirectToPharmacy) return <Redirect push to="/pharmacies" />;
		else if (this.state.redirectToConsultation) return <Redirect push to="/observe-consultations-history" />;
		else if (this.state.redirectToAppointment) return <Redirect push to="/dermatologist-history" />;

		if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />
				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Select to whom the complaint relates</h5>
					<div className="control-group mt-5">
						<div className="form-row justify-content-center align-middle ">
							<div
								className="form-col mt-5 mr-5 rounded p-3"
								onMouseOut={() => this.handleImgMouseOut(1)}
								onMouseOver={() => this.handleImgMouseOver(1)}
								onClick={() => this.handleImgClick(1)}
								style={{ backgroundColor: this.state.redirectToConsultationOver ? "silver" : "transparent", cursor: "pointer" }}
							>
								<div>
									<img className="img-fluid" src={PharmacistLogo} width="170em" />
								</div>
								<div className="mt-3 text-center" style={{ fontSize: "2em" }}>
									Pharmacist
								</div>
							</div>
							<div
								className="form-col mt-5 mr-5 ml-5 rounded p-3"
								onMouseOut={() => this.handleImgMouseOut(2)}
								onMouseOver={() => this.handleImgMouseOver(2)}
								onClick={() => this.handleImgClick(2)}
								style={{ backgroundColor: this.state.redirectToPharmacyOver ? "silver" : "transparent", cursor: "pointer" }}
							>
								<div>
									<img className="img-fluid" src={PharmacyLogo} width="170em" />
								</div>
								<div className="mt-3 text-center" style={{ fontSize: "2em" }}>
									Pharmacy
								</div>
							</div>
							<div
								className="form-col mt-5 ml-5 rounded p-3 "
								onMouseOut={() => this.handleImgMouseOut(3)}
								onMouseOver={() => this.handleImgMouseOver(3)}
								onClick={() => this.handleImgClick(3)}
								style={{ backgroundColor: this.state.redirectToAppointmentOver ? "silver" : "transparent", cursor: "pointer" }}
							>
								<div>
									<img className="img-fluid ml-4" src={DermatologistLogo} width="150em" />
								</div>
								<div className="mt-3 text-center" style={{ fontSize: "2em" }}>
									Dermatologist
								</div>
							</div>
						</div>
					</div>
				</div>
			</React.Fragment>
		);
	}
}

export default PatientsRedirectComplaints;
