import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import PharmacyLogo from "../../static/pharmacyLogo.png";
import '../../App.js'
import { Redirect } from "react-router-dom";
import getAuthHeader from "../../GetHeader";


class Subscription extends Component {
	state = {
		pharmacies: [],
		formShowed: false,
		name: "",
		city: "",
		currentLatitude: null,
		currentLongitude: null,
		sortIndicator: 0,
		redirect:false,
		redirectUrl:''
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

		Axios.get(BASE_URL + "/api/users/subscribed-pharmacies", { 
			headers: { Authorization: getAuthHeader() } 
		})
			.then((res) => {
				this.setState({ pharmacies: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

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
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Subscribed pharmacies</h5>

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.pharmacies.map((pharmacy) => (
								<tr id={pharmacy.Id} key={pharmacy.Id} onClick={() => this.handleClickOnPharmacy(pharmacy.Id)}>
									<td width="130em">
										<img className="img-fluid" src={PharmacyLogo} width="70em" />
									</td>
									<td>
										<div>
											<b>Name: </b> {pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {pharmacy.EntityDTO.address.street}, {pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.address.country}
										</div>
										<div>
											<b>Grade: </b> {pharmacy.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
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

export default Subscription;
