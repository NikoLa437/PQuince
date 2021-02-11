import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import { YMaps, Map, GeoObject, Placemark } from "react-yandex-maps";
import getAuthHeader from "../../GetHeader";
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import HeadingAlert from "../../components/HeadingAlert";
import { Chart } from "react-google-charts";

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
		consultationPrice:0,
		nameError: "none",
		descriptionError: "none",
		addressError: "none",
		consultationPriceError: "none",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		montlyExaminationStatistics:'',
		quartalExaminationStatistics:'',

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

		})

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
					pharmacyId: this.state.pharmacyId,
					name: this.state.pharmacyName,
					description: this.state.pharmacyDescription,
					address: { street, country, city, latitude, longitude },
					consultationPrice: this.state.consultationPrice,
				};


				if (this.validateForm(pharmacyDTO)) {
					Axios.put(BASE_URL + "/api/pharmacy/", pharmacyDTO, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
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

	validateForm = (pharmacyDTO) => {
		alert(pharmacyDTO.name)
		this.setState({
			nameError: "none",
			descriptionError: "none",
			addressError: "none",
			consultationPriceError: "none",
		});

		if (pharmacyDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (pharmacyDTO.description === "") {
			this.setState({ descriptionError: "initial" });
			return false;
		} else if (this.addressInput.current.value === "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (pharmacyDTO.consultationPrice === "") {
			this.setState({ consultationPriceError: "initial" });
			return false;
		}
		return true;
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

		Axios
		.get(BASE_URL + "/api/pharmacy/find-statistics-for-examinations-and-consultations", {
				headers: { Authorization: getAuthHeader() },
			}).then((res) =>{
				this.setState({montlyExaminationStatistics : res.data.montlyStatistics, quartalExaminationStatistics:res.data.quartalStatistics});
				console.log(this.state.montlyExaminationStatistics)
				console.log(res.data);
			}).catch((err) => {console.log(err);});
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

	handleNameChange = (event) => {
		this.setState({ pharmacyName: event.target.value });
	};

	handleDescriptionChange= (event) => {
		this.setState({ pharmacyDescription: event.target.value });
	};

	handleConsultationPriceChange = (event) => {
		this.setState({ consultationPrice: event.target.value });
	};

	
	handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
	};

	
	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
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
				<Chart
						width={'800px'}
						height={'600px'}
						chartType="PieChart"
						loader={<div>Loading Chart</div>}
						data={[
							['Task', 'Hours per Day'],
							['Jan',this.state.montlyExaminationStatistics[0]],
							['Feb', this.state.montlyExaminationStatistics[1]],
							['Mar', this.state.montlyExaminationStatistics[2]],
							['Apr', this.state.montlyExaminationStatistics[3]],
							['May', this.state.montlyExaminationStatistics[4]],
							['Jun', this.state.montlyExaminationStatistics[5]],
							['Jul', this.state.montlyExaminationStatistics[6]],
							['Avg', this.state.montlyExaminationStatistics[7]],
							['Sep', this.state.montlyExaminationStatistics[8]],
							['Okt', this.state.montlyExaminationStatistics[9]],
							['Nov', this.state.montlyExaminationStatistics[10]],
							['Dec', this.state.montlyExaminationStatistics[11]],

						]}
						options={{
							title: 'Montly examinations',
						}}
						rootProps={{ 'data-testid': '1' }}
						/>

<Chart
						width={'800px'}
						height={'600px'}
						chartType="PieChart"
						loader={<div>Loading Chart</div>}
						data={[
							['Task', 'Hours per Day'],
							['First',this.state.quartalExaminationStatistics[0]],
							['Second', this.state.quartalExaminationStatistics[1]],
							['Third', this.state.quartalExaminationStatistics[2]],
							['Fourth', this.state.quartalExaminationStatistics[3]],

						]}
						options={{
							title: 'Quartals examinations',
						}}
						rootProps={{ 'data-testid': '1' }}
						/>
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
                        <div className="col-xs-4" style={{width:'45%'}}>
							<div className="col shadow p-3 bg-white rounded">

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
											onChange={this.handleDescriptionChange}
											value={this.state.pharmacyDescription}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.descriptionError }}>
										Description must be entered.
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
											onChange={this.handleConsultationPriceChange}
											value={this.state.consultationPrice}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.consultationPriceError }}>
										Consultation price must be entered.
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
                        </div>
						<div className="col-xs-8" style={{marginLeft:'3%'}}>
							<YMaps>
								<Map state={mapState} width="33em" height="470px">
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

				</div>

			</React.Fragment>
		);
	}
}

export default PharmacyForAdmin;
