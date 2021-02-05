import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import CapsuleLogo from "../static/capsuleLogo.png";

class EReceiptDrugsModal extends Component {
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
					<h4>{this.props.subheader}</h4>

					<table className="table" style={{ width: "100%" }}>
						<tbody>
							{this.props.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id}>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="90em" />
									</td>
									<td>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Kind:</b> {drug.EntityDTO.drugKind}
										</div>
										<div>
											<b>Format:</b> {drug.EntityDTO.drugFormat}
										</div>
										<div>
											<b>Amount:</b> {drug.EntityDTO.drugAmount}
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

export default EReceiptDrugsModal;
