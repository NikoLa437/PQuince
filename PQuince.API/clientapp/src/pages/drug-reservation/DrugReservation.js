import React, { Component } from "react";
import DrugsPage from "./DrugsPage";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import PharmacyPage from "./PharmacyPage";
import DrugReservationModal from "../../components/DrugReservationModal";
import ModalDialog from "../../components/ModalDialog";

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
		maxDrugAmount: 1,
		drugPrice: 0,
	};
	handleBackIcon = () => {
		this.setState({ drugsPageHidden: false });
	};

	handleReservation = (amount, date) => {
		console.log(amount, date);

		let reservationDTO = {
			drugId: this.state.drugId,
			pharmacyId: this.state.pharmacyId,
			drugAmount: amount,
			drugPrice: this.state.drugPrice,
			endDate: date,
		};
		Axios.post(BASE_URL + "/api/drug/reserve", reservationDTO)
			.then((res) => {
				console.log(res.data);
				this.setState({ reservationModalShow: false, openModal: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handlePharmacyClick = (pharmacyId, price, count) => {
		this.setState({
			reservationModalShow: true,
			maxDrugAmount: count,
			drugPrice: price,
			pharmacyId: pharmacyId,
		});
	};

	handleModalClose = () => {
		this.setState({ reservationModalShow: false });
	};
	handleModalSuccessClose = () => {
		this.setState({ openModal: false });
	};

	handleDrugSelect = (drug) => {
		console.log(drug);
		Axios.get(BASE_URL + "/api/pharmacy/find-by-drug/" + drug.Id)
			.then((res) => {
				console.log(res.data);
				this.setState({
					pharmacies: res.data,
					drugsPageHidden: true,
					drugName: drug.EntityDTO.drugInstanceName,
					drugManufacturer: drug.EntityDTO.manufacturer.EntityDTO.name,
					drugQuantity: drug.EntityDTO.quantity,
					drugId: drug.Id,
				});
			})
			.catch((err) => {
				console.log(err);
			});
	};

	render() {
		return (
			<React.Fragment>
				<DrugsPage
					hidden={this.state.drugsPageHidden}
					onDrugSelect={this.handleDrugSelect}
				/>
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
					href="/"
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully reserved"
					text="Your reservation is accepted. Further details are sent to your email address."
				/>
			</React.Fragment>
		);
	}
}

export default DrugReservation;
