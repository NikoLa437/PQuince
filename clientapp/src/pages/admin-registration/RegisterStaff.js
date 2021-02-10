import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import getAuthHeader from "../../GetHeader";
import HeadingAlert from "../../components/HeadingAlert";
import Axios from "axios";
import { withRouter } from "react-router";
import ModalDialog from "../../components/ModalDialog";
import { YMaps, Map } from "react-yandex-maps";

const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};

class RegisterStaff extends Component {
	state = {
		errorHeader: "",
		errorMessage: "",
		hiddenErrorAlert: true,
		email: "",
		password: "",
		name: "",
		surname: "",
		address: "",
		openModalData: false,
		phoneNumber: "",
		emailError: "none",
		passwordError: "none",
		nameError: "none",
		surnameError: "none",
		addressError: "none",
		phoneError: "none",
		emailNotValid: "none",
		openModal: false,
		pharmacies:[],
		selectedPharmacy:null,
		pharmacy:"",
		coords: [],
		selectValue:"dermathologist",
	};

	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
	}

	
	handleModalDataClose = () => {
		this.setState({ 
			openModalData: false,
		});
	};

	handleModalEmailClose = () => {
		this.setState({ 
			openModalEmail: false,
		});
	};
	
	componentDidMount() {

		Axios.get(BASE_URL + "/api/pharmacy")
			.then((res) => {
				this.setState({ pharmacies: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}
	onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;
		new this.ymaps.SuggestView(this.addressInput.current, {
			provider: {
				suggest: (request, options) => this.ymaps.suggest(request),
			},
		});
	};

	handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
	handlePharmacyChange = (event) => {
		this.setState({ pharmacy: event.target.value });
	};
	
	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	handleSurnameChange = (event) => {
		this.setState({ surname: event.target.value });
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	handlePhoneNumberChange = (event) => {
		this.setState({ phoneNumber: event.target.value });
	};

	validateForm = (userDTO) => {
		this.setState({
			emailError: "none",
			emailNotValid: "none",
			nameError: "none",
			surnameError: "none",
			addressError: "none",
			phoneError: "none",
			passwordError: "none",
		});

		if (userDTO.email === "") {
			this.setState({ emailError: "initial" });
			return false;
		} else if (!userDTO.email.includes("@")) {
			this.setState({ emailNotValid: "initial" });
			return false;
		} else if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.surname === "") {
			this.setState({ surnameError: "initial" });
			return false;
		} else if (this.addressInput.current.value === "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (userDTO.phoneNumber === "") {
			this.setState({ phoneError: "initial" });
			return false;
		} else if (userDTO.password === "") {
			this.setState({ passwordError: "initial" });
			return false;
		}
		return true;
	};

	handleModalClose = () => {
		this.setState({ openModal: false });
	};
	
	onPharmacyChange  = (pharmacy) => {
		this.state.selectedPharmacy = pharmacy;
		console.log(pharmacy, "PHARMACy");
	
	};
	
	handleSignUp = () => {

		if(this.state.surname !== "" &&
		this.state.name !== "" &&
		this.state.phoneNumber !== "" &&
		this.state.password !== "" &&
		this.state.email !== ""){

		let street;
		let city;
		let country;
		let latitude;
		let longitude;
		let found = true;

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
					email: this.state.email,
					name: this.state.name,
					surname: this.state.surname,
					address: { street, country, city, latitude, longitude },
					phoneNumber: this.state.phoneNumber,
					password: this.state.password,
				};
				console.log(userDTO, "AAAA")
				if (this.validateForm(userDTO)) {
					if (found === false) {
						this.setState({ addressNotFoundError: "initial" });
					} else {
					if(this.state.selectValue == "dermathologist"){
							
						Axios.post(BASE_URL + "/auth/signup-dermathologist", userDTO,{ headers: { Authorization: getAuthHeader()}})
							.then((res) => {
								if (res.status === 409) {
									this.setState({
										errorHeader: "Resource conflict!",
										errorMessage: "Email already exist.",
										hiddenErrorAlert: false,
									});
								} else if (res.status === 500) {
									console.log("USO")
									this.setState({ openModalData: true });
								} else {
									console.log("Success");
									this.setState({ openModal: true });
								}
							})
							.catch((err) => {
								console.log(err);
							});
					}
					if(this.state.selectValue == "pharmacyadmin"){
						console.log(this.state.selectedPharmacy.Id);
						Axios.post(BASE_URL + "/auth/signup-pharmacyadmin/" + this.state.selectedPharmacy.Id, userDTO, { headers: { Authorization: getAuthHeader()}})
							.then((res) => {
								console.log("Success");
								this.setState({ openModal: true });
							})
							.catch((err) => {
								console.log(err);
							});
						
					}
					if(this.state.selectValue == "sysadmin"){
							
						Axios.post(BASE_URL + "/auth/signup-sysadmin", userDTO, { headers: { Authorization: getAuthHeader()}})
							.then((res) => {
								console.log("Success");
								this.setState({ openModal: true });
							})
							.catch((err) => {
								console.log(err);
							});
					}
					if(this.state.selectValue == "supplier"){
							
						Axios.post(BASE_URL + "/auth/signup-supplier", userDTO, { headers: { Authorization: getAuthHeader()}})
							.then((res) => {
								console.log("Success");
								this.setState({ openModal: true });
							})
							.catch((err) => {
								console.log(err);
							});
					}
				}
				}
			});
		}else{
			this.setState({
				openModalData: true,
			})
		}
		
	};
	handleCloseAlert = () => {
		this.setState({ hiddenErrorAlert: true });
	};
	handleSelectChange  = (event) => {
		this.setState({ selectValue: event.target.value });
	};
	
	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "8%" }}>
					<HeadingAlert
						hidden={this.state.hiddenErrorAlert}
						header={this.state.errorHeader}
						message={this.state.errorMessage}
						handleCloseAlert={this.handleCloseAlert}
					/>
					
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Register staff
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" novalidate="novalidate">
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Staff type:</label><br></br>
										<select
									        onChange={this.handleSelectChange} 
											value={this.state.selectValue} 
									     >
										  <option value="dermathologist">Dermathologist</option>
										  <option value="pharmacyadmin">Pharmacy admin</option>
										  <option value="supplier">Supplier</option>
										  <option value="sysadmin">System admin</option>
										</select>	
									</div>
								</div>
								<div className="control-group" hidden={this.state.selectValue !== "pharmacyadmin"}>
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Pharmacy:</label><br></br>
										<select
									       onChange={this.handlePharmacyChange}
											value={this.state.pharmacy}
									     >{this.state.pharmacies.map((pharmacy) => (
										  <option onClick={this.onPharmacyChange(pharmacy)}  id={pharmacy.Id} key={pharmacy.Id} value={pharmacy.EntityDTO.name}>{pharmacy.EntityDTO.name},{pharmacy.EntityDTO.address.street}, {pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.address.country}</option>
										))}	
										</select>	
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Email address:</label>
										<input
											placeholder="Email address"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleEmailChange}
											value={this.state.email}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.emailError }}>
										Email address must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.emailNotValid }}>
										Email address is not valid.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Name:</label>
										<input
											placeholder="Name"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleNameChange}
											value={this.state.name}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Surname:</label>
										<input
											placeholder="Surname"
											class="form-control"
											type="text"
											id="surname"
											onChange={this.handleSurnameChange}
											value={this.state.surname}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.surnameError }}>
										Surname must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Address:</label>
										<input className="form-control" id="suggest" ref={this.addressInput} placeholder="Address" />
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
										<label>Phone number:</label>
										<input
											placeholder="Phone number"
											class="form-control"
											id="phone"
											type="text"
											onChange={this.handlePhoneNumberChange}
											value={this.state.phoneNumber}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.phoneError }}>
										Phone number must be entered.
									</div>
								</div>
								<div className="control-group">
									<label>Password:</label>
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<input
											placeholder="Password"
											class="form-control"
											type="password"
											onChange={this.handlePasswordChange}
											value={this.state.password}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.passwordError }}>
										Password must be entered.
									</div>
								</div>

								<div className="form-group">
									<button
										style={{
											background: "#1977cc",
											marginTop: "15px",
											marginLeft: "40%",
											width: "20%",
										}}
										onClick={this.handleSignUp}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Sign Up
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<ModalDialog
					show={this.state.openModal}
					href="/"
					onCloseModal={this.handleModalClose}
					header="Successful registration"
					text="You have successfully registered staff."
				/>
				<ModalDialog
					show={this.state.openModalData}
					onCloseModal={this.handleModalDataClose}
					header="Error"
					text="You must fill all the info."
				/>
				<ModalDialog
					show={this.state.openModalEmail}
					onCloseModal={this.handleModalEmailClose}
					header="Error"
					text="Email already exist."
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(RegisterStaff);
