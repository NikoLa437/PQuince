import React, { Component } from "react";
import AppointmentIcon from "../../static/complaint.png";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import { withRouter } from "react-router";
import EditOfferModal from "../../components/EditOfferModal";
import getAuthHeader from "../../GetHeader";

class Offers extends Component {
	state = {
		price: "",
		showOfferModal: false,
		offers: [],
		date:"",
	};
	
	componentDidMount() {
			Axios.get(BASE_URL + "/api/offer", { headers: { Authorization: getAuthHeader() } })
					.then((res) => {
						console.log(res.data);
						this.setState({
							offers: res.data,
						});
					})
					.catch((err) => {
						console.log("GRESKA");
						console.log(err);
					});
	}
	handleOffer = () => {
	
	};

	handlePriceChange = (event) => {
		this.setState({ price: event.target.value });
	};
	
	handleOfferClick = (offer) => {
		this.setState({ 
			price: offer.price,
			date: offer.dateToDelivery,
			showOfferModal: true 
		});
	};
	
	handleOfferModalClose = () => {
		this.setState({ showOfferModal: false });
	};

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Offers </h5>
						<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
							<tbody>
								{this.state.offers.map((offer) => (
									<tr className="rounded">
										<td width="190em">
											<img className="img-fluid" src={AppointmentIcon} width="150em" />
										</td>
										<td>
											<div>
												<b>Due to date : </b>{" "}
												{new Date(offer.EntityDTO.dateToDelivery).toLocaleTimeString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
													hour: "2-digit",
													minute: "2-digit",
												})}
											</div>
											<div>
												<b>Status : </b>{" "}
												{offer.EntityDTO.orderStatus}
											</div>
											<div>
												<b>Price : </b>{" "}
												{offer.EntityDTO.price}{" "}din
											</div>
										</td>
										<td className="align-middle">
											<button
												type="button"
												onClick={() => this.handleOfferClick(offer.EntityDTO)}
												className="btn btn-outline-secondary"
											>
												Edit an offer
											</button>
										</td>
									</tr>
								))}
							</tbody>
						</table>
				</div>

				<EditOfferModal
					buttonName="Edit offer"
					header="Edit an offer"
					handlePriceChange={this.handlePriceChange}
					show={this.state.showOfferModal}
					price={this.state.price}
					date={this.state.date}
					onCloseModal={this.handleOfferModalClose}
					giveOffer={this.handleOffer}
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(Offers);
