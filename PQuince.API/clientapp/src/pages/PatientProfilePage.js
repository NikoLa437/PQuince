import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { BASE_URL } from "../constants.js";
import Axios from "axios";
import PatientLogo from "../static/patientLogo.png";


class PatientProfilePage extends Component {
	state = {
		id: "",
		email: "",
		password: "",
		name: "",
		surname: "",
		address: "",
		phoneNumber: "",
		appointments: []
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/users/patient/22793162-52d3-11eb-ae93-0242ac130002")
			.then((res) => {
				console.log(res.data);
				this.setState({
					id: res.data.Id,
					email: res.data.EntityDTO.email,
					name: res.data.EntityDTO.name,
					surname: res.data.EntityDTO.surname,
					address: res.data.EntityDTO.address,
					phoneNumber: res.data.EntityDTO.phoneNumber
                });
                console.log(this.state)
			})
			.catch((err) => {
				console.log(err);
            });
	}

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "8%" }}>
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Patient information
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage">	

                                <img
                                    style={{float: "left"}}
									className="img-fluid"
									src={PatientLogo}
									width="150em"
								/>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Name: </b> {this.state.name}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Surname: </b> {this.state.surname}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Email address: </b> {this.state.email}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Phone number: </b> {this.state.phoneNumber}
										</div>
									</div>
								</div>
                                <div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Address: </b> {this.state.address.country + " " + this.state.address.city + " " + this.state.address.street}
										</div>
									</div>
								</div>
								
							</form>
						</div>
					</div>
				</div>
			</React.Fragment>
		);
	}
}

export default PatientProfilePage;
