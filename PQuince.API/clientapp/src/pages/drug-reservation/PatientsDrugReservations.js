import React, { Component } from "react";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import ModalDialog from "../../components/ModalDialog";
import ReservationModalInfo from "../../components/ReservationInfoModal";
import { NavLink } from "react-router-dom";
import getAuthHeader from "../../GetHeader";

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
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug/future-reservations", { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ drugReservations: res.data });
				console.log(res.data);
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
		Axios.put(BASE_URL + "/api/drug/reservations/cancel", { id: reservationId }, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
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
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModalSuccessClose = () => {
		this.setState({ showSuccessModal: false });
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
		});
	};

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
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
					href="/"
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully canceled"
					text="Your reservation is succesfully canceled."
				/>
				<ReservationModalInfo
					header="Reservation info"
					onCloseModal={this.handleModalInfoClose}
					show={this.state.showInfoModal}
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
