import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import dermatologistLogo from "../../static/dermatologistLogo.png";
import PharmaciesForDermatologistModal from "../../components/PharmaciesForDermatologistModal";
import getAuthHeader from "../../GetHeader";
import "react-confirm-alert/src/react-confirm-alert.css"; // Import css

class DermatologistsPageForPatient extends Component {
	state = {
		dermatologists: [],
		forPharmacy: "cafeddee-56cb-11eb-ae93-0242ac130202",
		forStaff: "",
		formShowed: false,
		searchName: "",
		searchSurname: "",
		searchGradeFrom: "",
		searchGradeTo: "",
		searchPharmacyId: "",
		showingSearched: false,
		pharmaciesForDermatologist: [],
		showPharmaciesModal: false,
		showingSorted: false,
		pharmacies: [],
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/users/dermatologists", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ dermatologists: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get(BASE_URL + "/api/pharmacy")
			.then((res) => {
				this.setState({ pharmacies: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handlePharmaciesModalClose = () => {
		this.setState({ showPharmaciesModal: false });
	};

	showPharmacies = (id) => {
		Axios.get(BASE_URL + "/api/users/pharmacies-where-dermatologist-work", {
			params: {
				dermatologistId: id,
			},
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ pharmaciesForDermatologist: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
		this.setState({
			showPharmaciesModal: true,
		});
	};

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handleNameChange = (event) => {
		this.setState({ searchName: event.target.value });
	};

	handleSurnameChange = (event) => {
		this.setState({ searchSurname: event.target.value });
	};

	handlePharmacyChange = (event) => {
		this.setState({ searchPharmacyId: event.target.value });
	};

	handleGradeFromChange = (event) => {
		this.setState({ searchGradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		this.setState({ searchGradeTo: event.target.value });
	};

	handleSearchClick = () => {
		if (
			!(
				this.state.searchGradeFrom === "" &&
				this.state.searchGradeTo === "" &&
				this.state.searchName === "" &&
				this.state.searchSurname === "" &&
				this.state.searchPharmacyId === ""
			)
		) {
			let gradeFrom = this.state.searchGradeFrom;
			let gradeTo = this.state.searchGradeTo;
			let name = this.state.searchName;
			let surname = this.state.searchSurname;
			let pharmacyId = this.state.searchPharmacyId;

			if (gradeFrom === "") gradeFrom = -1;
			if (gradeTo === "") gradeTo = -1;
			if (name === "") name = "";
			if (surname === "") surname = "";
			if (pharmacyId === "") pharmacyId = "00000000-0000-0000-0000-000000000000";

			const SEARCH_URL =
				BASE_URL +
				"/api/users/search-dermatologist?name=" +
				name +
				"&surname=" +
				surname +
				"&gradeFrom=" +
				gradeFrom +
				"&gradeTo=" +
				gradeTo +
				"&pharmacyId=" +
				pharmacyId;

			Axios.get(SEARCH_URL, {
				headers: { Authorization: getAuthHeader() },
			})
				.then((res) => {
					this.setState({
						dermatologists: res.data,
						formShowed: false,
						showingSearched: true,
					});
					console.log(res.data);
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};

	handleResetSearch = () => {
		Axios.get(BASE_URL + "/api/users/dermatologists", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({
					dermatologists: res.data,
					formShowed: false,
					showingSearched: false,
					searchName: "",
					searchSurname: "",
					searchGradeFrom: "",
					searchGradeTo: "",
				});
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	render() {
		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
			<React.Fragment>
				<div></div>

				<TopBar />
				<Header />
				<div className="container" style={{ marginTop: "10%" }}>
					<button className="btn btn-outline-primary btn-xl" type="button" onClick={this.hangleFormToogle}>
						<i className="icofont-rounded-down mr-1"></i>
						Search dermatologists
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
								placeholder="LastName"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="text"
								onChange={this.handleSurnameChange}
								value={this.state.searchSurname}
							/>

							<input
								placeholder="Grade from"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="number"
								min="1"
								max="5"
								onChange={this.handleGradeFromChange}
								value={this.state.searchGradeFrom}
							/>
							<input
								placeholder="Grade to"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="number"
								min="1"
								max="5"
								onChange={this.handleGradeToChange}
								value={this.state.searchGradeTo}
							/>
							<select
								placeholder="Pharmacy"
								onChange={this.handlePharmacyChange}
								value={this.state.searchPharmacyId}
								style={{ width: "9em" }}
								className="form-control mr-3"
							>
								<option key="1" value="">
									{" "}
								</option>
								{this.state.pharmacies.map((pharmacy) => (
									<option key={pharmacy.Id} value={pharmacy.Id}>
										{pharmacy.EntityDTO.name}{" "}
									</option>
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

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.dermatologists.map((dermatologist) => (
								<tr id={dermatologist.Id} key={dermatologist.Id}>
									<td width="130em">
										<img className="img-fluid" src={dermatologistLogo} width="70em" />
									</td>

									<td>
										<div>
											<b>Name: </b> {dermatologist.EntityDTO.name}
										</div>
										<div>
											<b>Surname: </b> {dermatologist.EntityDTO.surname}
										</div>
										<div>
											<b>Email: </b> {dermatologist.EntityDTO.email}
										</div>
										<div>
											<b>Phone number: </b> {dermatologist.EntityDTO.phoneNumber}
										</div>
										<div>
											<b>Grade: </b> {dermatologist.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
									</td>
									<td>
										<div style={{ marginLeft: "55%", height: "100%" }}>
											<button
												style={({ height: "30px" }, { verticalAlign: "center" })}
												className="btn btn-outline-secondary mt-1"
												onClick={() => this.showPharmacies(dermatologist.Id)}
												type="button"
											>
												<i className="icofont-subscribe mr-1"></i>Pharmacies
											</button>
										</div>
									</td>
								</tr>
							))}
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div>
					<PharmaciesForDermatologistModal
						show={this.state.showPharmaciesModal}
						onCloseModal={this.handlePharmaciesModalClose}
						pharmacies={this.state.pharmaciesForDermatologist}
						header="Work in pharmacies"
					/>
				</div>
			</React.Fragment>
		);
	}
}

export default DermatologistsPageForPatient;
