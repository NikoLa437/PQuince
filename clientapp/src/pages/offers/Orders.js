import React, { Component } from "react";
import AppointmentIcon from "../../static/complaint.png";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import { withRouter } from "react-router";
import ModalDialog from "../../components/ModalDialog";
import OfferModal from "../../components/OfferModal";
import getAuthHeader from "../../GetHeader";
import OrderDrugsModal from "../../components/OrderDrugsModal";

class Orders extends Component {
	state = {
		price: "",
		selectedDate: new Date(),
		hours: new Date().getHours(),
        minutes: new Date().getMinutes(),
		replacingDrugs:[],
		pharmacyName:"",
		showOfferModal: false,
		orderId: "",
		showOrderModal: false,
		openModal: false,
		orders:[],
		address:"",
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

	handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleOffer = () => {
		
		if(this.state.price !==""){

			let offerDate = new Date(this.state.selectedDate.getFullYear(),this.state.selectedDate.getMonth(),this.state.selectedDate.getDate(),this.state.hours,this.state.minutes,0,0);
			let OfferDTO = {
								price: this.state.price,
								dateToDelivery: offerDate,
								id: this.state.orderId,
							}
			Axios.put(BASE_URL + "/api/offer/check-drugs", OfferDTO ,{ headers: { Authorization: getAuthHeader() } })
					.then((res) => {
						console.log(res.data);
						
					})
					.catch((err) => {
						console.log("GRESKA");
						console.log(err);
					});

			console.log(OfferDTO, "AJDE RADI")
		}
			
	};

	handlePriceChange = (event) => {
		this.setState({ price: event.target.value });
	};

	handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleMinutesChange = (event) => {
		if (event.target.value > 59) this.setState({ minutes: 59 });
		else if (event.target.value < 0) this.setState({ minutes: 0 });
		else this.setState({ minutes: event.target.value });
	};

	handleHoursChange = (event) => {
		if (event.target.value > 23) this.setState({ hours: 23 });
		else if (event.target.value < 0) this.setState({ hours: 0 });
		else this.setState({ hours: event.target.value });
    };
	
	handleOfferClick = (order) => {
		this.setState({ 
			price: "",
			showOfferModal: true,
			orderId: order.Id,
		});
		
	};
	
	handleModalClose = () => {
		this.setState({ 
			openModal: false,
			redirect:true, 
		});
	};
	handleOrderClick = (order) => {
		console.log(order,"AA");
		this.setState({
			 showOrderModal: true,
			 address: order.pharmacy.EntityDTO.address.street +", "+ order.pharmacy.EntityDTO.address.city +", " +
			 order.pharmacy.EntityDTO.address.country,
			 pharmacyName: order.pharmacy.EntityDTO.name,
			 replacingDrugs: order.order
		});
	};
	
	handleOfferModalClose = () => {
		this.setState({ showOfferModal: false });
	};

	handleOrderModalClose = () => {
		this.setState({ showOrderModal: false });
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
											<button type="button" onClick={() => this.handleOrderClick(order.EntityDTO)} className="btn btn-outline-secondary">
												See order
											</button>
										</div>
										<div className="mt-2" >
											<button
												type="button"
												onClick={() => this.handleOfferClick(order)}
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
					price={this.state.price}
					onCloseModal={this.handleOfferModalClose}
					giveOffer={this.handleOffer}
					handleDateChange={this.handleDateChange}
					selectedDate={this.state.selectedDate}
				/>
				<OrderDrugsModal
					buttonName="Order"
					header="Order"
					handlePriceChange={this.handlePriceChange}
					show={this.state.showOrderModal}
					replacingDrugs={this.state.replacingDrugs}
					address={this.state.address}
					pharmacyName={this.state.pharmacyName}
					onCloseModal={this.handleOrderModalClose}
				/>
				<ModalDialog
					show={this.state.openModal}
					onCloseModal={this.handleModalClose}
					header="Error"
					text="You must fill all the info."
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(Orders);
