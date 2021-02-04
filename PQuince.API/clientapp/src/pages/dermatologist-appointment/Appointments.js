import React, { Component } from "react";
import AppointmentIcon from "../../static/appointment-icon.jpg";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ModalDialog from "../../components/ModalDialog";
import { Redirect } from "react-router-dom";
import getAuthHeader from "../../GetHeader";
import { withRouter } from "react-router";
import HeadingAlert from "../../components/HeadingAlert";

class Appointments extends Component {
	state = {
		pharmacyId: "",
		appointments: [],
		openModalSuccess: false,
		showingSorted: false,
		redirect: false,
		pharmacyId: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		unauthorizedRedirect: false,
	};

	fetchData = (id) => {
		this.setState({
			pharmacyId: id,
		});
	};

	fetchData = (id) => {
		this.setState({
			pharmacyId: id,
		});
	};

	componentDidMount() {
		const id = this.props.match.params.id;
		this.fetchData(id);

		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/" + id, {
			validateStatus: () => true,
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else {
					this.setState({ appointments: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleAppointmentClick = (appointmentId) => {
		this.setState({ hiddenFailAlert: true, failHeader: "", failMessage: "" });

		Axios.post(
			BASE_URL + "/api/appointment/reserve-dermatologist-appointment",
			{ id: appointmentId },
			{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				if (res.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: res.data });
				} else if (res.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (res.status === 201) {
					this.setState({ openModalSuccess: true });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModalSuccessClose = () => {
		this.setState({ openModalSuccess: false, redirect: true });
	};

	handleResetSort = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/" + this.state.pharmacyId, {
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
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-grade-ascending/" + this.state.pharmacyId, {
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
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-price-descending/" + this.state.pharmacyId, {
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
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-price-ascending/" + this.state.pharmacyId, {
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
		Axios.get(BASE_URL + "/api/appointment/dermatologist/find-by-pharmacy/sort-by-price-descending/" + this.state.pharmacyId, {
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

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	render() {
		if (this.state.redirect) return <Redirect push to="/" />;
		if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<HeadingAlert
						hidden={this.state.hiddenFailAlert}
						header={this.state.failHeader}
						message={this.state.failMessage}
						handleCloseAlert={this.handleCloseAlertFail}
					/>
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
												{(Math.round(appointment.EntityDTO.price * 100) / 100).toFixed(2)}
											</span>
											<b> din</b>
										</div>
										<div hidden={appointment.EntityDTO.price === appointment.EntityDTO.discountPrice}>
											<b>Price with discount:</b> {(Math.round(appointment.EntityDTO.discountPrice * 100) / 100).toFixed(2)}
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

export default withRouter(Appointments);
