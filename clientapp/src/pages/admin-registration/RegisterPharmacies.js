import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import { withRouter } from "react-router";
import getAuthHeader from "../../GetHeader";
import ModalDialog from "../../components/ModalDialog";
import { YMaps, Map } from "react-yandex-maps";

const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};

class RegisterPharmacies extends Component {
	state = {
		name: "",
		description: "",
		address: "",
		consulationPrice: "",
		openModalData: false,
		nameError: "none",
		addressError: "none",
		consulationPriceError: "none",
		openModal: false,
		coords: [],
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

	onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;
		new this.ymaps.SuggestView(this.addressInput.current, {
			provider: {
				suggest: (request, options) => this.ymaps.suggest(request),
			},
		});
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	handleConsulationPriceChange = (event) => {
		this.setState({ consulationPrice: event.target.value });
	};
	
	handleDescriptionChange = (event) => {
		this.setState({ description: event.target.value });
	};

	validateForm = (userDTO) => {
		this.setState({
			nameError: "none",
			addressError: "none",
			consulationPriceError: "none",
		});

		if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (this.addressInput.current.value === "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (userDTO.consulationPrice === "") {
			this.setState({ consulationPriceError: "initial" });
			return false;
		}
		
		return true;
	};

	handleModalClose = () => {
		this.setState({ openModal: false });
	};

	handleSignUp = () => {

		if(this.state.name !== "" &&
		this.state.description !== "" &&
		this.state.consulationPrice !== ""){

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
				let pharmacyDTO = {
					name: this.state.name,
					description: this.state.description,
					address: { street, country, city, latitude, longitude },
					consultationPrice: this.state.consulationPrice*1,
				};
				console.log(pharmacyDTO);
				if (this.validateForm(pharmacyDTO)) {
				
					Axios.post(BASE_URL + "/api/pharmacy", pharmacyDTO, { headers: { Authorization: getAuthHeader()}})
						.then((res) => {
							console.log("Success");
							this.setState({ openModal: true });
						})
						.catch((err) => {
							console.log(err);
						});
				}
			});
		}else{
			this.setState({
				openModalData: true,
			})
		}
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
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Registrater pharmacy
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" novalidate="novalidate">
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
										<label>Consultation price:</label>
										<input
											placeholder="Consultation price"
											class="form-control"
											id="consulationPrice"
											type="number"
											onChange={this.handleConsulationPriceChange}
											value={this.state.consulationPrice}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.consulationPriceError }}>
										Phone number must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Description:</label>
										<input
											placeholder="Description"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleDescriptionChange}
											value={this.state.description}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
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
			</React.Fragment>
		);
	}
}

export default withRouter(RegisterPharmacies);
