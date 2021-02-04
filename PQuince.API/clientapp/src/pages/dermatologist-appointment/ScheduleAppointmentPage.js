import React, { Component } from "react";
import AppointmentIcon from "../../static/appointment-icon.jpg";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ModalDialog from "../../components/ModalDialog";
import getAuthHeader from "../../GetHeader";
import { withRouter } from "react-router";

class ScheduleAppointmentPage extends Component {
	state = {
		id: "",
		appointments: [],
		openModalSuccess: false,
	};

	fetchData = id => {
		this.setState({
			id:id
		});
	};

	componentDidMount() {
		const id = this.props.match.params.id;
		this.fetchData(id);

		Axios.get(BASE_URL + "/api/appointment/dermatologist/created", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ appointments: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleAppointmentClick = (appointmentId) => {
		Axios.post(BASE_URL + "/api/appointment/schedule-appointment",
			{ appointmentId: appointmentId, patientId: this.state.id },
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
		this.setState({ openModalSuccess: false });
	};

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Schedule Appointment</h5>

					<p className="mb-0 mt-2 text-uppercase">
						Click on appointment to schedule
					</p>

					<table
						className="table table-hover"
						style={{ width: "70%", marginTop: "3rem" }}
					>
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
											<b>Price: </b> {appointment.EntityDTO.price} <b>din</b>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<ModalDialog
					show={this.state.openModalSuccess}
					href={"/patient-profile/" + this.state.id}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully scheduled appointment for patient"
					text="Start examination for scheduled appointment."
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(ScheduleAppointmentPage);
