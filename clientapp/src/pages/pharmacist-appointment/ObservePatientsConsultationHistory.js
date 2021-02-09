import React, { Component } from "react";
import AppointmentIcon from "../../static/appointment-icon.jpg";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import { NavLink, Redirect } from "react-router-dom";
import FeedbackCreateModal from "../../components/FeedbackCreateModal";
import getAuthHeader from "../../GetHeader";
import ComplaintCreateModal from "../../components/ComplaintCreateModal";
import HeadingAlert from "../../components/HeadingAlert";
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";

class ObservePatientsCosultationHistory extends Component {
	state = {
		appointments: [],
		showingSorted: false,
		showFeedbackModal: false,
		showModifyFeedbackModal: false,
		selectedStaffId: "",
		showComplaintModal: false,
		complaint: "",
		StaffName: "",
		StaffSurame: "",
		grade: 0,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		unauthorizedRedirect: false,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/appointment/pharmacist-history", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else {
					console.log(res.data);
					this.setState({ appointments: res.data });
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}
	handleComplaintClick = (staff) => {
		console.log(staff);
		Axios.get(BASE_URL + "/api/staff/feedback/" + staff.Id, { validateStatus: () => true })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						selectedStaffId: staff.Id,
						showComplaintModal: true,
						StaffName: staff.EntityDTO.name,
						StaffSurame: staff.EntityDTO.surname,
						grade: 0,
					});
				} else {
					this.setState({
						selectedStaffId: staff.Id,
						showComplaintModal: true,
						StaffName: staff.EntityDTO.name,
						StaffSurame: staff.EntityDTO.surname,
						grade: res.data.grade,
					});
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleComplaintChange = (event) => {
		this.setState({ complaint: event.target.value });
	};

	handleComplaintModalClose = () => {
		this.setState({ showComplaintModal: false });
	};

	handleResetSort = () => {
		Axios.get(BASE_URL + "/api/appointment/pharmacist-history", { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: false });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByDateAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/appointment-history/sort-by-date-ascending?appointmentType=CONSULTATION", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				console.log(res.data);
				this.setState({ appointments: res.data, showingSorted: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByDateDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/appointment-history/sort-by-date-descending?appointmentType=CONSULTATION", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				console.log(res.data);
				this.setState({ appointments: res.data, showingSorted: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/appointment-history/sort-by-price-ascending?appointmentType=CONSULTATION", {
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
		Axios.get(BASE_URL + "/api/appointment/appointment-history/sort-by-price-descending?appointmentType=CONSULTATION", {
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

	handleSortByDurationAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/appointment-history/sort-by-time-ascending?appointmentType=CONSULTATION", {
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

	handleSortByDurationDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/appointment-history/sort-by-time-descending?appointmentType=CONSULTATION", {
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

	handleFeedbackClick = (staff) => {
		console.log(staff);
		Axios.get(BASE_URL + "/api/staff/feedback/" + staff.Id, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						selectedStaffId: staff.Id,
						showFeedbackModal: true,
						StaffName: staff.EntityDTO.name,
						StaffSurame: staff.EntityDTO.surname,
						grade: 0,
					});
				} else if (res.status === 200) {
					this.setState({
						selectedStaffId: staff.Id,
						showModifyFeedbackModal: true,
						StaffName: staff.EntityDTO.name,
						StaffSurame: staff.EntityDTO.surname,
						grade: res.data.grade,
					});
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleFeedbackModalClose = () => {
		this.setState({ showFeedbackModal: false });
	};

	handleModifyFeedbackModalClose = () => {
		this.setState({ showModifyFeedbackModal: false });
	};

	handleFeedback = () => {
		let entityDTO = {
			staffId: this.state.selectedStaffId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.post(BASE_URL + "/api/staff/feedback", entityDTO, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((resp) => {
				if (resp.status === 405) {
					this.setState({ hiddenFailAlert: false, failHeader: "Not allowed", failMessage: "Staff feedback not allowed." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully saved." });
					Axios.get(BASE_URL + "/api/appointment/pharmacist-history", {
						headers: { Authorization: getAuthHeader() },
					})
						.then((res) => {
							this.setState({ appointments: res.data, showFeedbackModal: false });
							console.log(res.data);
						})
						.catch((err) => {
							console.log(err);
						});
				}
				this.setState({ showFeedbackModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleComaplaint = () => {
		let entityDTO = {
			staffId: this.state.selectedStaffId,
			date: new Date(),
			text: this.state.complaint,
			staffName: this.state.StaffName,
			staffSurname: this.state.StaffSurame,
			profession: "pharmacist",
		};
		
		Axios.post(BASE_URL + "/api/staff/complaint", entityDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((resp) => {
				if (resp.status === 405) {
					this.setState({ hiddenFailAlert: false, failHeader: "Not allowed", failMessage: "Staff complaint not allowed." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Complaint successfully saved." });
					Axios.get(BASE_URL + "/api/appointment/pharmacist-history", {
						headers: { Authorization: getAuthHeader() },
					})
						.then((res) => {
							this.setState({ appointments: res.data, showComplaintModal: false });
							console.log(res.data);
						})
						.catch((err) => {
							console.log(err);
						});
				}
				this.setState({ showComplaintModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
		
	};

	handleModifyFeedback = () => {
		let entityDTO = {
			staffId: this.state.selectedStaffId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.put(BASE_URL + "/api/staff/feedback", entityDTO, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((resp) => {
				if (resp.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Bad request when modifying feedback." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 204) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully modified." });
					Axios.get(BASE_URL + "/api/appointment/pharmacist-history", {
						headers: { Authorization: getAuthHeader() },
					})
						.then((res) => {
							this.setState({ appointments: res.data, showModifyFeedbackModal: false });
							console.log(res.data);
						})
						.catch((err) => {
							console.log(err);
						});
				}
				this.setState({ showModifyFeedbackModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleClickIcon = (grade) => {
		this.setState({ grade });
	};

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
	};

	render() {
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

					<HeadingSuccessAlert
						hidden={this.state.hiddenSuccessAlert}
						header={this.state.successHeader}
						message={this.state.successMessage}
						handleCloseAlert={this.handleCloseAlertSuccess}
					/>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Consultations</h5>
					<nav className="nav nav-pills nav-justified justify-content-center mt-5">
						<NavLink className="nav-link " exact to="/observe-consultations">
							Future consultations
						</NavLink>
						<NavLink className="nav-link active" exact to="/observe-consultations-history">
							Consultations history
						</NavLink>
					</nav>
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
											<button className="dropdown-item" type="button" onClick={this.handleSortByDateAscending}>
												Date ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByDateDescending}>
												Date descending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByPriceAscending}>
												Price ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByPriceDescending}>
												Price descending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByDurationAscending}>
												Appointment duration ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByDurationDescending}>
												Appointment duration descending
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

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.appointments.map((appointment) => (
								<tr id={appointment.Id} key={appointment.Id} className="rounded">
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
											<b>Price: </b> {(Math.round(appointment.EntityDTO.price * 100) / 100).toFixed(2)} <b>din</b>
										</div>
										<div>
											<b>Pharmacst: </b>{" "}
											{appointment.EntityDTO.staff.EntityDTO.name + " " + appointment.EntityDTO.staff.EntityDTO.surname}
										</div>
										<div>
											<b>Pharmacst grade: </b> {appointment.EntityDTO.staff.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
									</td>
									<td className="align-middle">
										<button
											type="button"
											onClick={() => this.handleFeedbackClick(appointment.EntityDTO.staff)}
											className="btn btn-outline-secondary"
										>
											Give feedback
										</button>
										<br></br>
										<br></br>
										<button
											type="button"
											onClick={() => this.handleComplaintClick(appointment.EntityDTO.staff)}
											className="btn btn-outline-secondary"
										>
											Make complaint
										</button>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>

				<FeedbackCreateModal
					buttonName="Give feedback"
					grade={this.state.grade}
					header="Give feedback"
					show={this.state.showFeedbackModal}
					onCloseModal={this.handleFeedbackModalClose}
					giveFeedback={this.handleFeedback}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="pharmacist"
					handleClickIcon={this.handleClickIcon}
				/>
				<FeedbackCreateModal
					buttonName="Modify feedback"
					grade={this.state.grade}
					header="Modify feedback"
					show={this.state.showModifyFeedbackModal}
					onCloseModal={this.handleModifyFeedbackModalClose}
					giveFeedback={this.handleModifyFeedback}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="pharmacist"
					handleClickIcon={this.handleClickIcon}
				/>
				<ComplaintCreateModal
					buttonName="Send complaint"
					header="Give complaint"
					handleComplaintChange={this.handleComplaintChange}
					show={this.state.showComplaintModal}
					onCloseModal={this.handleComplaintModalClose}
					giveFeedback={this.handleComaplaint}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="consultant"
					handleClickIcon={this.handleClickIcon}
					complaint={this.state.complaint}
				/>
			</React.Fragment>
		);
	}
}

export default ObservePatientsCosultationHistory;
