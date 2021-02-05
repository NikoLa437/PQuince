import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import PharmacyLogo from "../../static/pharmacyLogo.png";
import "../../App.js";
import { Redirect } from "react-router-dom";
import { withRouter } from "react-router";
import getAuthHeader from "../../GetHeader";

class PatientsSubscribedPharmacies extends Component {
	state = {
		pharmacies: [],
		redirect: false,
		redirectUrl: "",
		unauthorizedRedirect: false,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/pharmacy/subscribed", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else {
					this.setState({ pharmacies: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleClickOnPharmacy = (id) => {
		this.setState({
			redirect: true,
			redirectUrl: "/pharmacy/" + id,
		});
		//window.location.href = "pharmacy/" + id
	};

	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
		if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacy Subscriptions</h5>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.pharmacies.map((pharmacy) => (
								<tr
									id={pharmacy.Id}
									key={pharmacy.Id}
									onClick={() => this.handleClickOnPharmacy(pharmacy.Id)}
									style={{ cursor: "pointer" }}
								>
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

export default withRouter(PatientsSubscribedPharmacies);
