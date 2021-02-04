import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import AppointmentIcon from "../static/appointment-icon.jpg";

class EventDetailsModal extends Component {
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
					<form className="ml-3">
						<div className="control-group">
							<div className="form-group controls" style={{ color: "#6c757d", opacity: 1 }}>
								<div className="form-row" width="160em">
									<div className="form-col">
										<img className="img-fluid" src={AppointmentIcon} width="130em" />
									</div>
									<div className="form-col ml-3">
										<div>
											<b>Date: </b>{" "}
											{new Date(this.props.startDateTime).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Time from: </b>{" "}
											{new Date(this.props.startDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Time to: </b>{" "}
											{new Date(this.props.endDateTime).toLocaleTimeString("en-US", {
												hour: "2-digit",
												minute: "2-digit",
											})}
										</div>
										<div>
											<b>Price: </b> {(Math.round(this.props.price * 100) / 100).toFixed(2)} <b>din</b>
										</div>
										<div>
											<b>Patient: </b> {this.props.name + " " + this.props.surname}
										</div>
										<div hidden={this.props.name !== "" && this.props.surname !== ""}>
											<h4 className="text-secondary">Appointment is not scheduled by patient</h4>
										</div>
									</div>
								</div>
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

export default EventDetailsModal;
