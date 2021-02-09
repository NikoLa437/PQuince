import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import ModalDialog from "../../components/ModalDialog";
import { Redirect } from "react-router-dom";
import getAuthHeader from "../../GetHeader";
import PharmacistForSelectedTime from "./PharmacistForSelectedTime";
import { withRouter } from "react-router";

class TimeSelectingPage extends Component {
	state = {
		pharmacists: [],
		selectedDate: new Date(),
		hours: new Date().getHours(),
		minutes: new Date().getMinutes(),
		showDateError: "none",
		hiddenPharmacists: true,
		pharmacyName: "",
		pharmacyAddress: "",
		pharmacyGrade: "",
		pharmacyPrice: "",
		consultationDate: new Date(),
		selectedPharmacyId: "",
		showingPharmacistSorted: false,
		redirect: false,
		openModalSuccess: false,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		unauthorizedRedirect: false,
	};

	hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

	fetchData = (id) => {
		this.setState({
			selectedPharmacyId: id,
		});
	};

	componentDidMount() {
		if (!this.hasRole("ROLE_PATIENT")) {
			this.setState({ unauthorizedRedirect: true });
		} else {
			const id = this.props.match.params.id;
			this.fetchData(id);
			Axios.get(BASE_URL + "/api/pharmacy/get-pharmacy-with-price/" + id, {
				validateStatus: () => true,
				headers: { Authorization: getAuthHeader() },
			})
				.then((res) => {
					if (res.status === 401) {
						this.setState({ unauthorizedRedirect: true });
					} else if (res.status === 200) {
						this.setState({
							selectedPharmacyId: res.data.Id,
							pharmacyName: res.data.EntityDTO.name,
							pharmacyAddress:
								res.data.EntityDTO.address.street +
								", " +
								res.data.EntityDTO.address.city +
								", " +
								res.data.EntityDTO.address.country,
							pharmacyGrade: res.data.EntityDTO.grade,
							pharmacyPrice: res.data.EntityDTO.discountPrice,
						});
						console.log(res.data);
					}
				})
				.catch((err) => {
					console.log(err);
				});
		}
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

	handleCheckAvailability = () => {
		let today = new Date();
		this.setState({
			showDateError: "none",
			consultationDate: new Date(
				this.state.selectedDate.getFullYear(),
				this.state.selectedDate.getMonth(),
				this.state.selectedDate.getDate(),
				this.state.hours,
				this.state.minutes,
				0,
				0
			).getTime(),
		});

		let dateMin = new Date().getTime() + 3600000;
		let selectedDate = new Date(
			this.state.selectedDate.getFullYear(),
			this.state.selectedDate.getMonth(),
			this.state.selectedDate.getDate(),
			this.state.hours,
			this.state.minutes,
			0,
			0
		).getTime();
		console.log(dateMin, selectedDate, selectedDate <= dateMin);
		if (selectedDate <= dateMin) {
			this.setState({ showDateError: "inline" });
		} else {
			this.setState({
				consultationDate: new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			});
			Axios.get(
				BASE_URL +
					"/api/users/get-pharmacists?pharmacyId=" +
					this.state.selectedPharmacyId +
					"&startDateTime=" +
					new Date(
						this.state.selectedDate.getFullYear(),
						this.state.selectedDate.getMonth(),
						this.state.selectedDate.getDate(),
						this.state.hours,
						this.state.minutes,
						0,
						0
					).getTime(),
				{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } }
			)
				.then((res) => {
					if (res.status === 401) {
						this.setState({ unauthorizedRedirect: true });
					} else if (res.status === 200) {
						this.setState({ pharmacists: res.data, hiddenPharmacists: false });
						console.log(res.data);
					}
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};

	handlePhamacyClick = (pharmacy) => {
		this.setState({
			selectedPharmacyId: pharmacy.Id,
			pharmacyName: pharmacy.EntityDTO.name,
			pharmacyAddress: pharmacy.EntityDTO.address.street + ", " + pharmacy.EntityDTO.address.city + ", " + pharmacy.EntityDTO.address.country,
			pharmacyGrade: pharmacy.EntityDTO.grade,
			pharmacyPrice: pharmacy.EntityDTO.discountPrice,
			consultationDate: new Date(
				this.state.selectedDate.getFullYear(),
				this.state.selectedDate.getMonth(),
				this.state.selectedDate.getDate(),
				this.state.hours,
				this.state.minutes,
				0,
				0
			).getTime(),
		});
		Axios.get(
			BASE_URL +
				"/api/users/get-pharmacists?pharmacyId=" +
				pharmacy.Id +
				"&startDateTime=" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else if (res.status === 200) {
					this.setState({ pharmacists: res.data, hiddenPharmacists: false, hiddenPharmacies: true });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handlePharmacistBackIcon = () => {
		this.setState({ hiddenPharmacists: true, hiddenPharmacies: false, hiddenFailAlert: true });
	};

	handleSortPharmacstByGradeAscending = () => {
		Axios.get(
			BASE_URL +
				"/api/users/get-pharmacists/sort-by-grade-ascending?pharmacyId=" +
				this.state.selectedPharmacyId +
				"&startDateTime=" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else if (res.status === 200) {
					this.setState({ pharmacists: res.data, showingPharmacistSorted: true });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortPharmacstByGradeDescending = () => {
		Axios.get(
			BASE_URL +
				"/api/users/get-pharmacists/sort-by-grade-descending?pharmacyId=" +
				this.state.selectedPharmacyId +
				"&startDateTime=" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else if (res.status === 200) {
					this.setState({ pharmacists: res.data, showingPharmacistSorted: true });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleResetPharmacistSort = () => {
		Axios.get(
			BASE_URL +
				"/api/users/get-pharmacists?pharmacyId=" +
				this.state.selectedPharmacyId +
				"&startDateTime=" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else if (res.status === 200) {
					this.setState({ pharmacists: res.data, showingPharmacistSorted: false });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleModalSuccessClose = () => {
		this.setState({ openModalSuccess: false, redirect: true });
	};

	handlePharmacistClick = (pharmacistId) => {
		this.setState({ hiddenFailAlert: true, failHeader: "", failMessage: "" });

		let EntityDTO = {
			pharmacistId: pharmacistId,
			startDateTime: new Date(
				this.state.selectedDate.getFullYear(),
				this.state.selectedDate.getMonth(),
				this.state.selectedDate.getDate(),
				this.state.hours,
				this.state.minutes,
				0,
				0
			),
		};

		Axios.post(BASE_URL + "/api/appointment/reserve-appointment", EntityDTO, {
			validateStatus: () => true,
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				if (res.status === 400) {
					this.setState({ hiddenFailAlert: false, failHeader: "Bad request", failMessage: res.data });
				} else if (res.status === 500) {
					this.setState({ hiddenFailAlert: false, failHeader: "Internal server error", failMessage: "Server error." });
				} else if (res.status === 201) {
					this.setState({ openModalSuccess: true });
					console.log(res);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

	render() {
		if (this.state.redirect) return <Redirect push to="/" />;
		if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div hidden={!this.state.hiddenPharmacists} className="container" style={{ marginTop: "8%" }}>
					<h3
						className="text-center mb-0 text-uppercase"
						style={{ letterSpacing: "0.1em", fontWight: "bold", color: "#1977cc", marginTop: "2rem" }}
					>
						Find the perfect time for pharmacist consultation in just a few steps
					</h3>

					<h4 className="text-center mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Select desired date
					</h4>
					<div className="control-group mt-5">
						<div className="form-row justify-content-center">
							<div className="form-col  mr-3">
								<div className="mb-2" style={{ fontSize: "1.5em" }}>
									Select date:
								</div>

								<DatePicker
									className="form-control"
									minDate={new Date()}
									onChange={(date) => this.handleDateChange(date)}
									selected={this.state.selectedDate}
								/>
							</div>
							<div className="form-col ml-2">
								<div className="mb-2" style={{ fontSize: "1.5em" }}>
									Hours:
								</div>
								<input
									onChange={this.handleHoursChange}
									className="form-control mr-3"
									value={this.state.hours}
									style={{ width: "9em" }}
									type="number"
									min="00"
									max="23"
								/>
							</div>
							<div className="form-col ml-2">
								<div className="mb-2" style={{ fontSize: "1.5em" }}>
									Minutes:
								</div>
								<input
									min="00"
									className="form-control"
									onChange={this.handleMinutesChange}
									value={this.state.minutes}
									style={{ width: "9em" }}
									type="number"
									max="59"
								/>
							</div>
						</div>
						<div className="form-row justify-content-center mt-4">
							<div className="mt-1 text-danger" style={{ fontSize: "1.5em", display: this.state.showDateError }}>
								Invalid date or time! Desired time needs to be at least 1 hour from current time.
							</div>
						</div>
						<div className="form-row justify-content-center mt-4">
							<button className="btn btn-primary btn-lg" type="button" onClick={this.handleCheckAvailability}>
								<i className="icofont-search mr-1"></i>
								Check availability
							</button>
						</div>
					</div>
				</div>

				<PharmacistForSelectedTime
					hiddenFailAlert={this.state.hiddenFailAlert}
					failHeader={this.state.failHeader}
					failMessage={this.state.failMessage}
					handleCloseAlertFail={this.handleCloseAlertFail}
					onPharmacistClick={this.handlePharmacistClick}
					showingPharmacistSorted={this.state.showingPharmacistSorted}
					handleSortPharmacstByGradeAscending={this.handleSortPharmacstByGradeAscending}
					handleSortPharmacstByGradeDescending={this.handleSortPharmacstByGradeDescending}
					handleResetPharmacistSort={this.handleResetPharmacistSort}
					consultationDate={this.state.consultationDate}
					pharmacists={this.state.pharmacists}
					pharmacyName={this.state.pharmacyName}
					pharmacyAddress={this.state.pharmacyAddress}
					pharmacyGrade={this.state.pharmacyGrade}
					pharmacyPrice={this.state.pharmacyPrice}
					hidden={this.state.hiddenPharmacists}
					onPharmacistBackIcon={this.handlePharmacistBackIcon}
				/>
				<ModalDialog
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully reserved"
					text="Your reserved pharmacist consultation. Further details are sent to your email address."
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(TimeSelectingPage);
