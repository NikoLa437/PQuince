import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import DrugSpecificationModal from "../../components/DrugSpecification";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import FeedbackCreateModal from "../../components/FeedbackCreateModal";
import HeadingAlert from "../../components/HeadingAlert";
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import { Redirect } from "react-router-dom";

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
		onReciept: false,
		drugKind: "",
		drugFormat: "",
		sideEffects: "",
		points: "",
		formShowed: false,
		searchName: "",
		searchGradeFrom: "",
		searchGradeTo: "",
		drugKinds: [],
		showFeedbackModal: false,
		showModifyFeedbackModal: false,
		selectedDrugId: "",
		drugNameModal: "",
		grade: 1,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		loggedPatient: false,
		unauthorizedRedirect: false,
	};

	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

	componentDidMount() {
		if (localStorage.getItem("keyToken") !== null && this.hasRole("ROLE_PATIENT")) this.setState({ loggedPatient: true });

		Axios.get(BASE_URL + "/api/drug/grade")

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
		Axios.get(BASE_URL + "/api/drug/grade")

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
	};

	handleSearchClick = () => {
		{
			let gradeFrom = this.state.searchGradeFrom;
			let gradeTo = this.state.searchGradeTo;
			let name = this.state.searchName;
			let drugKind = this.state.drugKind;

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
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};

	handleDrugClick = (drug) => {
		console.log(drug);
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
	};

	handleModalClose = () => {
		this.setState({ specificationModalShow: false });
	};

	handleFeedbackClick = (drug) => {
		console.log(drug);
		Axios.get(BASE_URL + "/api/drug/feedback/" + drug.Id, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						selectedDrugId: drug.Id,
						showFeedbackModal: true,
						drugNameModal: drug.EntityDTO.drugInstanceName,
						grade: 1,
					});
				} else if (res.status === 200) {
					this.setState({
						selectedDrugId: drug.Id,
						showModifyFeedbackModal: true,
						drugNameModal: drug.EntityDTO.drugInstanceName,
						grade: res.data.grade,
					});
				} else if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleFeedbackModalClose = () => {
		this.setState({ showFeedbackModal: false });
	};

	handleModifyFeedbackModalClose = () => {
		this.setState({ showModifyFeedbackModal: false });
	};

	handleFeedback = () => {
		this.setState({ hiddenFailAlert: true, failHeader: "", failMessage: "", hiddenSuccessAlert: true, successHeader: "", successMessage: "" });

		let entityDTO = {
			drugId: this.state.selectedDrugId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.post(BASE_URL + "/api/drug/feedback", entityDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((resp) => {
				if (resp.status === 405) {
					this.setState({ hiddenFailAlert: false, failHeader: "Not allowed", failMessage: "Drug feedback not allowed." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully saved." });

					Axios.get(BASE_URL + "/api/drug/grade")

						.then((res) => {
							this.setState({ drugs: res.data });
						})
						.catch((err) => {
							console.log(err);
						});
				}
				this.setState({ showFeedbackModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModifyFeedback = () => {
		this.setState({ hiddenFailAlert: true, failHeader: "", failMessage: "", hiddenSuccessAlert: true, successHeader: "", successMessage: "" });

		let entityDTO = {
			drugId: this.state.selectedDrugId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.put(BASE_URL + "/api/drug/feedback", entityDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((resp) => {
				if (resp.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Bad request when modifying feedback." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 204) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully modified." });

					Axios.get(BASE_URL + "/api/drug/grade")

						.then((res) => {
							this.setState({ drugs: res.data });
						})
						.catch((err) => {
							console.log(err);
						});
				}
				this.setState({ showModifyFeedbackModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
	};

	handleClickIcon = (grade) => {
		this.setState({ grade });
	};

	render() {
		if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
			<div hidden={this.props.hidden}>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<HeadingAlert
						hidden={this.state.hiddenFailAlert}
						header={this.state.failHeader}
						message={this.state.failMessage}
						handleCloseAlert={this.handleCloseAlertFail}
					/>

					<HeadingSuccessAlert
						hidden={this.state.hiddenSuccessAlert}
						header={this.state.successHeader}
						message={this.state.successMessage}
						handleCloseAlert={this.handleCloseAlertSuccess}
					/>
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
					<p hidden={!this.state.loggedPatient} className="mb-4 mt-4 text-uppercase">
						Click on drug to see availability in pharmacies
					</p>

					<table className={this.state.loggedPatient === true ? "table table-hover" : "table"} style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id} style={this.state.loggedPatient === true ? { cursor: "pointer" } : {}}>
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
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
									</td>
									<td className="align-middle">
										<div style={{ marginLeft: "55%" }}>
											<button
												type="button"
												onClick={() => this.handleDrugClick(drug)}
												className="btn btn-outline-secondary btn-block"
											>
												Specification
											</button>
										</div>
										<div style={{ marginLeft: "55%" }} className="mt-2" hidden={!this.state.loggedPatient}>
											<button
												type="button"
												onClick={() => this.handleFeedbackClick(drug)}
												className="btn btn-outline-secondary btn-block"
											>
												Give feedback
											</button>
										</div>
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
				<FeedbackCreateModal
					buttonName="Give feedback"
					grade={this.state.grade}
					header="Give feedback"
					show={this.state.showFeedbackModal}
					onCloseModal={this.handleFeedbackModalClose}
					giveFeedback={this.handleFeedback}
					name={this.state.drugNameModal}
					forWho="drug"
					handleClickIcon={this.handleClickIcon}
				/>
				<FeedbackCreateModal
					buttonName="Modify feedback"
					grade={this.state.grade}
					header="Modify feedback"
					show={this.state.showModifyFeedbackModal}
					onCloseModal={this.handleModifyFeedbackModalClose}
					giveFeedback={this.handleModifyFeedback}
					name={this.state.drugNameModal}
					forWho="drug"
					handleClickIcon={this.handleClickIcon}
				/>
			</div>
		);
	}
}

export default DrugsPage;
