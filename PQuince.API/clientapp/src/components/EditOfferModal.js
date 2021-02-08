import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";

class EditOfferModal extends Component {
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
					<h5>Edit offer</h5>
					<tr>
						<td>
							<div>
								<label>Price:</label>
							</div>
							<div>
								<input
									placeholder="Price"
									className="form-control"
									id="complaints"
									type="number"
									value={this.props.price}
									onChange={this.props.handlePriceChange}
								/>
							</div>
							
						</td>
						<td>
							<div>
								<label>Due to date:</label>
							</div>
							<div>
								<input
									placeholder="Price"
									className="form-control"
									id="complaints"
									value={new Date(this.props.date).toLocaleTimeString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
													hour: "2-digit",
													minute: "2-digit",
												})}
									type="number"
									onChange={this.props.handlePriceChange}
								/>
							</div>
							
						</td>
					</tr>
					<tr>
							<div>
								<Button className="mt-3" onClick={this.props.giveOffer}>
									{this.props.buttonName}
								</Button>
							</div>
					</tr>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default EditOfferModal;
