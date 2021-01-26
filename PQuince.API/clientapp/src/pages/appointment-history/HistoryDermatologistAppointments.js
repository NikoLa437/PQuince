import React, { Component } from "react";
import AppointmentIcon from "../../static/appointment-icon.jpg";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import FeedbackCreateModal from "../../components/FeedbackCreateModal";

class HistoryDermatologistAppointments extends Component {
	state = {
		appointments: [],
		showingSorted: false,
		showFeedbackModal: false,
		showModifyFeedbackModal: false,
		selectedStaffId: "",
		StaffName: "",
		StaffSurame: "",
		grade: 0,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history")
			.then((res) => {
				this.setState({ appointments: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleResetSort = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history")
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: false });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByDateAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-date-ascending")
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByDateDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-date-descending")
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-price-ascending")
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-price-descending")
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByDurationAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-time-ascending")
			.then((res) => {
				this.setState({ appointments: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByDurationDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-time-descending")
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
		Axios.get(BASE_URL + "/api/staff/feedback/" + staff.Id, { validateStatus: () => true })
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
				} else {
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
		Axios.post(BASE_URL + "/api/staff/feedback", entityDTO)
			.then((resp) => {
				Axios.get(BASE_URL + "/api/appointment/dermatologist-history")
					.then((res) => {
						this.setState({ appointments: res.data, showFeedbackModal: false });
						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
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
		Axios.put(BASE_URL + "/api/staff/feedback", entityDTO)
			.then((resp) => {
				Axios.get(BASE_URL + "/api/appointment/dermatologist-history")
					.then((res) => {
						this.setState({ appointments: res.data, showModifyFeedbackModal: false });
						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleClickIcon = (grade) => {
		this.setState({ grade });
	};

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">
						Dermatologist Appointment History
					</h5>

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
										<div
											className="dropdown-menu"
											aria-labelledby="dropdownMenu2"
										>
											<button
												className="dropdown-item"
												type="button"
												onClick={this.handleSortByDateAscending}
											>
												Date ascending
											</button>
											<button
												className="dropdown-item"
												type="button"
												onClick={this.handleSortByDateDescending}
											>
												Date descending
											</button>
											<button
												className="dropdown-item"
												type="button"
												onClick={this.handleSortByPriceAscending}
											>
												Price ascending
											</button>
											<button
												className="dropdown-item"
												type="button"
												onClick={this.handleSortByPriceDescending}
											>
												Price descending
											</button>
											<button
												className="dropdown-item"
												type="button"
												onClick={this.handleSortByDurationAscending}
											>
												Appointment duration ascending
											</button>
											<button
												className="dropdown-item"
												type="button"
												onClick={this.handleSortByDurationDescending}
											>
												Appointment duration descending
											</button>
										</div>
									</div>
								</div>
								<div className="form-col ml-3">
									<div
										className={
											this.state.showingSorted
												? "form-group"
												: "form-group collapse"
										}
									>
										<button
											type="button"
											className="btn btn-outline-secondary"
											onClick={this.handleResetSort}
										>
											<i className="icofont-close-line mr-1"></i>Reset
											criteria
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
									<td
										width="190em"
										onClick={() => this.handleAppointmentClick(appointment.Id)}
									>
										<img
											className="img-fluid"
											src={AppointmentIcon}
											width="150em"
										/>
									</td>
									<td onClick={() => this.handleAppointmentClick(appointment.Id)}>
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
											<b>Price: </b> {appointment.EntityDTO.price} <b>din</b>
										</div>
										<div>
											<b>Dermatologist: </b>{" "}
											{appointment.EntityDTO.staff.EntityDTO.name +
												" " +
												appointment.EntityDTO.staff.EntityDTO.surname}
										</div>
										<div>
											<b>Dermatologist grade: </b>{" "}
											{appointment.EntityDTO.staff.EntityDTO.grade}
											<i
												className="icofont-star"
												style={{ color: "#1977cc" }}
											></i>
										</div>
									</td>
									<td className="align-middle">
										<button
											type="button"
											onClick={() =>
												this.handleFeedbackClick(
													appointment.EntityDTO.staff
												)
											}
											className="btn btn-outline-secondary"
										>
											Give feedback
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
					forWho="dermatologist"
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
					forWho="dermatologist"
					handleClickIcon={this.handleClickIcon}
				/>
			</React.Fragment>
		);
	}
}

export default HistoryDermatologistAppointments;
