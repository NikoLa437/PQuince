import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import pharmacistLogo from "../../static/pharmacistLogo.png";
import getAuthHeader from "../../GetHeader";
import "react-confirm-alert/src/react-confirm-alert.css"; // Import css
import HeadingAlert from "../../components/HeadingAlert";
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import FeedbackCreateModal from "../../components/FeedbackCreateModal";

class PharmacistPageForPatient extends Component {
	state = {
		pharmacists: [],
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
		selectedStaffId: "",
		StaffName: "",
		StaffSurame: "",
		grade: 1,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/users/pharmacists", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ pharmacists: res.data });
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
				"/api/users/search-pharmacists-for-pharmacy?name=" +
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
						pharmacists: res.data,
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
		Axios.get(BASE_URL + "/api/users/pharmacists", {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({
					pharmacists: res.data,
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

	handleFeedbackClick = (staff) => {
		console.log(staff);
		Axios.get(BASE_URL + "/api/staff/feedback/" + staff.Id, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						selectedStaffId: staff.Id,
						showFeedbackModal: true,
						StaffName: staff.EntityDTO.name,
						StaffSurame: staff.EntityDTO.surname,
						grade: 1,
					});
				} else if (res.status === 200) {
					this.setState({
						selectedStaffId: staff.Id,
						showModifyFeedbackModal: true,
						StaffName: staff.EntityDTO.name,
						StaffSurame: staff.EntityDTO.surname,
						grade: res.data.grade,
					});
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
		let entityDTO = {
			staffId: this.state.selectedStaffId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.post(BASE_URL + "/api/staff/feedback", entityDTO, {
			validateStatus: () => true,
			headers: { Authorization: getAuthHeader() },
		})
			.then((resp) => {
				if (resp.status === 405) {
					this.setState({ hiddenFailAlert: false, failHeader: "Not allowed", failMessage: "Staff feedback not allowed." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully saved." });
					Axios.get(BASE_URL + "/api/users/pharmacists", {
						headers: { Authorization: getAuthHeader() },
					})
						.then((res) => {
							this.setState({ pharmacists: res.data });
							console.log(res.data);
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
		let entityDTO = {
			staffId: this.state.selectedStaffId,
			date: new Date(),
			grade: this.state.grade,
		};
		Axios.put(BASE_URL + "/api/staff/feedback", entityDTO, {
			validateStatus: () => true,
			headers: { Authorization: getAuthHeader() },
		})
			.then((resp) => {
				if (resp.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Bad request when modifying feedback." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 204) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully modified." });
					Axios.get(BASE_URL + "/api/users/pharmacists", {
						headers: { Authorization: getAuthHeader() },
					})
						.then((res) => {
							this.setState({ pharmacists: res.data });
							console.log(res.data);
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

	handleClickIcon = (grade) => {
		this.setState({ grade });
	};

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
	};

	render() {
		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
			<React.Fragment>
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
					<button className="btn btn-outline-primary btn-xl" type="button" onClick={this.hangleFormToogle}>
						<i className="icofont-rounded-down mr-1"></i>
						Search pharmacists
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
							{this.state.pharmacists.map((pharmacist) => (
								<tr id={pharmacist.Id} key={pharmacist.Id}>
									<td width="130em">
										<img className="img-fluid" src={pharmacistLogo} width="70em" />
									</td>

									<td>
										<div>
											<b>Name: </b> {pharmacist.EntityDTO.name}
										</div>
										<div>
											<b>Surname: </b> {pharmacist.EntityDTO.surname}
										</div>
										<div>
											<b>Pharmacy: </b> {pharmacist.EntityDTO.pharmacyName}
										</div>
										<div>
											<b>Grade: </b> {pharmacist.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
									</td>
									<td className="align-middle">
										<div style={{ marginLeft: "55%" }}>
											<button
												type="button"
												onClick={() => this.handleFeedbackClick(pharmacist)}
												className="btn btn-outline-secondary mt-2"
											>
												Give feedback
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
				<FeedbackCreateModal
					buttonName="Give feedback"
					grade={this.state.grade}
					header="Give feedback"
					show={this.state.showFeedbackModal}
					onCloseModal={this.handleFeedbackModalClose}
					giveFeedback={this.handleFeedback}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="pharmacist"
					handleClickIcon={this.handleClickIcon}
				/>
				<FeedbackCreateModal
					buttonName="Modify feedback"
					grade={this.state.grade}
					header="Modify feedback"
					show={this.state.showModifyFeedbackModal}
					onCloseModal={this.handleModifyFeedbackModalClose}
					giveFeedback={this.handleModifyFeedback}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="pharmacist"
					handleClickIcon={this.handleClickIcon}
				/>
			</React.Fragment>
		);
	}
}

export default PharmacistPageForPatient;
