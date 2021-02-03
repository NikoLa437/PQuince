import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import { YMaps, Map, GeoObject, Placemark } from "react-yandex-maps";
import PharmacyDermatologistModal from "../../components/PharmacyDermatologistModal";
import DrugsInPharmacyModal from "../../components/DrugsInPharmacyModal";
import FeedbackCreateModal from "../../components/FeedbackCreateModal";
import ComplaintCreateModal from "../../components/ComplaintCreateModal";
import getAuthHeader from "../../GetHeader";
import { withRouter } from "react-router";
import ReserveDrugsInPharmacy from "../../components/ReserveDrugsInPharmacyModal";

class PharmacyForAdmin extends Component {
	state = {
		pharmacy: "",
		pharmacyId: "",
		pharmacyName: "",
		pharmacyDescription: "",
		pharmacyAdress: "",
		pharmacyCity: "",
        grade: "",
        adress:'',
		patientsGrade: 0,
		x: "",
		y: "",
		showDermatologistModal: false,
		showCreateAppointment: false,
		showDrugsInPharmacy: false,
		showReserveDrugsInPharmacy:false,
		complaint: "",
		dermatologists:[],
		drugsInPharmacy:[],
        isPatient:false,
        hiddenEditInfo: true,
		consultationPrice:'',
		hiddenEditInfo:true

    };
    
	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
	}

    handleChangeInfo = () => {
		this.setState({
			hiddenSuccessAlert: true,
			successHeader: "",
			successMessage: "",
			hiddenFailAlert: true,
			failHeader: "",
			failMessage: "",
		});

		let street;
		let city;
		let country;
		let latitude;
		let longitude;

		this.ymaps
			.geocode(this.addressInput.current.value, {
				results: 1,
			})
			.then(function (res) {
				var firstGeoObject = res.geoObjects.get(0),
					coords = firstGeoObject.geometry.getCoordinates();
				latitude = coords[0];
				longitude = coords[1];
				country = firstGeoObject.getCountry();
				street = firstGeoObject.getThoroughfare();
				city = firstGeoObject.getLocalities().join(", ");
			})
			.then((res) => {
				let userDTO = {
					name: this.state.name,
					surname: this.state.surname,
					address: { street, country, city, latitude, longitude },
					phoneNumber: this.state.phoneNumber,
				};
				console.log(userDTO);

				if (this.validateForm(userDTO)) {
					console.log(userDTO);
					Axios.put(BASE_URL + "/api/users/patient", userDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
						.then((res) => {
							if (res.status === 400) {
								this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: "Illegal argument." });
							} else if (res.status === 500) {
								this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
							} else if (res.status === 204) {
								console.log("Success");
								this.setState({
									hiddenSuccessAlert: false,
									successHeader: "Success",
									successMessage: "You successfully updated your information.",
									hiddenEditInfo: true,
								});
							}
						})
						.catch((err) => {
							console.log(err);
						});
				}
			});
	};

	handleEditInfoClick = () => {
		this.setState({ hiddenEditInfo: false });
	};
	componentDidMount() {
        this.addressInput = React.createRef();

		let pharmacyId=localStorage.getItem("keyPharmacyId")
		this.setState({
			pharmacyId: pharmacyId
		})


		Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId="+localStorage.getItem("keyPharmacyId"))
			.then((response) => {
				this.setState({
					pharmacy: response.data,
					pharmacyId: response.data.Id,
					pharmacyName: response.data.EntityDTO.name,
					pharmacyDescription: response.data.EntityDTO.description,
                    pharmacyAdress: response.data.EntityDTO.address.street,
                    address: response.data.EntityDTO.address,
					pharmacyCity: response.data.EntityDTO.address.city,
					x: response.data.EntityDTO.address.latitude,
					y: response.data.EntityDTO.address.longitude,
                    grade: response.data.EntityDTO.grade,
                    consultationPrice: response.data.EntityDTO.consultationPrice
				});
			})
			.catch((err) => {
				console.log(err);
			});
    }
    
    onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;

		if (this.state.address !== "") {
			console.log(this.state);
			this.ymaps
				.geocode([this.state.address.latitude, this.state.address.longitude], {
					results: 1,
				})
				.then(function (res) {
					var firstGeoObject = res.geoObjects.get(0);
					document.getElementById("suggest").setAttribute("value", firstGeoObject.getAddressLine());
					console.log(firstGeoObject.getAddressLine());
				});

			new this.ymaps.SuggestView(this.addressInput.current, {
				provider: {
					suggest: (request, options) => this.ymaps.suggest(request),
				},
			});
		}
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


				<div className="container" style={{ marginTop: "8%" }}>
					<div className="row" style={{ verticalAlign: "center" }}></div>
					<div className="row" style={{ marginTop: "3%" }}>
                        <div className="col-xs-4" style={{width:'45%'}}>
							<h5 className=" text-center text-uppercase">{pharmacyName}</h5>
							<form id="contactForm" name="sentMessage">
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Name:</label>
										<input
											readOnly={this.state.hiddenEditInfo}
											className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
											placeholder="Name"
											type="text"
											onChange={this.handleNameChange}
											value={this.state.pharmacyName}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
								</div>
                                <div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Address:</label>
										<input
											readOnly={this.state.hiddenEditInfo}
											className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
											id="suggest"
											ref={this.addressInput}
											placeholder="Address"
										/>
									</div>
									<YMaps
										query={{
											load: "package.full",
											apikey: "b0ea2fa3-aba0-4e44-a38e-4e890158ece2",
											lang: "en_RU",
										}}
									>
										<Map
											style={{ display: "none" }}
											state={mapState}
											onLoad={this.onYmapsLoad}
											instanceRef={(map) => (this.map = map)}
											modules={["coordSystem.geo", "geocode", "util.bounds"]}
										></Map>
									</YMaps>
									<div className="text-danger" style={{ display: this.state.addressError }}>
										Address must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Description:</label>
										<input
											readOnly={this.state.hiddenEditInfo}
											className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
											placeholder="Description"
											type="text"
											onChange={this.handleSurnameChange}
											value={this.state.pharmacyDescription}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.surnameError }}>
										Surname must be entered.
									</div>
								</div>
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Consultation Price:</label>
										<input
											placeholder="Consultation price"
											readOnly={this.state.hiddenEditInfo}
											className={!this.state.hiddenEditInfo === false ? "form-control-plaintext" : "form-control"}
											type="text"
											onChange={this.handlePhoneNumberChange}
											value={this.state.consultationPrice}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.phoneError }}>
										Phone number must be entered.
									</div>
								</div>
								<div className="form-group text-center" hidden={this.state.hiddenEditInfo}>
									<button
										style={{ background: "#1977cc", marginTop: "15px" }}
										onClick={this.handleChangeInfo}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Change information
									</button>
								</div>
								<br />

								<div className="form-group">
									<div className="form-group controls mb-0 pb-2">
										<div className="form-row justify-content-center">
											<div className="form-col" hidden={!this.state.hiddenEditInfo}>
												<button
													onClick={this.handleEditInfoClick}
													className="btn btn-outline-primary btn-xl"
													id="sendMessageButton"
													type="button"
												>
													Edit Info
												</button>
											</div>
										</div>
									</div>
								</div>
							</form>
                        </div>
						<div className="col-xs-8" style={{marginLeft:'3%'}}>
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
                    <div>
                        Test
                    </div>
				</div>

			</React.Fragment>
		);
	}
}

export default PharmacyForAdmin;
