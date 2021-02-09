import React, { Component } from "react";
import AppointmentIcon from "../../static/complaint.png";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ModalDialog from "../../components/ModalDialog";
import { withRouter } from "react-router";
import EditOfferModal from "../../components/EditOfferModal";
import getAuthHeader from "../../GetHeader";

class Offers extends Component {
	state = {
		price: "",
        selectedDate: new Date(),
		hours: new Date().getHours(),
        minutes: new Date().getMinutes(),
		openModal: false,
		openModalData: false,
		showOfferModal: false,
		offers: [],
		date:new Date(),
		offerId: "",
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
						console.log(err);
					});
	}
	
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
    

	handleModalClose = () => {
		this.setState({ 
			openModal: false,
		});
	};

	handleModalDataClose = () => {
		this.setState({ 
			openModalData: false,
		});
	};

	handleOffer = () => {
		
		if(this.state.price !==""){
			
		Axios.get(BASE_URL + "/api/offer/check-update/" + this.state.offerId, { headers: { Authorization: getAuthHeader() } })
					.then((res) => {
						console.log(res.data);
						
						if(res.data){
							if(this.state.selectedDate === ""){
								this.setState({ selectedDate: this.state.date });
							}else{
							let offerDate = new Date(this.state.selectedDate.getFullYear(),this.state.selectedDate.getMonth(),this.state.selectedDate.getDate(),this.state.hours,this.state.minutes,0,0);
		
							let OfferDTO = {
								price: this.state.price,
								dateToDelivery: offerDate,
								id: this.state.offerId,
							}

							Axios.put(BASE_URL + "/api/offer/update", OfferDTO ,{ headers: { Authorization: getAuthHeader() } })
								.then((res) => {
									console.log(res.data);
									this.setState({ 
										showOfferModal: false 
									});
									Axios.get(BASE_URL + "/api/offer", { headers: { Authorization: getAuthHeader() } })
									.then((res) => {
										console.log(res.data);
										this.setState({
											offers: res.data,
										});
									})
									.catch((err) => {
										console.log(err);
									});

								})
								.catch((err) => {
									console.log("GRESKA");
									console.log(err);
								});
							}
						}else{

								this.setState({ 
									openModal: true ,
									showOfferModal: false,
								});
						}
					})
					.catch((err) => {
						console.log("GRESKA");
						console.log(err);
					});
		}else{
			this.setState({
				openModalData: true,
			})

		}

	};

	handlePriceChange = (event) => {
		this.setState({ price: event.target.value });
	};
	
	handleOfferClick = (offer) => {
		console.log(offer)
		this.setState({ 
			price: offer.EntityDTO.price,
			offerId: offer.Id,
			date: offer.EntityDTO.dateToDelivery,
			selectedDate: offer.EntityDTO.dateToDelivery,
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
												onClick={() => this.handleOfferClick(offer)}
												className="btn btn-outline-secondary"
											>
												See offer
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
					date={this.state.date}
					handleDateChange={this.handleDateChange}
					selectedDate={this.state.selectedDate}
				/>
				<ModalDialog
					show={this.state.openModal}
					onCloseModal={this.handleModalClose}
					header="Error"
					text="You can't edit this offer because the order date expired."
				/>
				<ModalDialog
					show={this.state.openModalData}
					onCloseModal={this.handleModalDataClose}
					header="Error"
					text="You must fill all the info."
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(Offers);
