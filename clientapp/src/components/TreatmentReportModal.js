import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";

class TreatmentReportModal extends Component {
	render() {
		console.log(this.props);
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
							<div className="form-group controls" style={{ fontSize: "1em", color: "#6c757d", opacity: 1 }}>
								<div hidden={!(this.props.anamnesis === "" && this.props.therapy === "" && this.props.diagnosis === "")}>
									<div>
										<b>There is no treatment information. </b>{" "}
									</div>
								</div>
								<div hidden={this.props.anamnesis === ""}>
									<div style={{ fontSize: "1.2em" }}>
										<b>Anamnesis: </b>{" "}
									</div>
									<div>{this.props.anamnesis}</div>
								</div>
								<div hidden={this.props.diagnosis === ""}>
									<div style={{ fontSize: "1.2em" }}>
										<b>Diagnosis: </b>{" "}
									</div>
									<div>{this.props.diagnosis}</div>
								</div>
								<div hidden={this.props.therapy === ""}>
									<div style={{ fontSize: "1.2em" }}>
										<b>Therapy: </b>{" "}
									</div>
									<div>{this.props.therapy}</div>
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

export default TreatmentReportModal;
