import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import CapsuleLogo from "../static/capsuleLogo.png";

class OrderDrugsModal extends Component {

	state = {
		replacingDrugs: this.props.replacingDrugs
	};
	
	
	componentDidMount() {
		
		console.log(this.state.replacingDrugs)
	}
	render() {
		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-80w-300h"
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
								<div className="form-row" width="130em">
									<div className="form-col">
										<img className="img-fluid" src={CapsuleLogo} width="90em" />
									</div>
									<div className="form-col">
										<div>
											<b>Name: </b> {this.props.pharmacyName}
										</div>
										<div>
											<b>Address: </b> {this.props.address}
										</div>
										
									</div>
								</div>
							</div>
							<br />
							<div className="form-row">
								<div className="form-col">
									<div>
											<b>Order drugs: {" "}</b>
										{this.props.replacingDrugs.map((drug) => (
											<div>
													<b>Drug id: </b><i>{drug.EntityDTO.drugInstanceId} </i>
													<b>Amount: </b> {drug.EntityDTO.amount}
											</div>	
										))}
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

export default OrderDrugsModal;
