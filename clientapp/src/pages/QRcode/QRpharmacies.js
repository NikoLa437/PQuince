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
		
		Axios.get(BASE_URL + "/api/users/patient/auth", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
		.then((res) => {
			if (res.status === 401) {
				this.setState({
					redirect: true,
					redirectUrl: "/unauthorized"
				});
			} else {
				console.log(res.data);
			}
		})
		.catch((err) => {
			console.log(err);
		});

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
				console.log(res.data)
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

	handleSortByNameAscending = () => {
		let URL = BASE_URL + "/api/pharmacy";
		
		URL += "/sort-by-qr/name-ascending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
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

		URL += "/sort-by-qr/name-descending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 2 });
			})
			.catch((err) => {
				console.log(err);
			});
	};
	
	handleSortByPriceAscending = () => {
		let URL = BASE_URL + "/api/pharmacy";
		
		URL += "/sort-by-qr/price-ascending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 1 });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceDescending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		URL += "/sort-by-qr/price-descending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
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

		URL += "/sort-by-qr/city-name-ascending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 3 });
			})
			.catch((err) => {
				console.log(err);
			});
	};
	
	
	handleSortByCityDescending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		URL += "/sort-by-qr/city-name-descending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
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

		URL += "/sort-by-qr/grade-ascending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 5 });
			})
			.catch((err) => {
				console.log(err);
			});
	};
	
	handleSortByGradeDescending = () => {
		let URL = BASE_URL + "/api/pharmacy";

		URL += "/sort-by-qr/grade-descending/" + this.props.match.params.id;
		
		Axios.get(URL, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true, sortIndicator: 6 });
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

	handleClickOnPharmacy = (id) =>{
		this.setState({
			redirect:true,
			redirectUrl : "/pharmacy/"+id
		})
		//window.location.href = "pharmacy/" + id
	}

	
  render() {
	if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
	
    return (
	    <React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacies to buy eRecipe</h5>

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
											<button className="dropdown-item" type="button" onClick={this.handleSortByPriceAscending}>
												Price ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByPriceDescending}>
												Price descending
											</button>
										</div>
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
											<b>Grade: </b> {pharmacy.EntityDTO.grade}
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