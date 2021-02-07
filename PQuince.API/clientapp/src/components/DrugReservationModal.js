import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import CapsuleLogo from "../static/capsuleLogo.png";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import HeadingAlert from "./HeadingAlert";

class DrugReservationModal extends Component {
	state = {
		selectedDate: new Date(),
		drugAmount: 1,
	};

	handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleDrugAmountChange = (event) => {
		if (event.target.value < 1) this.setState({ drugAmount: 1 });
		else this.setState({ drugAmount: event.target.value });
	};

	render() {
		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-80w-100h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">{this.props.header}</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<HeadingAlert
						hidden={this.props.hiddenFailAlert}
						header={this.props.failHeader}
						message={this.props.failMessage}
						handleCloseAlert={this.props.handleCloseAlertFail}
					/>
					<form className="ml-3">
						<div className="control-group">
							<div className="form-group controls" style={{ color: "#6c757d", opacity: 1 }}>
								<div className="form-row" width="130em">
									<div className="form-col">
										<img className="img-fluid" src={CapsuleLogo} width="90em" />
									</div>
									<div className="form-col">
										<div>
											<b>Name:</b> {this.props.drugName}
										</div>
										<div>
											<b>Manufacturer:</b> {this.props.drugManufacturer}
										</div>
										<div>
											<b>Quantity:</b> {this.props.drugQuantity} <b>mg</b>
										</div>
										<div>
											<b>Price by peace:</b> {(Math.round(this.props.drugPrice * 100) / 100).toFixed(2)} <b>mg</b>
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
										onChange={this.handleDrugAmountChange}
										value={this.state.drugAmount}
									/>
								</div>
							</div>
							<div className="form-row mt-3">
								<div className="form-col">
									<h5>
										Total price:{" "}
										<b color="red">{(Math.round(this.state.drugAmount * this.props.drugPrice * 100) / 100).toFixed(2)} din</b>
									</h5>
								</div>
							</div>
							<div className="form-group text-center">
								<Button className="mt-3" onClick={() => this.props.reserveDrugs(this.state.drugAmount, this.state.selectedDate)}>
									Reserve drugs
								</Button>
							</div>
						</div>
					</form>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default DrugReservationModal;
