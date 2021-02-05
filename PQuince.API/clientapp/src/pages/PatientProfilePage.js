import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { BASE_URL } from "../constants.js";
import Axios from "axios";
import PatientLogo from "../static/patientLogo.png";
import AppointmentIcon from "../static/appointment-icon.jpg";
import getAuthHeader from "../GetHeader";
import { withRouter } from "react-router";
import { Redirect } from "react-router-dom";

class PatientProfilePage extends Component {
	state = {
		id: "",
		email: "",
		password: "",
		name: "",
		surname: "",
		address: "",
		phoneNumber: "",
		appointments: [],
		redirect: false,
		redirectUrl: ''
	};

	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

	componentDidMount() {
		const id = this.props.match.params.id;
		this.setState({
			id: id
		});

		Axios.get(BASE_URL + "/api/users/patient/" + id, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);

				if (res.status === 401) {
					this.setState({
						redirect: true,
						redirectUrl: "/unauthorized"
					});
				} else {
					this.setState({
						id: res.data.Id,
						email: res.data.EntityDTO.email,
						name: res.data.EntityDTO.name,
						surname: res.data.EntityDTO.surname,
						address: res.data.EntityDTO.address,
						phoneNumber: res.data.EntityDTO.phoneNumber
					});
				}

				console.log(this.state)
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get(BASE_URL + "/api/appointment/patient/" + id, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {

				if (res.status === 401) {
					this.setState({
						redirect: true,
						redirectUrl: "/unauthorized"
					});
				} else {
					this.setState({ appointments: res.data });
				}

				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	//TODO: can examine only appointments scheduled for today?
	handleExamine = (appointmentId) => {
		this.setState({
			redirect: true,
			redirectUrl: "/treatment-report/" + appointmentId
		});
	};

	handleNotShowUp = (appointmentId) => {
		console.log(appointmentId)
	};

	handleSchedule = () => {
		this.setState({
			redirect: true,
			redirectUrl: "/schedule-appointment/" + this.state.id
		});
	};

	handleCreateAndSchedule = () => {
		this.setState({
			redirect: true,
			redirectUrl: "/create-and-schedule-appointment/" + this.state.id
		});
	};

	isCurrentDate = (appointmentDate) => {
		appointmentDate = new Date(appointmentDate);
		appointmentDate.setHours(0,0,0,0);
		let currentDate = new Date();
		currentDate.setHours(0,0,0,0);
		return appointmentDate.getTime() === currentDate.getTime();
	};

	render() {

		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

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
									style={{ float: "left" }}
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
								<br />
								<button
									type="button"
									style={{ width: "80%" }}
									onClick={() =>
										this.handleSchedule()
									}
									className="btn btn-primary"
								>
									Schedule appointment
								</button>
								<br />
								<br />
								<button
									hidden={!this.hasRole("ROLE_DERMATHOLOGIST")}
									type="button"
									style={{ width: "80%" }}
									onClick={() =>
										this.handleCreateAndSchedule()
									}
									className="btn btn-primary"
								>
									Create and schedule appointment
								</button>

							</form>
						</div>

						<table className="table" style={{ width: "70%", marginTop: "3rem" }}>
							<tbody>
								{this.state.appointments.map((appointment) => (
									<tr
										id={appointment.Id}
										key={appointment.Id}
										className="rounded"
										style={{ background: appointment.EntityDTO.appointmentStatus == "SCHEDULED" ? "#93c2aa" : "" }}
									>
										<td
											width="190em"
										>
											<img
												className="img-fluid"
												src={AppointmentIcon}
												width="100em"
											/>
										</td>
										<td>
											<div>
												<b>Date: </b>{" "}
												{new Date(
													appointment.EntityDTO.startDateTime
												).toLocaleDateString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
												})}
											</div>
											<div>
												<b>Time from: </b>{" "}
												{new Date(
													appointment.EntityDTO.startDateTime
												).toLocaleTimeString("en-US", {
													hour: "2-digit",
													minute: "2-digit",
												})}
											</div>
											<div>
												<b>Time to: </b>{" "}
												{new Date(
													appointment.EntityDTO.endDateTime
												).toLocaleTimeString("en-US", {
													hour: "2-digit",
													minute: "2-digit",
												})}
											</div>
											<div>
												<b>{this.hasRole("ROLE_DERMATOLOGIST") ? "Dermatologist:" : "Pharmacist:"}</b>{" "}
												{appointment.EntityDTO.staff.EntityDTO.name +
													" " + appointment.EntityDTO.staff.EntityDTO.surname}
											</div>
										</td>
										<td className="align-right" style={{ width: "35%" }}>
											<div hidden={!this.isCurrentDate(appointment.EntityDTO.startDateTime)}>
												<button
													type="button"
													style={{ width: "60%", float: "right" }}
													hidden={
														appointment.EntityDTO.appointmentStatus != "SCHEDULED"
													}
													onClick={() =>
														this.handleExamine(appointment.Id)
													}
													className="btn btn-primary"
												>
													Start examination
												</button>
												<br />
												<br />
												<button
													type="button"
													style={{ width: "60%", float: "right", verticalAlign: "bottom" }}
													hidden={
														appointment.EntityDTO.appointmentStatus != "SCHEDULED"
													}
													onClick={() =>
														this.handleNotShowUp(appointment.Id)
													}
													className="btn btn-danger"
												>
													Did not show up
												</button>
											</div>
										</td>
									</tr>
								))}
							</tbody>
						</table>

					</div>
				</div>
			</React.Fragment>
		);
	}
}

export default withRouter(PatientProfilePage);
