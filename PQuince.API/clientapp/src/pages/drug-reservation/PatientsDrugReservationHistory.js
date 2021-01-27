import React, { Component } from "react";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import { NavLink } from "react-router-dom";
import FeedbackCreateModal from "../../components/FeedbackCreateModal";

class PatientsDrugReservationHistory extends Component {
	state = {
		drugReservations: [],
		showFeedbackModal: false,
		showModifyFeedbackModal: false,
		selectedDrugId: "",
		drugName: "",
		grade: 0,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug/processed-reservations")
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
		Axios.get(BASE_URL + "/api/drug/feedback/" + drug.Id, { validateStatus: () => true })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						selectedDrugId: drug.Id,
						showFeedbackModal: true,
						drugName: drug.EntityDTO.name,
						grade: 0,
					});
				} else {
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
		let entityDTO = {
			drugId: this.state.selectedDrugId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.post(BASE_URL + "/api/drug/feedback", entityDTO)
			.then((resp) => {
				this.setState({ showFeedbackModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModifyFeedback = () => {
		let entityDTO = {
			drugId: this.state.selectedDrugId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.put(BASE_URL + "/api/drug/feedback", entityDTO)
			.then((resp) => {
				this.setState({ showModifyFeedbackModal: false });
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
					<h5 className=" text-center mb-0 mt-2 text-uppercase">My drug reservations</h5>
					<nav className="nav nav-pills nav-justified justify-content-center mt-5">
						<NavLink className="nav-link" exact to="/drugs-reservation">
							Future reservations
						</NavLink>
						<NavLink className="nav-link active" exact to="/drugs-reservation-history">
							Reservation history
						</NavLink>
					</nav>
					<table
						className="table table-hover"
						style={{ width: "100%", marginTop: "3rem" }}
					>
						<tbody>
							{this.state.drugReservations.map((drugReservation) => (
								<tr id={drugReservation.Id} key={drugReservation.Id}>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="75em" />
									</td>
									<td>
										<div>
											<b>Name:</b>{" "}
											{
												drugReservation.EntityDTO.drugInstance.EntityDTO
													.drugInstanceName
											}
										</div>
										<div>
											<b>Amount:</b> {drugReservation.EntityDTO.amount}
										</div>
										<div>
											<b>Total price:</b>{" "}
											{drugReservation.EntityDTO.drugPeacePrice *
												drugReservation.EntityDTO.amount}
											<b> din</b>
										</div>
									</td>
									<td className="align-middle">
										<button
											type="button"
											onClick={() =>
												this.handleFeedbackClick(
													drugReservation.EntityDTO.drugInstance
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
