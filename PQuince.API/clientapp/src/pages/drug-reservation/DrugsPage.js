import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import DrugSpecificationModal from "../../components/DrugSpecification";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";

class DrugsPage extends Component {
	state = {
		drugs: [],
		specificationModalShow: false,
		ingredients: [],
		replacingDrugs: [],
		drugGrades: [],
		newGrades: [],
		drugAmount: "",
		drugQuantity: "",
		drugManufacturer: "",
		drugName: "",
		onReciept: "",
		drugKind: "",
		drugFormat: "",
		sideEffects: "",
		points: "",
		formShowed: false,
		searchName: "",
		searchGradeFrom: "",
		searchGradeTo: "",
		drugKinds: [],
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug/boze")
			.then((res) => {
				this.setState({ drugs: res.data });
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get(BASE_URL + "/api/drug/drugkind")
			.then((res) => {
				this.setState({
					drugKinds: res.data,
				});
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleDrugKindChange = (event) => {
		this.setState({ drugKind: event.target.value });
	};

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handleNameChange = (event) => {
		this.setState({ searchName: event.target.value });
	};

	handleGradeFromChange = (event) => {
		this.setState({ searchGradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		this.setState({ searchGradeTo: event.target.value });
	};

	handleResetSearch = () => {
		Axios.get(BASE_URL + "/api/drug/boze")
			.then((res) => {
				this.setState({
					drugs: res.data,
					formShowed: false,
					showingSearched: false,
					searchName: "",
					searchGradeFrom: "",
					searchGradeTo: "",
					drugKind: "",
				});
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSearchClick = () => {
		{
			let gradeFrom = this.state.searchGradeFrom;
			let gradeTo = this.state.searchGradeTo;
			let name = this.state.searchName;
			let drugKind = this.state.drugKind;

			console.log("HIHIH", drugKind);

			if (gradeFrom === "") gradeFrom = -1;
			if (gradeTo === "") gradeTo = -1;
			if (name === "") name = "";
			if (drugKind === "") drugKind = "";

			Axios.get(BASE_URL + "/api/drug/search-drugs", {
				params: {
					name: name,
					gradeFrom: gradeFrom,
					gradeTo: gradeTo,
					drugKind: drugKind,
				},
			})
				.then((res) => {
					this.setState({
						drugs: res.data,
						formShowed: false,
						showingSearched: true,
					});
					console.log(res.data, "HAHAHAH");
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};

	handleDrugClick = (drug) => {
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
		});
		console.log(drug.EntityDTO.ingredients, "XOXOXO");
	};

	handleModalClose = () => {
		this.setState({ specificationModalShow: false });
	};
	render() {
		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
			<div hidden={this.props.hidden}>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Drugs</h5>
					<button className="btn btn-outline-primary btn-xl" type="button" onClick={this.hangleFormToogle}>
						<i className="icofont-rounded-down mr-1"></i>
						Search drugs
					</button>
					<form className={this.state.formShowed ? "form-inline mt-3" : "form-inline mt-3 collapse"} width="100%" id="formCollapse">
						<div className="form-group mb-2" width="100%">
							<input
								placeholder="Name"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="text"
								onChange={this.handleNameChange}
								value={this.state.searchName}
							/>
							<input
								placeholder="Grade from"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="number"
								min="0"
								max="5"
								onChange={this.handleGradeFromChange}
								value={this.state.searchGradeFrom}
							/>
							<input
								placeholder="Grade to"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="number"
								min="0"
								max="5"
								onChange={this.handleGradeToChange}
								value={this.state.searchGradeTo}
							/>
							<select
								placeholder="Drug kind"
								onChange={this.handleDrugKindChange}
								style={{ width: "9em" }}
								className="form-control mr-3"
							>
								<option value="" selected disabled>
									Drug Kind
								</option>
								{this.state.drugKinds.map((kind) => (
									<option value={kind.EntityDTO.type}>{kind.EntityDTO.type}</option>
								))}
							</select>
							<button
								style={{ background: "#1977cc" }}
								onClick={this.handleSearchClick}
								className="btn btn-primary btn-x2"
								type="button"
							>
								<i className="icofont-search mr-1"></i>
								Search
							</button>
						</div>
					</form>

					<div className={this.state.showingSearched ? "form-group mt-2" : "form-group mt-2 collapse"}>
						<button type="button" className="btn btn-outline-secondary" onClick={this.handleResetSearch}>
							<i className="icofont-close-line mr-1"></i>Reset criteria
						</button>
					</div>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id} style={{ cursor: "pointer" }}>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="70em" />
									</td>
									<td onClick={() => this.props.onDrugSelect(drug)}>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Kind:</b> {drug.EntityDTO.drugKind}
										</div>
										<div>
											<b>Grade:</b> {drug.EntityDTO.avgGrade}
										</div>
									</td>
									<td>
										<button type="button" onClick={() => this.handleDrugClick(drug)} className="btn btn-outline-secondary">
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
