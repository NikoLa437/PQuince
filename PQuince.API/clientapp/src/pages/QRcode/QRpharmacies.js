import React, { Component } from "react";
import { BASE_URL } from "../../constants.js";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import ModalDialog from "../../components/ModalDialog";
import getAuthHeader from "../../GetHeader";
import PharmacyLogo from "../../static/pharmacyLogo.png";
import '../../App.js'
import { Redirect } from "react-router-dom";
import { withRouter } from "react-router";
 
class QRpharmacies extends Component {
  	state = {
  		eReciptId: "",
		pharmacies: [],
		formShowed: false,
		name: "",
		city: "",
		gradeFrom: "",
		gradeTo: "",
		openModal: false,
		openModalAllergen: false,
		openModalRefused: false,
		distanceFrom: "",
		distanceTo: "",
		showingSearched: false,
		showingSorted: false,
		currentLatitude: null,
		currentLongitude: null,
		sortIndicator: 0,
		redirect:false,
		redirectUrl:''
	};

	fetchData = (id) => {
		this.setState({
			eReciptId: this.props.match.params.id,
		});
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	getCurrentCoords = () => {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition((position) => {
				this.setState({
					currentLatitude: position.coords.latitude,
					currentLongitude: position.coords.longitude,
				});
			});
		}
	};

	componentDidMount() {
		
		const id = this.props.match.params.id;
		
		Axios.get(BASE_URL + "/api/users/is-patient-allergic/" + this.props.match.params.id, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if(res.data.allergic == true){
					let RefuseReceiptDTO = {
						id: this.props.match.params.id
					}
					Axios.post(BASE_URL + "/api/ereceipt/refuse", RefuseReceiptDTO, { headers: { Authorization: getAuthHeader() } })
						.then((res) => {
						})
						.catch((err) => {
							console.log(err);
						});
					this.setState({
						openModalAllergen: true ,
						redirectUrl : "/",
        			})
				}
				
			})
			.catch((err) => {
				console.log(err);
			});
		
		this.fetchData(id);
		this.getCurrentCoords();
		
		let eReceiptIdDTO = {
			id: this.props.match.params.id,
		}
		let RefuseReceiptDTO = {
			id: this.props.match.params.id
		}
		Axios.post(BASE_URL + "/api/ereceipt/check-if-refused", RefuseReceiptDTO, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if(res.data.allergic == true){
					this.setState({
						openModalRefused: true ,
						redirectUrl : "/",
        			})
				}
			})
			.catch((err) => {
				console.log(err);
			});
			
		Axios.get(BASE_URL + "/api/pharmacy/qrpharmacies/" + this.props.match.params.id, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ pharmacies: res.data });
			})
			.catch((err) => {
				console.log(err);
			});
	}

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handleGradeFromChange = (event) => {
		this.setState({ gradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		this.setState({ gradeTo: event.target.value });
	};

	handleDistanceFromChange = (event) => {
		this.setState({ distanceFrom: event.target.value });
	};

	handleDistanceToChange = (event) => {
		this.setState({ distanceTo: event.target.value });
	};

	handleCityChange = (event) => {
		this.setState({ city: event.target.value });
	};

	handleSearch = () => {
		console.log(this.state);

		if (this.state.showingSorted === true) {
			this.setState({ showingSearched: true }, () => {
				if (this.state.sortIndicator === 1) this.handleSortByNameAscending();
				else if (this.state.sortIndicator === 2) this.handleSortByNameDescending();
				else if (this.state.sortIndicator === 3) this.handleSortByCityAscending();
				else if (this.state.sortIndicator === 4) this.handleSortByCityDescending();
				else if (this.state.sortIndicator === 5) this.handleSortByGradeAscending();
				else if (this.state.sortIndicator === 6) this.handleSortByGradeDescending();
			});
		} else {
			if (
				!(
					this.state.gradeFrom === "" &&
					this.state.gradeTo === "" &&
					this.state.distanceFrom === "" &&
					this.state.distanceTo === "" &&
					this.state.name === "" &&
					this.state.city === ""
				)
			) {
				let gradeFrom = this.state.gradeFrom;
				let gradeTo = this.state.gradeTo;
				let distanceFrom = this.state.distanceFrom;
				let distanceTo = this.state.distanceTo;
				let latitude = this.state.currentLatitude;
				let longitude = this.state.currentLongitude;

				if (gradeFrom === "") gradeFrom = 0;
				if (gradeTo === "") gradeTo = 0;
				if (distanceFrom === "") distanceFrom = 0;
				if (distanceTo === "") distanceTo = 0;
				if (latitude === null) latitude = -1000;
				if (longitude === null) longitude = -1000;

				const SEARCH_URL =
					BASE_URL +
					"/api/pharmacy/search?name=" +
					this.state.name +
					"&city=" +
					this.state.city +
					"&gradeFrom=" +
					gradeFrom +
					"&gradeTo=" +
					gradeTo +
					"&distanceFrom=" +
					distanceFrom +
					"&distanceTo=" +
					distanceTo +
					"&latitude=" +
					latitude +
					"&longitude=" +
					longitude;

				Axios.get(SEARCH_URL)
					.then((res) => {
						this.setState({
							pharmacies: res.data,
							formShowed: false,
							showingSearched: true,
						});
						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
			}
		}
	};

	getSearchParams = () => {
		let gradeFrom = this.state.gradeFrom;
		let gradeTo = this.state.gradeTo;
		let distanceFrom = this.state.distanceFrom;
		let distanceTo = this.state.distanceTo;
		let latitude = this.state.currentLatitude;
		let longitude = this.state.currentLongitude;

		if (gradeFrom === "") gradeFrom = 0;
		if (gradeTo === "") gradeTo = 0;
		if (distanceFrom === "") distanceFrom = 0;
		if (distanceTo === "") distanceTo = 0;
		if (latitude === null) latitude = -1000;
		if (longitude === null) longitude = -1000;

		return (
			"?name=" +
			this.state.name +
			"&city=" +
			this.state.city +
			"&gradeFrom=" +
			gradeFrom +
			"&gradeTo=" +
			gradeTo +
			"&distanceFrom=" +
			distanceFrom +
			"&distanceTo=" +
			distanceTo +
			"&latitude=" +
			latitude +
			"&longitude=" +
			longitude
		);
	};

	handleResetSearch = () => {
		Axios.get(BASE_URL + "/api/pharmacy/qrpharmacies" + this.state.eReciptId)
			.then((res) => {
				this.setState({
					pharmacies: res.data,
					formShowed: false,
					showingSearched: false,
					name: "",
					city: "",
					gradeFrom: "",
					gradeTo: "",
					distanceFrom: "",
					distanceTo: "",
				});
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleResetSort = () => {
		this.setState({ showingSorted: false, sortIndicator: 0 });
		if (this.state.showingSearched === true) {
			this.handleSearch();
		} else {
			Axios.get(BASE_URL + "/api/pharmacy/qrpharmacies" + this.state.eReciptId)
				.then((res) => {
					this.setState({ pharmacies: res.data });
					console.log(res.data);
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};

	handleSortByNameAscending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		if (this.state.showingSearched === true) {
			URL += "/search/sort-by/name-ascending" + this.getSearchParams();
		} else {
			URL += "/sort-by/name-ascending";
		}
		Axios.get(URL)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 1 });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByNameDescending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		if (this.state.showingSearched === true) {
			URL += "/search/sort-by/name-descending" + this.getSearchParams();
		} else {
			URL += "/sort-by/name-descending";
		}
		Axios.get(URL)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 2 });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByCityAscending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		if (this.state.showingSearched === true) {
			URL += "/search/sort-by/city-name-ascending" + this.getSearchParams();
		} else {
			URL += "/sort-by/city-name-ascending";
		}
		Axios.get(URL)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 3 });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleDrugClick = (id, price) => {
		let PharmacyERecipeDTO = {
			pharmacyId: id,
			eRecipeId: this.props.match.params.id,
			price: price,
		}
		
		Axios.post(BASE_URL + "/api/pharmacy/qrpharmacies/buy", PharmacyERecipeDTO , { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({ openModal: true });
			})
			.catch((err) => {
				console.log(err);
			});
		
		console.log(PharmacyERecipeDTO);
	};
	
	handleModalClose = () => {
		this.setState({ openModal: false });
	};
	
	handleModalCloseAllergen= () => {
		this.setState({ 
			openModalAllergen: false,
            redirect:true,
		});
	};
	
	handleModalCloseRefused= () => {
		this.setState({ 
			openModalRefused: false,
            redirect:true,
		});
	};
	
	handleSortByCityDescending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		if (this.state.showingSearched === true) {
			URL += "/search/sort-by/city-name-descending" + this.getSearchParams();
		} else {
			URL += "/sort-by/city-name-descending";
		}
		Axios.get(URL)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 4 });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByGradeAscending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		if (this.state.showingSearched === true) {
			URL += "/search/sort-by/grade-ascending" + this.getSearchParams();
		} else {
			URL += "/sort-by/grade-ascending";
		}
		Axios.get(URL)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 5 });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleClickOnPharmacy = (id) =>{
		this.setState({
			redirect:true,
			redirectUrl : "/pharmacy/"+id
		})
		//window.location.href = "pharmacy/" + id
	}

	handleSortByGradeDescending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		if (this.state.showingSearched === true) {
			URL += "/search/sort-by/grade-descending" + this.getSearchParams();
		} else {
			URL += "/sort-by/grade-descending";
		}
		Axios.get(URL)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 6 });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	
  render() {
	if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
	
    return (
	    <React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacies to buy eRecipe</h5>

					<div className="form-group">
						<div className="form-group controls">
							<div className="form-row">
								<button className="btn btn-outline-primary btn-xl" type="button" onClick={this.hangleFormToogle}>
									<i className="icofont-rounded-down mr-1"></i>
									Search pharmacies
								</button>
								<div className={this.state.showingSearched ? "ml-2" : "ml-2 collapse"}>
									<button type="button" className="btn btn-outline-secondary" onClick={this.handleResetSearch}>
										<i className="icofont-close-line mr-1"></i>Reset search criteria
									</button>
								</div>
							</div>
						</div>
					</div>
					<form className={this.state.formShowed ? "form-inline mt-3" : "form-inline mt-3 collapse"} width="100%" id="formCollapse">
						<div className="form-group mb-2" width="100%">
							<input
								placeholder="Name"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="text"
								onChange={this.handleNameChange}
								value={this.state.name}
							/>

							<input
								placeholder="City"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="text"
								onChange={this.handleCityChange}
								value={this.state.city}
							/>

							<input
								placeholder="Grade from"
								className="form-control mr-3"
								style={{ width: "9em" }}
								type="number"
								min="1"
								max="5"
								onChange={this.handleGradeFromChange}
								value={this.state.gradeFrom}
							/>
							<input
								placeholder="Grade to"
								className="form-control"
								style={{ width: "9em" }}
								type="number"
								min="1"
								max="5"
								onChange={this.handleGradeToChange}
								value={this.state.gradeTo}
							/>

							<div className="ml-5 mr-3">
								<input
									placeholder="Distance from"
									className="form-control"
									style={{ width: "10em" }}
									type="number"
									min="0"
									max="50"
									onChange={this.handleDistanceFromChange}
									value={this.state.distanceFrom}
								/>
								<span className="ml-1">km</span>
							</div>
							<div>
								<input
									placeholder="Distance to"
									className="form-control"
									style={{ width: "10em" }}
									type="number"
									min="0"
									max="50"
									onChange={this.handleDistanceToChange}
									value={this.state.distanceTo}
								/>
								<span className="ml-1">km</span>
							</div>
						</div>
						<div>
							<button style={{ background: "#1977cc" }} onClick={this.handleSearch} className="btn btn-primary btn-xl" type="button">
								<i className="icofont-search mr-1"></i>
								Search
							</button>
						</div>
					</form>

					<div className="form-group">
						<div className="form-group controls mb-0 pb-2">
							<div className="form-row mt-3">
								<div className="form-col">
									<div className="dropdown">
										<button
											className="btn btn-primary dropdown-toggle"
											type="button"
											id="dropdownMenu2"
											data-toggle="dropdown"
											aria-haspopup="true"
											aria-expanded="false"
										>
											Sort by
										</button>
										<div className="dropdown-menu" aria-labelledby="dropdownMenu2">
											<button className="dropdown-item" type="button" onClick={this.handleSortByNameAscending}>
												Name ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByNameDescending}>
												Name descending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByCityAscending}>
												City ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByCityDescending}>
												City descending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByGradeAscending}>
												Grade ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByGradeDescending}>
												Grade descending
											</button>
										</div>
									</div>
								</div>
								<div className="form-col ml-3">
									<div className={this.state.showingSorted ? "form-group" : "form-group collapse"}>
										<button type="button" className="btn btn-outline-secondary" onClick={this.handleResetSort}>
											<i className="icofont-close-line mr-1"></i>Reset sort criteria
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.pharmacies.map((pharmacy) => (
								<tr id={pharmacy.Id} key={pharmacy.Id} >
									<td width="130em">
										<img className="img-fluid" src={PharmacyLogo} width="70em" />
									</td>
									<td>
										<div>
											<b>Name: </b> {pharmacy.EntityDTO.pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {pharmacy.EntityDTO.pharmacy.EntityDTO.address.street}, {pharmacy.EntityDTO.pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.pharmacy.EntityDTO.address.country}
										</div>
										<div>
											<b>Grade: </b> {pharmacy.EntityDTO.pharmacy.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
										<div>
											<b>Price for all drugs: </b> {pharmacy.EntityDTO.price}
										</div>
									</td>
									<td className="align-middle">
										<div>
											<button type="button" onClick={() => this.handleDrugClick(pharmacy.Id, pharmacy.EntityDTO.price)} className="btn btn-outline-secondary">
												Buy drugs
											</button>
										</div>
										
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<ModalDialog
					show={this.state.openModal}
					href="/"
					onCloseModal={this.handleModalClose}
					header="Success"
					text="You have successfully buy drugs."
				/>
				<ModalDialog
					show={this.state.openModalAllergen}
					href="/"
					onCloseModal={this.handleModalCloseAllergen}
					header="Warning"
					text="You can't use this eReceipt because you are allergic to some drugs."
				/>
				<ModalDialog
					show={this.state.openModalRefused}
					href="/"
					onCloseModal={this.handleModalCloseRefused}
					header="Error"
					text="This EReceipt is REFUSED, you CAN'T use it."
				/>
			</React.Fragment>
    );
  }
}


export default withRouter(QRpharmacies);