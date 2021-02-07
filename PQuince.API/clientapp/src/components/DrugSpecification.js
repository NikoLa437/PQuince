import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import CapsuleLogo from "../static/capsuleLogo.png";

class DrugSpecification extends Component {

	state = {
		ingredients: this.props.ingredients,
		replacingDrugs: this.props.replacingDrugs
	};
	
	
	componentDidMount() {
	
		console.log(this.state.ingredients)
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
											<b>Name: </b> {this.props.drugName}
										</div>
										<div>
											<b>Manufacturer: </b> {this.props.drugManufacturer}
										</div>
										<div>
											<b>Quantity: </b> {this.props.drugQuantity} <b>mg</b>
										</div>
										
									</div>
								</div>
							</div>
							<br />
							<div className="form-row">
								<div className="form-col">
									<div>
										<b>Drug kind: </b> {this.props.drugKind}
									</div>
									<div>
										<b>Drug format: </b> {this.props.drugFormat}
									</div>
									<div>
										<b>Recommended amount: </b> {this.props.drugAmount}
									</div>
									<div>
										<b>Side effects: </b> {this.props.sideEffects}
									</div>
									<div>
										<b>Ingredients: {" "}</b>
										{this.props.ingredients.map((ingredient) => (
											
												<i>{ingredient.EntityDTO.name}, </i>
											
										))}
									</div>
									<div>
											<b>Replacing drugs: {" "}</b>
										{this.props.replacingDrugs.map((drug) => (
											
												<i>{drug.EntityDTO.drugInstanceName}, </i>
											
										))}
									</div>
									<div>
										<b>On reciept: </b> {this.props.onReciept ? 'Yes' : 'No'}
									</div>
									<div>
										<b>Loyalty points: </b> {this.props.points}
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

export default DrugSpecification;
