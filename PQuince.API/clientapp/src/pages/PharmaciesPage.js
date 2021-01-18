import React, { Component } from "react";
import TopBar from "../components/TopBar";
import Header from "../components/Header";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import PharmacyLogo from "../static/pharmacyLogo.png";

class PharmaciesPage extends Component {
	state = {
		pharmacies: [],
		formShowed: false,
		name: "",
		city: "",
		gradeFrom: "",
		gradeTo: "",
		distanceFrom: "",
		distanceTo: "",
		showingSearched: false,
		currentLatitude: null,
		currentLongitude: null,
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
		this.getCurrentCoords();

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
	};

	handleResetSearch = () => {
		Axios.get(BASE_URL + "/api/pharmacy")
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

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacies</h5>

					<button
						className="btn btn-outline-primary btn-xl"
						type="button"
						onClick={this.hangleFormToogle}
					>
						<i className="icofont-rounded-down mr-1"></i>
						Search pharmacies
					</button>
					<form
						className={
							this.state.formShowed ? "form-inline mt-3" : "form-inline mt-3 collapse"
						}
						width="100%"
						id="formCollapse"
					>
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
							<button
								style={{ background: "#1977cc" }}
								onClick={this.handleSearch}
								className="btn btn-primary btn-xl"
								type="button"
							>
								<i className="icofont-search mr-1"></i>
								Search
							</button>
						</div>
					</form>

					<div
						className={
							this.state.showingSearched
								? "form-group mt-2"
								: "form-group mt-2 collapse"
						}
					>
						<button
							type="button"
							className="btn btn-outline-secondary"
							onClick={this.handleResetSearch}
						>
							<i className="icofont-close-line mr-1"></i>Reset criteria
						</button>
					</div>

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.pharmacies.map((pharmacy) => (
								<tr id={pharmacy.Id} key={pharmacy.Id} onClick={this.handle}>
									<td width="130em">
										<img
											className="img-fluid"
											src={PharmacyLogo}
											width="70em"
										/>
									</td>
									<td>
										<div>
											<b>Name: </b> {pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {pharmacy.EntityDTO.address.street},{" "}
											{pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.address.country}
										</div>
										<div>
											<b>Grade: </b> {pharmacy.EntityDTO.grade}
											<i
												className="icofont-star"
												style={{ color: "#1977cc" }}
											></i>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
			</React.Fragment>
		);
	}
}

export default PharmaciesPage;
