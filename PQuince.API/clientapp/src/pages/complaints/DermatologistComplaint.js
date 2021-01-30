import React, { Component } from "react";
import AppointmentIcon from "../../static/appointment-icon.jpg";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ComplaintCreateModal from "../../components/ComplaintCreateModal";

class DermatologistComplaint extends Component {
	state = {
		appointments: [],
		showingSorted: false,
		showFeedbackModal: false,
		selectedStaffId: "",
		StaffName: "",
		StaffSurame: "",
		complaint: "",
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


	handleComplaintChange = (event) => {
		this.setState({ complaint: event.target.value });
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
						showFeedbackModal: true,
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

	handleComaplaint = () => {
		let entityDTO = {
			staffId: this.state.selectedStaffId,
			date: new Date(),
			text: this.state.complaint,
		};
		Axios.post(BASE_URL + "/api/staff/complaint", entityDTO)
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


	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Dermatologist Appointment History</h5>


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
											<b>Price: </b> {appointment.EntityDTO.price} <b>din</b>
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
									<td className="align-middle">
										<button
											type="button"
											onClick={() => this.handleFeedbackClick(appointment.EntityDTO.staff)}
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

				<ComplaintCreateModal
					buttonName="Send complaint"
					header="Give complaint"
					handleComplaintChange={this.handleComplaintChange}
					show={this.state.showFeedbackModal}
					onCloseModal={this.handleFeedbackModalClose}
					giveFeedback={this.handleComaplaint}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="dermatologist"
					handleClickIcon={this.handleClickIcon}
					complaint={this.state.complaint}
				/>
			</React.Fragment>
		);
	}
}

export default DermatologistComplaint;
