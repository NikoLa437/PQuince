import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import DatePicker from "react-datepicker";
import getAuthHeader from "../GetHeader";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";
import { Redirect } from "react-router-dom";

class WorkTimesModal extends Component {
	state = {
		workTimes: [],
		showAddWorkTime: false,
		selectedStartDate: new Date(),
		selectedEndDate: new Date(),
		timeFrom: 1,
		timeTo: 2,
		modalSize: "lg",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
	};

	handleBack = (event) => {
		this.setState({
			showAddWorkTime: false,
			modalSize: "lg",
			selectedStartDate: new Date(),
			selectedEndDate: new Date(),
			timeFrom: 1,
			timeTo: 2,
		});
	};

	handleAddWorkTimeModal = (event) => {
		this.setState({ showAddWorkTime: true, modalSize: "md" });
	};

	handleStartDateChange = (date) => {
		this.setState({
			selectedStartDate: date,
		});

		if (date > this.state.selectedEndDate) {
			this.setState({
				selectedEndDate: date,
			});
		}
	};

	handleEndDateChange = (date) => {
		this.setState({ selectedEndDate: date });
	};

	handleTimeFromChange = (event) => {
		if (event.target.value > 23) {
			this.setState({ timeFrom: 23 });
		} else if (event.target.value < 1) {
			this.setState({ timeFrom: 1 });
		} else {
			this.setState({ timeFrom: event.target.value });
		}
	};

	handleTimeToChange = (event) => {
		if (event.target.value > 24) {
			this.setState({ timeTo: 24 });
		} else if (event.target.value < 2) {
			this.setState({ timeTo: 2 });
		} else {
			this.setState({ timeTo: event.target.value });
		}
	};

	validateDTO = (wtDTO) => {
		console.log(wtDTO);
		if (parseInt(wtDTO.startTime) < parseInt(wtDTO.endTime)) {
			return false;
		}

		return true;
	};

	handleAdd = () => {
		let workTimeDTO = {
			forPharmacy: this.props.forPharmacy,
			forStaff: this.props.forStaff,
			pharmacyName: "",
			startDate: this.state.selectedStartDate,
			endDate: this.state.selectedEndDate,
			startTime: this.state.timeFrom,
			endTime: this.state.timeTo,
		};

		if (this.validateDTO(workTimeDTO)) {
			this.setState({
				hiddenSuccessAlert: true,
				hiddenFailAlert: false,
				failHeader: "Unsuccess",
				failMessage: "Time from must be least time to",
			});
		} else {
			Axios.post(BASE_URL + "/api/worktime/", workTimeDTO, {
				headers: { Authorization: getAuthHeader() },
			})
				.then((res) => {
					this.setState({ showAddWorkTime: false, modalSize: "lg" });
					this.setState({
						hiddenSuccessAlert: false,
						hiddenFailAlert: true,
						successHeader: "Success",
						successMessage: "You successfully add worktime for staff.",
					});
					this.props.updateWorkTimes();
				})
				.catch((err) => {
					this.setState({
						hiddenSuccessAlert: true,
						hiddenFailAlert: false,
						failHeader: "Unsuccess",
						failMessage: "It is not possible to add worktime",
					});
				});
		}
	};

	handleClickOnClose = () => {
		this.setState({
			showAddWorkTime: false,
			modalSize: "lg",
			selectedStartDate: new Date(),
			selectedEndDate: new Date(),
			timeFrom: 1,
			timeTo: 2,
			hiddenSuccessAlert: true,
			hiddenFailAlert: true,
		});
		this.props.onCloseModal();
	};

	handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
	};

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	render() {
		if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
			<Modal
				show={this.props.show}
				size={this.state.modalSize}
				dialogClassName="modal-120w-100h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
			>
				<Modal.Header>
					<Modal.Title style={{ marginLeft: "37%" }} id="contained-modal-title-vcenter">
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<HeadingSuccessAlert
						hidden={this.state.hiddenSuccessAlert}
						header={this.state.successHeader}
						message={this.state.successMessage}
						handleCloseAlert={this.handleCloseAlertSuccess}
					/>
					<HeadingAlert
						hidden={this.state.hiddenFailAlert}
						header={this.state.failHeader}
						message={this.state.failMessage}
						handleCloseAlert={this.handleCloseAlertFail}
					/>
					<div hidden={this.state.showAddWorkTime}>
						<Button style={{ marginBottom: "3%" }} onClick={() => this.handleAddWorkTimeModal()}>
							Add worktime
						</Button>

						<table border="1" style={{ width: "100%" }}>
							<tr>
								<th>Pharmacy</th>
								<th>StartDate</th>
								<th>EndDate</th>
								<th>StartTime</th>
								<th>EndTime</th>
							</tr>
							{this.props.workTimesForStaff.map((workTime) => (
								<tr>
									<td>{workTime.EntityDTO.pharmacyName}</td>
									<td>{new Date(workTime.EntityDTO.startDate).toDateString()}</td>
									<td>{new Date(workTime.EntityDTO.endDate).toDateString()}</td>
									<td>{workTime.EntityDTO.startTime}</td>
									<td>{workTime.EntityDTO.endTime}</td>
								</tr>
							))}
						</table>
					</div>

					<div hidden={!this.state.showAddWorkTime}>
						<form>
							<div className="control-group">
								<div className="form-row">
									<button onClick={() => this.handleBack()} className="btn btn-link btn-xl" type="button">
										<i className="icofont-rounded-left mr-1"></i>
										Back
									</button>
								</div>
								<table style={({ width: "100%" }, { marginLeft: "17%" })}>
									<tr>
										<td>
											<label>Date from:</label>
										</td>
										<td>
											<DatePicker
												className="form-control"
												style={{ width: "15em" }}
												minDate={new Date()}
												onChange={(date) => this.handleStartDateChange(date)}
												selected={this.state.selectedStartDate}
											/>
										</td>
									</tr>
									<tr>
										<td>
											<label>Date to:</label>
										</td>
										<td>
											<DatePicker
												className="form-control"
												style={{ width: "15em" }}
												minDate={this.state.selectedStartDate}
												onChange={(date) => this.handleEndDateChange(date)}
												selected={this.state.selectedEndDate}
											/>
										</td>
									</tr>
									<tr>
										<td>
											<label>Time from:</label>
										</td>
										<td>
											<input
												placeholder="Time from"
												className="form-control"
												style={{ width: "12.8em" }}
												type="number"
												min="1"
												max="23"
												onChange={this.handleTimeFromChange}
												value={this.state.timeFrom}
											/>
										</td>
									</tr>

									<tr>
										<td>
											<label>Time to:</label>
										</td>
										<td>
											<input
												placeholder="Time to"
												className="form-control"
												style={{ width: "12.8em" }}
												type="number"
												min="2"
												max="24"
												onChange={this.handleTimeToChange}
												value={this.state.timeTo}
											/>
										</td>
									</tr>
								</table>
								<div className="form-group text-center">
									<Button className="mt-3" onClick={() => this.handleAdd()}>
										Add worktime
									</Button>
								</div>
							</div>
						</form>
					</div>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={() => this.handleClickOnClose()}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default WorkTimesModal;
