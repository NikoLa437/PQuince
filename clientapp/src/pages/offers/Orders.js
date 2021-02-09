import React, { Component } from "react";
import AppointmentIcon from "../../static/complaint.png";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import { withRouter } from "react-router";
import OfferModal from "../../components/OfferModal";
import getAuthHeader from "../../GetHeader";

class Orders extends Component {
	state = {
		price: "",
		showOfferModal: false,
		orders:[],
	};

	componentDidMount() {
			Axios.get(BASE_URL + "/api/order/provider", { headers: { Authorization: getAuthHeader() } })
					.then((res) => {
						console.log(res.data);
						this.setState({
							orders: res.data
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
	
	handleOfferClick = () => {
		this.setState({ showOfferModal: true });
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
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Orders </h5>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.orders.map((order) => (
								<tr className="rounded">
									<td width="190em">
										<img className="img-fluid" src={AppointmentIcon} width="150em" />
									</td>
									<td>
										<div>
											<b>Date : </b>{" "}
											{new Date(order.EntityDTO.date).toLocaleTimeString("en-US", {
													day: "2-digit",
													month: "2-digit",
													year: "numeric",
													hour: "2-digit",
													minute: "2-digit",
												})}
										</div>
										<div>
											<b>Pharmacy : </b>{" "}
											{order.EntityDTO.pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {order.EntityDTO.pharmacy.EntityDTO.address.street}, {order.EntityDTO.pharmacy.EntityDTO.address.city},{" "}
											{order.EntityDTO.pharmacy.EntityDTO.address.country}
										</div>
									</td>
									<td className="align-middle">
										<div>
											<button type="button" onClick={() => this.handleOfferClick()} className="btn btn-outline-secondary">
												See order
											</button>
										</div>
										<div className="mt-2" >
											<button
												type="button"
												onClick={() => this.handleOfferClick()}
												className="btn btn-outline-secondary"
											>
												Make an offer
											</button>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>

				</div>

				<OfferModal
					buttonName="Send offer"
					header="Make an offer"
					handlePriceChange={this.handlePriceChange}
					show={this.state.showOfferModal}
					onCloseModal={this.handleOfferModalClose}
					giveOffer={this.handleOffer}
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(Orders);
