import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import receiptLogo from "../static/receiptLogo.png";

class EReceiptsForDrugModal extends Component {
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
					<table className="table" style={{ width: "100%" }}>
						<tbody>
							{this.props.ereceipts.map((ereceipt) => (
								<tr id={ereceipt.Id} key={ereceipt.Id}>
									<td width="130em">
										<img className="img-fluid" src={receiptLogo} width="100em" />
									</td>
									<td>
										<div>
											<b>Status:</b> <span style={{ color: "#1977cc" }}>{ereceipt.EntityDTO.status}</span>
										</div>
										<div>
											<b>Date of creation:</b>{" "}
											{new Date(ereceipt.EntityDTO.creationDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>Pharmacy name:</b> {ereceipt.EntityDTO.pharmacyName}
										</div>
										<div>
											<b>Amount:</b> {ereceipt.EntityDTO.drugAmount}
										</div>
										<div>
											<b>Price: </b> {(Math.round(ereceipt.EntityDTO.price * 100) / 100).toFixed(2)} <b>din</b>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default EReceiptsForDrugModal;
