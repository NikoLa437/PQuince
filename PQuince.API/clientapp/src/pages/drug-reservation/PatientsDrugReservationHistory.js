import React, { Component } from "react";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import { NavLink } from "react-router-dom";
import FeedbackCreateModal from "../../components/FeedbackCreateModal";
import getAuthHeader from "../../GetHeader";
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import HeadingAlert from "../../components/HeadingAlert";

class PatientsDrugReservationHistory extends Component {
	state = {
		drugReservations: [],
		showFeedbackModal: false,
		showModifyFeedbackModal: false,
		selectedDrugId: "",
		drugName: "",
		grade: 0,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug/processed-reservations", { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ drugReservations: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleFeedbackClick = (drug) => {
		console.log(drug);
		Axios.get(BASE_URL + "/api/drug/feedback/" + drug.Id, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						selectedDrugId: drug.Id,
						showFeedbackModal: true,
						drugName: drug.EntityDTO.name,
						grade: 0,
					});
				} else if (res.status === 200) {
					this.setState({
						selectedDrugId: drug.Id,
						showModifyFeedbackModal: true,
						drugName: drug.EntityDTO.name,
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
		this.setState({ hiddenFailAlert: true, failHeader: "", failMessage: "", hiddenSuccessAlert: true, successHeader: "", successMessage: "" });

		let entityDTO = {
			drugId: this.state.selectedDrugId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.post(BASE_URL + "/api/drug/feedback", entityDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((resp) => {
				if (resp.status === 405) {
					this.setState({ hiddenFailAlert: false, failHeader: "Not allowed", failMessage: "Drug feedback not allowed." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully sent." });
				}
				this.setState({ showFeedbackModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModifyFeedback = () => {
		this.setState({ hiddenFailAlert: true, failHeader: "", failMessage: "", hiddenSuccessAlert: true, successHeader: "", successMessage: "" });

		let entityDTO = {
			drugId: this.state.selectedDrugId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.put(BASE_URL + "/api/drug/feedback", entityDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((resp) => {
				if (resp.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Bad request when modifying feedback." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 204) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully modified." });
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
					<h5 className=" text-center mb-0 mt-2 text-uppercase">My drug reservations</h5>
					<nav className="nav nav-pills nav-justified justify-content-center mt-5">
						<NavLink className="nav-link" exact to="/drugs-reservation">
							Future reservations
						</NavLink>
						<NavLink className="nav-link active" exact to="/drugs-reservation-history">
							Reservation history
						</NavLink>
					</nav>
					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.drugReservations.map((drugReservation) => (
								<tr id={drugReservation.Id} key={drugReservation.Id}>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="75em" />
									</td>
									<td>
										<div>
											<b>Name:</b> {drugReservation.EntityDTO.drugInstance.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Amount:</b> {drugReservation.EntityDTO.amount}
										</div>
										<div>
											<b>Total price:</b>{" "}
											{(
												Math.round(drugReservation.EntityDTO.drugPeacePrice * drugReservation.EntityDTO.amount * 100) / 100
											).toFixed(2)}
											<b> din</b>
										</div>
									</td>
									<td className="align-middle">
										<button
											type="button"
											onClick={() => this.handleFeedbackClick(drugReservation.EntityDTO.drugInstance)}
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
					name={this.state.drugName}
					forWho="drug"
					handleClickIcon={this.handleClickIcon}
				/>
				<FeedbackCreateModal
					buttonName="Modify feedback"
					grade={this.state.grade}
					header="Modify feedback"
					show={this.state.showModifyFeedbackModal}
					onCloseModal={this.handleModifyFeedbackModalClose}
					giveFeedback={this.handleModifyFeedback}
					name={this.state.drugName}
					forWho="drug"
					handleClickIcon={this.handleClickIcon}
				/>
			</React.Fragment>
		);
	}
}

export default PatientsDrugReservationHistory;
