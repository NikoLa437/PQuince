import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ModalDialog from "../../components/ModalDialog";

const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};

class RegisterDrug extends Component {
	state = {
		name: "",
		description: "",
		drugCode: "",
		drugKind: "",
		sideEffects: "",
		recommendAmount: "",
		nameError: "none",
		consulationPriceError: "none",
		openModal: false,
		coords: [],
	};

	constructor(props) {
		super(props);
	}

	handleRecommendAmountChange = (event) => {
		this.setState({ recommendAmount: event.target.value });
	};
	
	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};
	
	handleSideEffectsChange = (event) => {
		this.setState({ sideEffects: event.target.value });
	};

	handleDrugCodeChange = (event) => {
		this.setState({ drugCode: event.target.value });
	};
	
	handleDrugKindChange = (event) => {
		this.setState({ drugKind: event.target.value });
	};
	
	handleDescriptionChange = (event) => {
		this.setState({ description: event.target.value });
	};

	validateForm = (userDTO) => {
		this.setState({
			nameError: "none",
			addressError: "none",
			consulationPriceError: "none",
		});

		if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.consulationPrice === "") {
			this.setState({ consulationPriceError: "initial" });
			return false;
		}
		
		return true;
	};

	handleModalClose = () => {
		this.setState({ openModal: false });
	};

	handleSignUp = () => {
		let street;
		let city;
		let country;
		let latitude;
		let longitude;

		
		let pharmacyDTO = {
			name: this.state.name,
			description: this.state.description,
			address: { street, country, city, latitude, longitude },
			consultationPrice: this.state.consulationPrice*1,
		};
		console.log(pharmacyDTO);
		if (this.validateForm(pharmacyDTO)) {
		
			Axios.post(BASE_URL + "/api/pharmacy", pharmacyDTO)
				.then((res) => {
					console.log("Success");
					this.setState({ openModal: true });
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};
	
	handleSelectChange  = (event) => {
		this.setState({ selectValue: event.target.value });
	};
	
	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "8%" }}>
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Registrater drug
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" novalidate="novalidate">
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Name:</label>
										<input
											placeholder="Name"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleNameChange}
											value={this.state.name}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
								</div>
								
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Drug code:</label>
										<input
											placeholder="Drug code"
											class="form-control"
											id="consulationPrice"
											type="text"
											onChange={this.handleDrugCodeChange}
											value={this.state.drugCode}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.consulationPriceError }}>
										Code must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Drug kind:</label>
										<input
											placeholder="Drug kind"
											class="form-control"
											id="consulationPrice"
											type="text"
											onChange={this.handleDrugKindChange}
											value={this.state.drugKind}
										/>
										<select
									       onChange={this.handleDrugKindChange}
											value={this.state.drugKind}
									     >
										  <option value="dermathologist">Dermathologist</option>
										  <option value="pharmacyadmin">Pharmacy admin</option>
										  <option value="supplier">Supplier</option>
										  <option value="sysadmin">System admin</option>
										</select>	
									</div>
									<div className="text-danger" style={{ display: this.state.consulationPriceError }}>
										Code must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Side effects:</label>
										<input
											placeholder="Side effects"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleSideEffectsChange}
											value={this.state.sideEffects}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Side effects must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Recommend amount:</label>
										<input
											placeholder="Recommend amount"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleRecommendAmountChange}
											value={this.state.recommendAmount}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Recommend amount must be entered.
									</div>
								</div>
								<div className="form-group">
									<button
										style={{
											background: "#1977cc",
											marginTop: "15px",
											marginLeft: "40%",
											width: "20%",
										}}
										onClick={this.handleSignUp}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Sign Up
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<ModalDialog
					show={this.state.openModal}
					href="/"
					onCloseModal={this.handleModalClose}
					header="Successful registration"
					text="You have successfully registered staff."
				/>
			</React.Fragment>
		);
	}
}

export default RegisterDrug;
