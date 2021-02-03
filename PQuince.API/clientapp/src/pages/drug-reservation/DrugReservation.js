import React, { Component } from "react";
import DrugsPage from "./DrugsPage";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import PharmacyPage from "./PharmacyPage";
import DrugReservationModal from "../../components/DrugReservationModal";
import ModalDialog from "../../components/ModalDialog";
import getAuthHeader from "../../GetHeader";
import { Redirect } from "react-router-dom";

class DrugReservation extends Component {
	state = {
		drugsPageHidden: false,
		reservationModalShow: false,
		openModal: false,
		pharmacies: [],
		pharmacyId: "",
		drugId: "",
		drugName: "",
		drugManufacturer: "",
		drugQuantity: "",
		drugSideEffects: "",
		drugRecommendedAmount: "",
		loyaltyPoints: "",
		drugFormat: "",
		drugKind: "",
		maxDrugAmount: 1,
		drugPrice: 0,
		redirect: false,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
	};
	handleBackIcon = () => {
		this.setState({ drugsPageHidden: false });
	};

	handleReservation = (amount, date) => {
		console.log(amount, date);
		this.setState({
			hiddenFailAlert: true,
			failHeader: "",
			failMessage: "",
		});
		let reservationDTO = {
			drugId: this.state.drugId,
			pharmacyId: this.state.pharmacyId,
			drugAmount: amount,
			drugPrice: this.state.drugPrice,
			endDate: date,
		};
		Axios.post(BASE_URL + "/api/drug/reserve", reservationDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if (res.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Bad request when resving drug." });
				} else if (res.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (res.status === 201) {
					console.log(res.data);
					this.setState({ reservationModalShow: false, openModal: true });
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));
		console.log(roles);

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

	handlePharmacyClick = (pharmacyId, price, count) => {
		if (this.hasRole("ROLE_PATIENT")) {
			this.setState({
				reservationModalShow: true,
				maxDrugAmount: count,
				drugPrice: price,
				pharmacyId: pharmacyId,
			});
		}
	};

	handleModalClose = () => {
		this.setState({ reservationModalShow: false });
	};
	handleModalSuccessClose = () => {
		this.setState({ openModal: false, redirect: true });
	};

	handleDrugSelect = (drug) => {
		console.log(drug);

		Axios.get(BASE_URL + "/api/pharmacy/find-by-drug/" + drug.Id, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({
					pharmacies: res.data,
					drugsPageHidden: true,
					drugName: drug.EntityDTO.drugInstanceName,
					drugManufacturer: drug.EntityDTO.manufacturer.EntityDTO.name,
					drugQuantity: drug.EntityDTO.quantity,
					drugId: drug.Id,
					drugSideEffects:  drug.EntityDTO.sideEffects,
					drugRecommendedAmount:  drug.EntityDTO.recommendedAmount,
					loyaltyPoints:  drug.EntityDTO.loyalityPoints,
					drugFormat: drug.EntityDTO.drugFormat,
					drugKind: drug.EntityDTO.drugKind,
 				});
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	render() {
		if (this.state.redirect) return <Redirect push to="/" />;

		return (
			<React.Fragment>
				<DrugsPage hidden={this.state.drugsPageHidden} onDrugSelect={this.handleDrugSelect} />
			
				<PharmacyPage
					onBackIcon={this.handleBackIcon}
					onPharmacyClick={this.handlePharmacyClick}
					hidden={!this.state.drugsPageHidden}
					pharmacies={this.state.pharmacies}
					drugQuantity={this.state.drugQuantity}
					drugManufacturer={this.state.drugManufacturer}
					drugName={this.state.drugName}
				/>
				<DrugReservationModal
					hiddenFailAlert={this.state.hiddenFailAlert}
					failHeader={this.state.failHeader}
					failMessage={this.state.failMessage}
					handleCloseAlertFail={this.handleCloseAlertFail}
					onCloseModal={this.handleModalClose}
					reserveDrugs={this.handleReservation}
					maxDrugAmount={this.state.maxDrugAmount}
					header="Drug reservation"
					drugPrice={this.state.drugPrice}
					show={this.state.reservationModalShow}
					drugQuantity={this.state.drugQuantity}
					drugManufacturer={this.state.drugManufacturer}
					drugName={this.state.drugName}
				/>
				<ModalDialog
					show={this.state.openModal}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully reserved"
					text="Your reservation is accepted. Further details are sent to your email address."
				/>
			</React.Fragment>
		);
	}
}

export default DrugReservation;
