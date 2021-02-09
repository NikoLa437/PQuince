import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";

class ComplaintReplyModal extends Component {
	render() {
		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-80w-150h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<h5>{"Give reply on complaint for " + this.props.name + " " + this.props.forWho}</h5>

					<input
						className="form-control"
						id="complaints"
						type="text"
						onChange={this.props.handleComplaintChange}
						
					/>

					<Button className="mt-3" onClick={this.props.giveFeedback}>
						{this.props.buttonName}
					</Button>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default ComplaintReplyModal;