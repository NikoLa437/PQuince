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
		showDrugsInPharmacy: false,
		complaint: "",
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=cafeddee-56cb-11eb-ae93-0242ac130002")
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

	handleSubscribe = () => {
		this.setState({
			showDermatologistModal: true,
		});
	};

	handleComplaintChange = (event) => {
		this.setState({ complaint: event.target.value });
	};
	
	handleOurDrugs = () => {
		this.setState({
			showDrugsInPharmacy: true,
		});
	};

	handleModalClose = () => {
		this.setState({ showDermatologistModal: false });
	};
	
	
	handleComplaintModalClose = () => {
		this.setState({ showComplaintModal: false });
	};

	handleShowDrugsInPharmacyClose = () => {
		this.setState({ showDrugsInPharmacy: false });
	};

	handleFeedbackClick = () => {
		Axios.get(BASE_URL + "/api/pharmacy/feedback/" + this.state.pharmacyId, { validateStatus: () => true })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						showFeedbackModal: true,
						patientsGrade: 0,
					});
				} else {
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
		Axios.get(BASE_URL + "/api/pharmacy/feedback/" + this.state.pharmacyId, { validateStatus: () => true })
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
		Axios.post(BASE_URL + "/api/pharmacy/feedback", entityDTO)
			.then((resp) => {
				Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=cafeddee-56cb-11eb-ae93-0242ac130002")
					.then((response) => {
						this.setState({
							grade: response.data.EntityDTO.grade,
							showFeedbackModal: false,
						});
					})
					.catch((err) => {
						console.log(err);
					});
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleComaplaint = () => {
		let entityDTO = {
			pharmacyId: this.state.pharmacyId,
			date: new Date(),
			text: this.state.complaint,
		};
		Axios.post(BASE_URL + "/api/pharmacy/complaint-pharmacy", entityDTO)
			.then((resp) => {
				Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=cafeddee-56cb-11eb-ae93-0242ac130002")
					.then((response) => {
						this.setState({
							grade: response.data.EntityDTO.grade,
							showComplaintModal: false,
						});
					})
					.catch((err) => {
						console.log(err);
					});
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModifyFeedback = () => {
		let entityDTO = {
			pharmacyId: this.state.pharmacyId,
			date: new Date(),
			grade: this.state.patientsGrade,
		};
		Axios.put(BASE_URL + "/api/pharmacy/feedback", entityDTO)
			.then((resp) => {
				Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=cafeddee-56cb-11eb-ae93-0242ac130002")
					.then((response) => {
						this.setState({
							grade: response.data.EntityDTO.grade,
							showModifyFeedbackModal: false,
						});
					})
					.catch((err) => {
						console.log(err);
					});
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleClickIcon = (grade) => {
		this.setState({ patientsGrade: grade });
	};

	render() {
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
					<ul style={{ marginLeft: "28%" }}>
						<li>
							<a href="#" className="appointment-btn scrollto" style={myStyle}>
								Rezervisi lek
							</a>
						</li>
						<li>
							<a href="#" className="appointment-btn scrollto" style={myStyle}>
								Proveri dostupnost leka
							</a>
						</li>
						<li className="drop-down">
							<a href="#" className="appointment-btn scrollto" style={myStyle}>
								Zakazi pregled
							</a>
							<ul>
								<li>
									<a href="/">Dermatolog</a>
								</li>
								<li>
									<a href="/">Farmaceut</a>
								</li>
							</ul>
						</li>
					</ul>
				</nav>

				<div className="container" style={{ marginTop: "0%" }}>
					<div className="row" style={{ verticalAlign: "center" }}></div>
					<div className="row" style={{ marginTop: "3%" }}>
						<div className="col-xs-4" style={{ width: "50%" }}>
							<div>
								<h1>{pharmacyName}</h1>

								<i className="icofont-star" style={{ color: "#1977cc" }}>
									{this.state.grade}
								</i>
							</div>
							<br></br>
							<h7>
								Adresa apoteke: {pharmacyAdress}, {pharmacyCity}
							</h7>
							<br></br>
							<h7>Opis apoteke: {pharmacyDescription}</h7>
							<br></br>
							<button
								style={({ background: "#1977cc" }, { height: "30px" }, { verticalAlign: "center" }, { marginTop: "5%" })}
								onClick={this.handleSubscribe}
								className="btn btn-primary btn-xl"
								type="button"
							>
								<i className="icofont-subscribe mr-1"></i>Lista dermatologa
							</button>
							<br></br>
							<button
								style={({ background: "#1977cc" }, { height: "30px" }, { verticalAlign: "center" }, { marginTop: "1%" })}
								onClick={this.handleSubscribe}
								className="btn btn-primary btn-xl"
								type="button"
							>
								<i className="icofont-subscribe mr-1"></i>Nasi farmaceuti
							</button>
							<br></br>
							<button
								style={({ background: "#1977cc" }, { height: "30px" }, { verticalAlign: "center" }, { marginTop: "1%" })}
								onClick={this.handleOurDrugs}
								className="btn btn-primary btn-xl"
								type="button"
							>
								<i className="icofont-subscribe mr-1"></i>Lekovi na stanju
							</button>
							<br></br>

							<button type="button" onClick={this.handleFeedbackClick} className="btn btn-outline-secondary mt-3">
								Give feedback
							</button>
							<br></br>
							<button type="button" onClick={this.handleComplaintClick} className="btn btn-outline-secondary mt-3">
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
						onCloseModal={this.handleModalClose}
						pharmacyId={this.state.pharmacyId}
						header="Our dermatologist"
					/>
					<DrugsInPharmacyModal
						show={this.state.showDrugsInPharmacy}
						onCloseModal={this.handleShowDrugsInPharmacyClose}
						pharmacyId={this.state.pharmacyId}
						header="Our drugs in stock"
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

export default PharmacyProfilePage;
