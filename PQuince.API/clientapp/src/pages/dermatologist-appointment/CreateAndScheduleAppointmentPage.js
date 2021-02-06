import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ModalDialog from "../../components/ModalDialog";
import getAuthHeader from "../../GetHeader";
import { withRouter } from "react-router";
import { Button, Modal } from 'react-bootstrap';
import DatePicker from "react-datepicker";
import PeriodIcon from "../../static/period-icon.png";
import { Redirect } from "react-router-dom";

class CreateAndScheduleAppointmentPage extends Component {
	state = {
		selectedDate: new Date(),
		duration: 10,
		periods: [],
		price: 300,
		openModalSuccess: false,
		redirect: false,
        redirectUrl: ''
	}

	handleDateChange = (date) => {
		this.setState({
			selectedDate: date,
		}, () => {
			this.fetchPeriods();
		});
	}

	handlePriceChange = (event) => {
		if (event.target.value < 50) {
			this.setState({
				price: 50,
			});
		} else {
			this.setState({
				price: event.target.value,
			});
		}
	}

	convertDate = str => {
		str = str.toString();
		let parts = str.split(" ");
		let months = {
			Jan: "01",
			Feb: "02",
			Mar: "03",
			Apr: "04",
			May: "05",
			Jun: "06",
			Jul: "07",
			Aug: "08",
			Sep: "09",
			Oct: "10",
			Nov: "11",
			Dec: "12"
		};
		return months[parts[1]] + "/" + parts[2] + "/" + parts[3];
	};

	handleSelectDurationChange = (event) => {
		if (event.target.value >= 10 && event.target.value <= 60) {
			this.setState({
				duration: event.target.value
			}, () => {
				this.fetchPeriods();
			});
		}
	}

	fetchPeriods = () => {
		Axios.get(BASE_URL + "/api/appointment/free-periods-dermatologist", {
			params: {
				date: this.convertDate(this.state.selectedDate),
				duration: this.state.duration
			},
			headers: { Authorization: getAuthHeader() }
		}).then((res) => {
			if (res.status === 401) {
				this.setState({
					redirect: true,
					redirectUrl: "/unauthorized"
				});
			} else {
				this.setState({ periods: res.data });
				console.log(res.data);
			}
		})
			.catch((err) => {
				console.log(err);
			});
	}

	fetchData = id => {
		this.setState({
			id: id
		});
	};

	componentDidMount() {
		const id = this.props.match.params.id;
		this.fetchData(id);
		this.fetchPeriods();
	}

	handleAppointment = (selectedPeriod) => {

		let appointmentDTO = {
			patientId: this.state.id,
			startDateTime: selectedPeriod.startDate,
			endDateTime: selectedPeriod.endDate,
			price: this.state.price,			
		};

		Axios
			.post(BASE_URL + "/api/appointment/create-and-schedule-appointment", appointmentDTO, {
				headers: { Authorization: getAuthHeader() },
			}).then((res) => {
				console.log(res.data);
				this.setState({
					openModalSuccess: true,
				});
			}).catch((err) => {
				alert("Appointment can't be created in selected period");
			});
	}

	handleModalSuccessClose = () => {
		this.setState({
			openModalSuccess: false,
			redirect: true,
			redirectUrl: "/patient-profile/" + this.state.id
		});
	};

	render() {

		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h4 className=" text-left mb-0 mt-2 text-uppercase" style={{ color: "#6c757d"}}>Create and schedule Appointment</h4>
					<p className="mb-0 mt-2">
						Select date and appointment duration and pick desired period to create and schedule appointment for patient
					</p>
						<form >
							<div className="control-group" >
								<table style={{ width: '50%', marginTop: '40px'}}>
								<tbody>
									<tr>
										<td>
											<h6 className="text-right mr-3 mt-2">Select date:</h6>
										</td>
										<td>  
											<DatePicker style={{width: "12em"}} className="form-control" minDate={new Date()} onChange={date => this.handleDateChange(date)} selected={this.state.selectedDate} />
										</td>
									</tr>
									<tr>
										<td>
											<h6 className="text-right mr-3 mt-2">Select duration:</h6>
										</td>
										<td>
											<input style={{width: "12.5em"}} placeholder="Duration" className="form-control" type="number" min="10" max="60" step="5" onChange={this.handleSelectDurationChange} value={this.state.duration} />
										</td>
									</tr>
									<tr>
										<td>
											<h6 className="text-right mr-3 mt-2">Price:</h6>
										</td>
										<td>
											<input style={{width: "12.5em"}} placeholder="Price" className="form-control" type="number" min="50" step="50" onChange={this.handlePriceChange} value={this.state.price} />
										</td>
									</tr>
									<tr>
										<td>
											<h5 className="text-left mr-3 mt-2">Pick desired period to create and schedule appointment:</h5>
										</td>

									</tr>
								</tbody>
								</table>
								<table className="table table-hover" style={{ width: "27%"}} >
									<tbody>
										{this.state.periods.map((period, index) => (
											<tr
												key={index}
												value={period}
												onClick={() => this.handleAppointment(period)}
												className="rounded"
												style={{ cursor: "pointer" }}
											>
												<td>
												<img
													style={{float: "left"}}
													className="img-fluid"
													src={PeriodIcon}
													width="25em"
												/>
												</td>
												<td>
													<div>
														{new Date(
															period.startDate
														).toLocaleTimeString("en-US", {
															hour: "2-digit",
															minute: "2-digit",
														})}
														{" - "}
														{new Date(
															period.endDate
														).toLocaleTimeString("en-US", {
															hour: "2-digit",
															minute: "2-digit",
														})}
													</div>
												</td>
												<td>
													
												</td>
											</tr>
										))}
									</tbody>
								</table>
							</div>
						</form>
				</div>
				<ModalDialog
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully created and scheduled appointment for patient"
					text="Start examination for scheduled appointment."
				/>
			</React.Fragment>
		);
	}
}
export default withRouter(CreateAndScheduleAppointmentPage);