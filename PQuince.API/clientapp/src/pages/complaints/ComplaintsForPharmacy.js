import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header"
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import PharmacyLogo from "../../static/pharmacyLogo.png";

class ComplaintsForPharmacy extends Component {
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

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacies</h5>


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
									<td>
                                        <div style={{marginLeft:'55%'}}>
                                            <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-primary btn-xl" type="button">
                                            <i className="icofont-subscribe mr-1"></i>Add complaint</button>
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

export default ComplaintsForPharmacy;
