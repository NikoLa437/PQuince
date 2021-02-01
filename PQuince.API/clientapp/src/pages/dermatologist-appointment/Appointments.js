import React, { Component } from "react";
import AppointmentIcon from "../../static/appointment-icon.jpg";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ModalDialog from "../../components/ModalDialog";
import { Redirect } from "react-router-dom";
import getAuthHeader from "../../GetHeader";

class Appointments extends Component {
	state = {
		appointments: [],
		openModalSuccess: false,
		showingSorted: false,
		redirect: false,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/" + "cafeddee-56cb-11eb-ae93-0242ac130002", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ appointments: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleAppointmentClick = (appointmentId) => {
		Axios.post(
			BASE_URL + "/api/appointment/reserve-dermatologist-appointment",
			{ id: appointmentId },
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ openModalSuccess: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModalSuccessClose = () => {
		this.setState({ openModalSuccess: false, redirect: true });
	};

	handleResetSort = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/" + "cafeddee-56cb-11eb-ae93-0242ac130002", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: false });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByGradeAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-grade-ascending/" + "cafeddee-56cb-11eb-ae93-0242ac130002", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByGradeDesscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-price-descending/" + "cafeddee-56cb-11eb-ae93-0242ac130002", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-price-ascending/" + "cafeddee-56cb-11eb-ae93-0242ac130002", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-price-descending/" + "cafeddee-56cb-11eb-ae93-0242ac130002", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	render() {
		if (this.state.redirect) return <Redirect push to="/" />;
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Create Appointment</h5>

					<p className="mb-0 mt-2 text-uppercase">Click on appointment to make reservation</p>

					<div className="form-group">
						<div className="form-group controls mb-0 pb-2">
							<div className="form-row mt-3">
								<div className="form-col">
									<div className="dropdown">
										<button
											className="btn btn-primary dropdown-toggle"
											type="button"
											id="dropdownMenu2"
											data-toggle="dropdown"
											aria-haspopup="true"
											aria-expanded="false"
										>
											Sort by
										</button>
										<div className="dropdown-menu" aria-labelledby="dropdownMenu2">
											<button className="dropdown-item" type="button" onClick={this.handleSortByGradeAscending}>
												Dermatologist grade ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByGradeDesscending}>
												Dermatologist grade descending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByPriceAscending}>
												Examination price ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByPriceDescending}>
												Examination price descending
											</button>
										</div>
									</div>
								</div>
								<div className="form-col ml-3">
									<div className={this.state.showingSorted ? "form-group" : "form-group collapse"}>
										<button type="button" className="btn btn-outline-secondary" onClick={this.handleResetSort}>
											<i className="icofont-close-line mr-1"></i>Reset criteria
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.appointments.map((appointment) => (
								<tr
									id={appointment.Id}
									key={appointment.Id}
									onClick={() => this.handleAppointmentClick(appointment.Id)}
									className="rounded"
									style={{ cursor: "pointer" }}
								>
									<td width="190em">
										<img className="img-fluid" src={AppointmentIcon} width="150em" />
									</td>
									<td>
										<div>
											<b>Date: </b>{" "}
											{new Date(appointment.EntityDTO.startDateTime).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Time from: </b>{" "}
											{new Date(appointment.EntityDTO.startDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Time to: </b>{" "}
											{new Date(appointment.EntityDTO.endDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Price:</b>{" "}
											<span
												style={
													appointment.EntityDTO.price !== appointment.EntityDTO.discountPrice
														? { textDecoration: "line-through" }
														: {}
												}
											>
												{" "}
												{appointment.EntityDTO.price}
											</span>
											<b> din</b>
										</div>
										<div hidden={appointment.EntityDTO.price === appointment.EntityDTO.discountPrice}>
											<b>Price with discount:</b> {appointment.EntityDTO.discountPrice}
											<b> din</b>
										</div>
										<div>
											<b>Dermatologist: </b>{" "}
											{appointment.EntityDTO.staff.EntityDTO.name + " " + appointment.EntityDTO.staff.EntityDTO.surname}
										</div>
										<div>
											<b>Dermatologist grade: </b> {appointment.EntityDTO.staff.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<ModalDialog
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully reserved"
					text="Your appointment is reserved. Further details are sent to your email address."
				/>
			</React.Fragment>
		);
	}
}

export default Appointments;
