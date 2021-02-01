import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import CapsuleLogo from "../static/capsuleLogo.png";
import DatePicker from "react-datepicker";

import Axios from "axios";
import { BASE_URL } from "../constants.js";

class DrugsInPharmacyModal extends Component {
	state = {
		drugs: [],
		selectedDate: new Date(),
		drugAmount: 1,
		showReservation: false,
		drugId: "",
		drugName: "",
		pharmacyId: "",
		drugPrice: "",
		drugQuantity: "",
		endDate: "",
		drugManufacturer: "",
	};

	handleClickOnDrug = (drug) => {
		Axios.get(BASE_URL + "/api/pharmacy/find-by-drug-in-pharmacy?drugId=" + drug.Id + "&pharmacyId=" + this.props.pharmacyId)
			.then((res) => {
				this.setState({
					drugName: res.data.EntityDTO.name,
					drugManufacturer: drug.EntityDTO.manufacturer.EntityDTO.name,
					drugQuantity: drug.EntityDTO.quantity,
					showReservation: true,
					drugPrice: res.data.price,
					drugId: drug.Id,
				});
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleReservation = (event) => {
		let reservationDTO = {
			drugId: this.state.drugId,
			pharmacyId: this.props.pharmacyId,
			drugAmount: this.state.drugAmount,
			drugPrice: this.state.drugPrice,
			endDate: this.state.selectedDate,
		};

		alert(this.state.amount);
		Axios.post(BASE_URL + "/api/drug/reserve", reservationDTO)
			.then((res) => {
				console.log(res.data);
				this.setState({ showReservation: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleGradeFromChange = (event) => {
		this.setState({ drugAmount: event.target.value });
	};

	handleBack = (event) => {
		this.setState({ showReservation: false });
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug/find-drug-by-pharmacy?pharmacyId=cafeddee-56cb-11eb-ae93-0242ac130002" + this.props.pharmacyId)
			.then((res) => {
				this.setState({ drugs: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	render() {
		return (
			<Modal
				show={this.props.show}
				size="lg"
				dialogClassName="modal-80w-100h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title style={{ marginLeft: "35%" }} id="contained-modal-title-vcenter">
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<div className="container">
						<table hidden={this.state.showReservation} className="table" style={{ width: "100%" }}>
							<tbody>
								{this.state.drugs.map((drug) => (
									<tr hidden={false} id={drug.Id} key={drug.Id} onClick={() => this.handleClickOnDrug(drug)}>
										<td width="130em">
											<img className="img-fluid" src={CapsuleLogo} width="70em" />
										</td>
										<td>
											<div>
												<b>Name:</b> {drug.EntityDTO.name}
											</div>
											<div>
												<b>Manufacturer:</b> {drug.EntityDTO.manufacturer.EntityDTO.name}
											</div>
											<div>
												<b>Quantity:</b> {drug.EntityDTO.quantity} <b>mg</b>
											</div>
										</td>
									</tr>
								))}
							</tbody>
						</table>

						<div hidden={!this.state.showReservation}>
							<form className="ml-3">
								<div className="control-group">
									<div className="form-row">
										<button onClick={() => this.handleBack()} className="btn btn-link btn-xl" type="button">
											<i className="icofont-rounded-left mr-1"></i>
											Back
										</button>
									</div>
									<div className="form-group controls" style={{ color: "#6c757d", opacity: 1 }}>
										<div className="form-row" width="130em">
											<div className="form-col">
												<img className="img-fluid" src={CapsuleLogo} width="90em" />
											</div>
											<div className="form-col" style={{ marginLeft: "2%" }}>
												<div>
													<b>Name:</b> {this.state.drugName}
												</div>
												<div>
													<b>Manufacturer:</b> {this.state.drugManufacturer}
												</div>
												<div>
													<b>Quantity:</b> {this.state.drugQuantity}
													<b>mg</b>
												</div>
												<div>
													<b>Price by peace:</b> {(Math.round(this.state.drugPrice * 100) / 100).toFixed(2)}
												</div>
											</div>
										</div>
									</div>
									<div className="form-row mt-3">
										<div className="form-col" style={{ color: "#6c757d", opacity: 1 }}>
											<label>Date of drug pickup:</label>
										</div>
									</div>
									<div className="form-row">
										<div className="form-col" style={{ color: "#6c757d", opacity: 1 }}>
											<DatePicker
												className="form-control mr-3"
												minDate={new Date()}
												onChange={(date) => this.handleDateChange(date)}
												selected={this.state.selectedDate}
											/>
										</div>
									</div>
									<div className="form-row">
										<div className="form-col">
											<label>Drug amount:</label>
											<input
												placeholder="Drug amount"
												className="form-control mr-3"
												style={{ width: "9em" }}
												type="number"
												min="1"
												max={this.props.maxDrugAmount}
												onChange={this.handleGradeFromChange}
												value={this.state.drugAmount}
											/>
										</div>
									</div>
									<div className="form-row mt-3">
										<div className="form-col">
											<h5>
												Total price:{" "}
												<b color="red">
													{(Math.round(this.state.drugAmount * this.state.drugPrice * 100) / 100).toFixed(2)} din
												</b>
											</h5>
										</div>
									</div>
									<div className="form-group text-center">
										<Button className="mt-3" onClick={() => this.handleReservation()}>
											Reserve drugs
										</Button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default DrugsInPharmacyModal;
