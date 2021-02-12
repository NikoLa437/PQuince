import React, { Component } from "react";
import TopBar from "../components/TopBar";
import Header from "../components/Header";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { YMaps, Map, GeoObject, Placemark } from "react-yandex-maps";
import PharmacyDermatologistModal from "../components/PharmacyDermatologistModal";
import DrugsInPharmacyModal from "../components/DrugsInPharmacyModal";
import FeedbackCreateModal from "../components/FeedbackCreateModal";
import ComplaintCreateModal from "../components/ComplaintCreateModal";
import getAuthHeader from "../GetHeader";
import { withRouter } from "react-router";
import ReserveDrugsInPharmacy from "../components/ReserveDrugsInPharmacyModal";
import { Redirect } from "react-router-dom";
import Star from "../static/star.png";
import StarOutline from "../static/star-outline.png";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";
import PharmacyPharmacistsModal from "../components/PharmacyPharmacistsModal";
import CheckQrAvailableModal from "../components/CheckQrAvailableModal";


class PharmacyProfilePage extends Component {
	state = {
		pharmacy: "",
		pharmacyId: "",
		pharmacyName: "",
		pharmacyDescription: "",
		pharmacyAdress: "",
		pharmacyCity: "",
		grade: "",
		patientsGrade: 0,
		x: "",
		y: "",
		showDermatologistModal: false,
		showCreateAppointment: false,
		showDrugsInPharmacy: false,
		showReserveDrugsInPharmacy: false,
		complaint: "",
		dermatologists: [],
		drugsInPharmacy: [],
		isPatient: false,
		redirect: false,
		redirectUrl: "",
		isPatientSubscribed: false,
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		pharmacists: [],
		showPharmacistsModal: false,
		drugAvailableModalShow:false
	};

	fetchData = (id) => {
		this.setState({
			pharmacyId: id,
		});
	};

	handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
	};

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	componentDidMount() {
		const id = this.props.match.params.id;
		this.fetchData(id);

		if (localStorage.getItem("keyRole") === '["ROLE_PATIENT"]') {
			this.setState({
				isPatient: true,
			});

			Axios.get(BASE_URL + "/api/users/check-if-patient-subscribed-to-pharmacy", {
				params: {
					pharmacyId: id,
				},
				headers: { Authorization: getAuthHeader() },
			})
				.then((response) => {
					console.log(response.data, "SUB");
					this.setState({
						isPatientSubscribed: response.data,
					});
				})
				.catch((err) => {
					console.log(err);
				});
		}

		Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=" + id)
			.then((response) => {
				this.setState({
					pharmacy: response.data,
					pharmacyId: response.data.Id,
					pharmacyName: response.data.EntityDTO.name,
					pharmacyDescription: response.data.EntityDTO.description,
					pharmacyAdress: response.data.EntityDTO.address.street,
					pharmacyCity: response.data.EntityDTO.address.city,
					x: response.data.EntityDTO.address.latitude,
					y: response.data.EntityDTO.address.longitude,
					grade: response.data.EntityDTO.grade,
				});
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleSubscribeClick = () => {
		let pharmacyIdDTO = {
			id: this.state.pharmacyId,
		};

		Axios.put(BASE_URL + "/api/users/subscribe-to-pharmacy", pharmacyIdDTO, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({
					isPatientSubscribed: true,
					hiddenSuccessAlert: false,
					successHeader: "Success",
					successMessage: "You successfully subscribed to own pharmacy.",
				});
			})
			.catch((err) => {
				this.setState({
					isPatientSubscribed: true,
					hiddenFailAlert: false,
					failHeader: "Unsuccess",
					failMessage: "We have a problem with subscribe.Try later.",
				});
			});
	};

	handleUnSubscribeClick = () => {
		let pharmacyIdDTO = {
			id: this.state.pharmacyId,
		};

		Axios.put(BASE_URL + "/api/users/unsubscribe-from-pharmacy", pharmacyIdDTO, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({
					isPatientSubscribed: false,
				});
				this.setState({
					isPatientSubscribed: false,
					hiddenSuccessAlert: false,
					successHeader: "Success",
					successMessage: "You successfully unsubscribed from own pharmacy.",
				});
			})
			.catch((err) => {
				this.setState({
					isPatientSubscribed: true,
					hiddenFailAlert: false,
					failHeader: "Unsuccess",
					failMessage: "We have a problem with subscribe.Try later.",
				});
			});
	};

	handleComplaintChange = (event) => {
		this.setState({ complaint: event.target.value });
	};

	handleReserveDrugsClick = () => {
		Axios.get(BASE_URL + "/api/drug/find-drug-by-pharmacy?pharmacyId=" + this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ drugsInPharmacy: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

		this.setState({
			showReserveDrugsInPharmacy: true,
		});
	};

	handleAddExaminationClick = () => {
		this.setState({
			redirect: true,
			redirectUrl: "/reserve-appointment/" + this.state.pharmacyId,
		});
	};

	handleConsultationClick = () => {
		this.setState({
			redirect: true,
			redirectUrl: "/pharmacy-consultation/" + this.state.pharmacyId,
		});
	};

	handleOurDrugs = () => {
		Axios.get(BASE_URL + "/api/drug/find-drug-by-pharmacy?pharmacyId=" + this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ drugsInPharmacy: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

		this.setState({
			showDrugsInPharmacy: true,
		});
	};

	handleModalClose = () => {
		this.setState({ showDermatologistModal: false });
	};

	onClosePharmacistsModal = () => {
		this.setState({ showPharmacistsModal: false });
	};
	handleCreateAppoitmentClose = () => {
		this.setState({ showCreateAppointment: false });
	};

	handleComplaintModalClose = () => {
		this.setState({ showComplaintModal: false });
	};

	handleShowDrugsInPharmacyClose = () => {
		this.setState({ showDrugsInPharmacy: false });
	};

	handleReserveDrugsInPharmacyClose = () => {
		this.setState({ showReserveDrugsInPharmacy: false });
	};

	handleFeedbackClick = () => {
		Axios.get(BASE_URL + "/api/pharmacy/feedback/" + this.state.pharmacyId, {
			validateStatus: () => true,
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						showFeedbackModal: true,
						patientsGrade: 0,
					});
				} else if (res.status === 200) {
					this.setState({
						showModifyFeedbackModal: true,
						patientsGrade: res.data.grade,
					});
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleComplaintClick = () => {
		Axios.get(BASE_URL + "/api/pharmacy/feedback/" + this.state.pharmacyId, {
			validateStatus: () => true,
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						showComplaintModal: true,
						patientsGrade: 0,
					});
				} else {
					this.setState({
						showComplaintModal: true,
						patientsGrade: res.data.grade,
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

	handleComplaintModalClose = () => {
		this.setState({ showComplaintModal: false });
	};

	handleModifyFeedbackModalClose = () => {
		this.setState({ showModifyFeedbackModal: false });
	};

	handleFeedback = () => {
		let entityDTO = {
			pharmacyId: this.state.pharmacyId,
			date: new Date(),
			grade: this.state.patientsGrade,
		};
		Axios.post(BASE_URL + "/api/pharmacy/feedback", entityDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((resp) => {
				if (resp.status === 405) {
					this.setState({ hiddenFailAlert: false, failHeader: "Not allowed", failMessage: "Pharmacy feedback not allowed." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully saved." });
					Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=" + this.state.pharmacyId)
						.then((response) => {
							this.setState({
								grade: response.data.EntityDTO.grade,
								showFeedbackModal: false,
							});
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

	handleListOfDermatologistOpenModal = () => {
		Axios.get(BASE_URL + "/api/users/dermatologist-for-pharmacy/" + this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ dermatologists: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

		this.setState({
			showDermatologistModal: true,
		});
	};

	handleListOfDermatologistCloseModal = () => {
		this.setState({
			showDermatologistModal: false,
		});
	};

	handleOurPharmaciesModalOpen = () => {
		Axios.get(BASE_URL + "/api/users/pharmacist-for-pharmacy/" + this.state.pharmacyId)
			.then((res) => {
				this.setState({ pharmacists: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

		this.setState({
			showPharmacistsModal: true,
		});
	};

	handleComaplaint = () => {
		let entityDTO = {
			pharmacyId: this.state.pharmacyId,
			date: new Date(),
			text: this.state.complaint,
		};

		Axios.post(BASE_URL + "/api/pharmacy/complaint-pharmacy", entityDTO, {
			validateStatus: () => true,
			headers: { Authorization: getAuthHeader() },
		})
			.then((resp) => {
				if (resp.status === 405) {
					this.setState({ hiddenFailAlert: false, failHeader: "Not allowed", failMessage: "Pharmacy complaint not allowed." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 201) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Complaint successfully saved." });
					Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=" + this.state.pharmacyId)
						.then((response) => {
							this.setState({
								grade: response.data.EntityDTO.grade,
								showComplaintModal: false,
							});
						})
						.catch((err) => {
							console.log(err);
						});
				}
				this.setState({ showComplaintModal: false });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleCloseCheckQR = () =>{
		this.setState({
			drugAvailableModalShow:false
		})
	}

	handleModifyFeedback = () => {
		let entityDTO = {
			pharmacyId: this.state.pharmacyId,
			date: new Date(),
			grade: this.state.patientsGrade,
		};
		Axios.put(BASE_URL + "/api/pharmacy/feedback", entityDTO, { headers: { Authorization: getAuthHeader() } })
			.then((resp) => {
				if (resp.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Bad request when modifying feedback." });
				} else if (resp.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (resp.status === 204) {
					this.setState({ hiddenSuccessAlert: false, successHeader: "Success", successMessage: "Feedback successfully modified." });
					Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=" + this.state.pharmacyId)
						.then((response) => {
							this.setState({
								grade: response.data.EntityDTO.grade,
								showModifyFeedbackModal: false,
							});
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

	handleCheckDrugAvailable =()=>{
		this.setState({
			drugAvailableModalShow:true
		})
	}

	handleClickIcon = (grade) => {
		this.setState({ patientsGrade: grade });
	};

	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));
		console.log(roles);

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
		const { pharmacy, pharmacyName, pharmacyDescription, pharmacyAdress, pharmacyCity, x, y } = this.state;
		const mapState = { center: [x, y], zoom: 17 };
		const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<nav className="nav-menu d-none d-lg-block" style={{ marginTop: "8%" }}>
					<ul hidden={!this.state.isPatient} style={{ marginLeft: "28%" }}>
						<li>
							<a onClick={this.handleReserveDrugsClick} className="appointment-btn scrollto" style={myStyle}>
								Reserve drug
							</a>
						</li>
						<li>
							<a onClick={this.handleCheckDrugAvailable} href="#" className="appointment-btn scrollto" style={myStyle}>
								Check drug availability
							</a>
						</li>
						<li className="drop-down">
							<a href="#" className="appointment-btn scrollto" style={myStyle}>
								Make an Appointment
							</a>
							<ul>
								<li>
									<a onClick={this.handleAddExaminationClick}>Examination</a>
								</li>
								<li>
									<a onClick={this.handleConsultationClick}>Consultation</a>
								</li>
							</ul>
						</li>
					</ul>
				</nav>

				<div className="container" style={{ marginTop: "0%" }}>
					<HeadingSuccessAlert
						hidden={this.state.hiddenSuccessAlert}
						header={this.state.successHeader}
						message={this.state.successMessage}
						handleCloseAlert={this.handleCloseAlertSuccess}
					/>
					<HeadingAlert
						hidden={this.state.hiddenFailAlert}
						header={this.state.failHeader}
						message={this.state.failMessage}
						handleCloseAlert={this.handleCloseAlertFail}
					/>
					<div className="row" style={{ verticalAlign: "center" }}></div>
					<div className="row" style={{ marginTop: "3%" }}>
						<div className="col-xs-4" style={{ width: "50%" }}>
							<div class="row">
								<div className="ml-3">
									<h1>{pharmacyName}</h1>
								</div>
								<div className="ml-3 mt-2">
									<img hidden={this.state.grade >= 1} className="img-fluid ml-1" src={StarOutline} width="30em" height="30em" />
									<img hidden={this.state.grade < 1} className="img-fluid ml-1" src={Star} width="30em" height="30em" />
									<img hidden={this.state.grade >= 2} className="img-fluid ml-1" src={StarOutline} width="30em" height="30em" />
									<img hidden={this.state.grade < 2} className="img-fluid ml-1" src={Star} width="30em" height="30em" />
									<img hidden={this.state.grade >= 3} className="img-fluid ml-1" src={StarOutline} width="30em" height="30em" />
									<img hidden={this.state.grade < 3} className="img-fluid ml-1" src={Star} width="30em" height="30em" />
									<img hidden={this.state.grade >= 4} className="img-fluid ml-1" src={StarOutline} width="30em" height="30em" />
									<img hidden={this.state.grade < 4} className="img-fluid ml-1" src={Star} width="30em" height="30em" />
									<img hidden={this.state.grade >= 5} className="img-fluid ml-1" src={StarOutline} width="30em" height="30em" />
									<img hidden={this.state.grade < 5} className="img-fluid ml-1" src={Star} width="30em" height="30em" />
								</div>
							</div>
							<button
								hidden={!this.state.isPatient || this.state.isPatientSubscribed}
								type="button"
								onClick={this.handleSubscribeClick}
								className="btn btn-outline-secondary mt-1 mb-3"
							>
								Subscribe
							</button>
							<button
								hidden={!this.state.isPatient || !this.state.isPatientSubscribed}
								type="button"
								onClick={this.handleUnSubscribeClick}
								className="btn btn-outline-secondary mt-1 mb-3"
							>
								Unsubscribe
							</button>
							<br></br>
							<h7>
								Address: {pharmacyAdress}, {pharmacyCity}
							</h7>
							<br></br>
							<h7>Description: {pharmacyDescription}</h7>
							<br></br>
							<button
								style={({ background: "#1977cc" }, { height: "30px" }, { verticalAlign: "center" }, { marginTop: "5%" })}
								onClick={this.handleListOfDermatologistOpenModal}
								className="btn btn-primary btn-xl"
								type="button"
							>
								<i className="icofont-subscribe mr-1"></i>List of dermatologists
							</button>
							<br></br>
							<button
								style={({ background: "#1977cc" }, { height: "30px" }, { verticalAlign: "center" }, { marginTop: "1%" })}
								onClick={this.handleOurPharmaciesModalOpen}
								className="btn btn-primary btn-xl"
								type="button"
							>
								<i className="icofont-subscribe mr-1"></i>List of pharmacists
							</button>
							<br></br>
							<button
								style={({ background: "#1977cc" }, { height: "30px" }, { verticalAlign: "center" }, { marginTop: "1%" })}
								onClick={this.handleOurDrugs}
								className="btn btn-primary btn-xl"
								type="button"
							>
								<i className="icofont-subscribe mr-1"></i>Drugs in stock
							</button>
							<br></br>

							<button
								hidden={!this.state.isPatient}
								type="button"
								hidden={!this.hasRole("ROLE_PATIENT")}
								onClick={this.handleFeedbackClick}
								className="btn btn-outline-secondary mt-3"
							>
								Give feedback
							</button>
							<br></br>
							<button
								hidden={!this.state.isPatient}
								type="button"
								onClick={this.handleComplaintClick}
								className="btn btn-outline-secondary mt-3"
							>
								Make complaint
							</button>
						</div>
						<div className="col-xs-8">
							<YMaps>
								<Map state={mapState} width="35em" height="500px">
									<GeoObject
										geometry={{
											type: "Point",
											coordinates: [x, y],
										}}
										properties={{
											iconContent: pharmacyName,
										}}
										options={{
											preset: "islands#blackStretchyIcon",
											draggable: true,
										}}
									/>
								</Map>
							</YMaps>
						</div>
					</div>

					<PharmacyDermatologistModal
						show={this.state.showDermatologistModal}
						onCloseModal={this.handleListOfDermatologistCloseModal}
						pharmacyId={this.state.pharmacyId}
						dermatologists={this.state.dermatologists}
						header="Our dermatologist"
					/>
					<PharmacyPharmacistsModal
						show={this.state.showPharmacistsModal}
						onCloseModal={this.onClosePharmacistsModal}
						pharmacyId={this.state.pharmacyId}
						pharmacists={this.state.pharmacists}
						header="Our pharmacists"
					/>
					<DrugsInPharmacyModal
						show={this.state.showDrugsInPharmacy}
						onCloseModal={this.handleShowDrugsInPharmacyClose}
						pharmacyId={this.state.pharmacyId}
						drugs={this.state.drugsInPharmacy}
						header="Our drugs in stock"
					/>

					<CheckQrAvailableModal
						show={this.state.drugAvailableModalShow}
						onCloseModal={this.handleCloseCheckQR}
						pharmacyId={this.state.pharmacyId}
						header="Check QR"
					/>				

					<ReserveDrugsInPharmacy
						show={this.state.showReserveDrugsInPharmacy}
						onCloseModal={this.handleReserveDrugsInPharmacyClose}
						pharmacyId={this.state.pharmacyId}
						drugs={this.state.drugsInPharmacy}
						header="Reserve drugs"
					/>
				</div>

				<FeedbackCreateModal
					buttonName="Give feedback"
					grade={this.state.patientsGrade}
					header="Give feedback"
					show={this.state.showFeedbackModal}
					onCloseModal={this.handleFeedbackModalClose}
					giveFeedback={this.handleFeedback}
					name={this.state.pharmacyName}
					forWho="pharmacy"
					handleClickIcon={this.handleClickIcon}
				/>
				<FeedbackCreateModal
					buttonName="Modify feedback"
					grade={this.state.patientsGrade}
					header="Modify feedback"
					show={this.state.showModifyFeedbackModal}
					onCloseModal={this.handleModifyFeedbackModalClose}
					giveFeedback={this.handleModifyFeedback}
					name={this.state.pharmacyName}
					forWho="pharmacy"
					handleClickIcon={this.handleClickIcon}
				/>
				<ComplaintCreateModal
					buttonName="Send complaint"
					header="Make complaint"
					handleComplaintChange={this.handleComplaintChange}
					show={this.state.showComplaintModal}
					onCloseModal={this.handleComplaintModalClose}
					giveFeedback={this.handleComaplaint}
					name={this.state.pharmacyName}
					forWho="pharmacy"
					handleClickIcon={this.handleClickIcon}
					complaint={this.state.complaint}
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(PharmacyProfilePage);
