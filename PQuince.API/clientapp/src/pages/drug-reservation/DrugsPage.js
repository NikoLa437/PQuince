import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import DrugSpecificationModal from "../../components/DrugSpecification";
import Axios from "axios";

class DrugsPage extends Component {
	state = {
		drugs: [],
		specificationModalShow: false,
		ingredients: [],
		replacingDrugs: [],
		drugAmount: "",
		drugQuantity: "",
		drugManufacturer: "",
		drugName: "",
		onReciept: "",
		drugKind: "",
		drugFormat: "",
		sideEffects: "",
		points: ""
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug")
			.then((res) => {
				this.setState({ drugs: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}
	handleDrugClick  = (drug) => {
		this.setState({ 
			drugAmount: drug.EntityDTO.recommendedAmount,
			drugQuantity: drug.EntityDTO.quantity,
			drugManufacturer: drug.EntityDTO.manufacturer.EntityDTO.name,
			drugName: drug.EntityDTO.name,
			drugKind: drug.EntityDTO.drugKind,
			drugFormat: drug.EntityDTO.drugFormat,
			sideEffects: drug.EntityDTO.sideEffects,
			onReciept: drug.EntityDTO.onReciept,
			points: drug.EntityDTO.loyalityPoints,
			ingredients: drug.EntityDTO.ingredients,
			replacingDrugs: drug.EntityDTO.replacingDrugs,
			specificationModalShow: true,
		})
		console.log( drug.EntityDTO.ingredients, "XOXOXO")
	}
	handleModalClose = () => {
		this.setState({ specificationModalShow: false });
	};
	render() {
		return (
			<div hidden={this.props.hidden}>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Drugs</h5>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id}  style={{ cursor: "pointer" }}>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="70em" />
									</td>
									<td onClick={() => this.props.onDrugSelect(drug)}>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Manufacturer:</b> {drug.EntityDTO.manufacturer.EntityDTO.name}
										</div>
										<div>
											<b>Quantity:</b> {drug.EntityDTO.quantity} <b>mg</b>
										</div>
									</td>
									<td>
										<button
											type="button"
											onClick={() => this.handleDrugClick(drug)}
											className="btn btn-outline-secondary"
										>
											Specification
										</button>
									</td>
								</tr>
							))}							
						</tbody>
					</table>
				</div>
				<React.Fragment>
				
				<DrugSpecificationModal
					onCloseModal={this.handleModalClose}
					drugAmount={this.state.drugAmount}
					header="Drug specification"
					show={this.state.specificationModalShow}
					drugQuantity={this.state.drugQuantity}
					drugKind={this.state.drugKind}
					drugFormat={this.state.drugFormat}
					drugManufacturer={this.state.drugManufacturer}
					drugName={this.state.drugName}
					onReciept={this.state.onReciept}
					sideEffects={this.state.sideEffects}
					points={this.state.points}
					ingredients={this.state.ingredients}
					replacingDrugs={this.state.replacingDrugs}
				/>
			</React.Fragment>
			</div>
		);
	}
}

export default DrugsPage;
