import React, { Component } from "react";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import ModalDialog from "../../components/ModalDialog";
import ReservationModalInfo from "../../components/ReservationInfoModal";
import { NavLink, Redirect } from "react-router-dom";
import getAuthHeader from "../../GetHeader";
import HeadingAlert from "../../components/HeadingAlert";

class PatientsDrugReservations extends Component {
	state = {
		drugReservations: [],
		showSuccessModal: false,
		showInfoModal: false,
		drugName: "",
		drugManufacturer: "",
		drugQuantity: "",
		drugPrice: "",
		pharmacyName: "",
		pharmacyAddress: "",
		endDate: "",
		drugAmount: "",
		reservationStatus: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		reservationCode: "",
		unauthorizedRedirect: false,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug/future-reservations", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else {
					this.setState({ drugReservations: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}

	addDays = (date, days) => {
		var result = new Date(date);
		result.setDate(result.getDate() + days);
		return result;
	};

	handleCancelDrugReservation = (reservationId) => {
		this.setState({ hiddenFailAlert: true, failHeader: "", failMessage: "" });
		Axios.put(
			BASE_URL + "/api/drug/reservations/cancel",
			{ id: reservationId },
			{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				if (res.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Bad request when canceling drug reservation." });
				} else if (res.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (res.status === 204) {
					let reservations = [...this.state.drugReservations];
					for (let reservation of reservations) {
						if (reservation.Id === reservationId) {
							reservation.EntityDTO.reservationStatus = "CANCELED";
							break;
						}
					}
					this.setState({
						drugReservations: reservations,
						showSuccessModal: true,
					});
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModalSuccessClose = () => {
		this.setState({ showSuccessModal: false, redirect: true });
	};

	handleModalInfoClose = () => {
		this.setState({ showInfoModal: false });
	};

	handleSelectReservation = (drugReservation) => {
		this.setState({
			showInfoModal: true,
			drugName: drugReservation.EntityDTO.drugInstance.EntityDTO.drugInstanceName,
			drugManufacturer: drugReservation.EntityDTO.drugInstance.EntityDTO.manufacturer.EntityDTO.name,
			drugQuantity: drugReservation.EntityDTO.drugInstance.EntityDTO.quantity,
			drugPrice: drugReservation.EntityDTO.drugPeacePrice,
			pharmacyName: drugReservation.EntityDTO.pharmacy.EntityDTO.name,
			pharmacyAddress: drugReservation.EntityDTO.pharmacy.EntityDTO.address,
			endDate: drugReservation.EntityDTO.endDate,
			drugAmount: drugReservation.EntityDTO.amount,
			reservationStatus: drugReservation.EntityDTO.reservationStatus,
			reservationCode: drugReservation.Id,
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
					<h5 className=" text-center mb-0 mt-2 text-uppercase">My drug reservations</h5>
					<nav className="nav nav-pills nav-justified justify-content-center mt-5">
						<NavLink className="nav-link active" exact to="/drugs-reservation">
							Future reservations
						</NavLink>
						<NavLink className="nav-link" exact to="/drugs-reservation-history">
							Reservation history
						</NavLink>
					</nav>
					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.drugReservations.map((drugReservation) => (
								<tr id={drugReservation.Id} key={drugReservation.Id} style={{ cursor: "pointer" }}>
									<td width="130em" onClick={() => this.handleSelectReservation(drugReservation)}>
										<img className="img-fluid" src={CapsuleLogo} width="110em" />
									</td>
									<td onClick={() => this.handleSelectReservation(drugReservation)}>
										<div>
											<b>Reservation code:</b> {drugReservation.Id}
										</div>
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
										<div>
											<b>Reserved until: </b>
											{new Date(drugReservation.EntityDTO.endDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
									</td>
									<td class="align-middle">
										<button
											type="button"
											hidden={
												this.addDays(new Date(drugReservation.EntityDTO.endDate), -1) <= new Date() ||
												drugReservation.EntityDTO.reservationStatus === "CANCELED" ||
												drugReservation.EntityDTO.reservationStatus === "PROCESSED" ||
												drugReservation.EntityDTO.reservationStatus === "EXPIRED"
											}
											onClick={() => this.handleCancelDrugReservation(drugReservation.Id)}
											class="btn btn-outline-danger"
										>
											Cancel
										</button>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<ModalDialog
					show={this.state.showSuccessModal}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully canceled"
					text="Your reservation is succesfully canceled."
				/>
				<ReservationModalInfo
					header="Reservation info"
					onCloseModal={this.handleModalInfoClose}
					show={this.state.showInfoModal}
					reservationCode={this.state.reservationCode}
					pharmacyName={this.state.pharmacyName}
					pharmacyAddress={this.state.pharmacyAddress}
					drugName={this.state.drugName}
					drugManufacturer={this.state.drugManufacturer}
					drugAmount={this.state.drugAmount}
					drugPrice={this.state.drugPrice}
					drugQuantity={this.state.drugQuantity}
					endDate={this.state.endDate}
					reservationStatus={this.state.reservationStatus}
				/>
			</React.Fragment>
		);
	}
}

export default PatientsDrugReservations;
