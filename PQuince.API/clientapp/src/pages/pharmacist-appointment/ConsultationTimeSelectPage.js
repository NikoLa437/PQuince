import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import PharmaciesForDatePage from "./PharmaciesForDatePage";
import PharmacistForPharmacy from "./PharmacistForPharmacy";
import ModalDialog from "../../components/ModalDialog";
import { Redirect } from "react-router-dom";
import getAuthHeader from "../../GetHeader";

class ConsultationTimeSelectPage extends Component {
	state = {
		pharmacists: [],
		pharmacies: [],
		showingSorted: false,
		selectedDate: new Date(),
		hours: new Date().getHours(),
		minutes: new Date().getMinutes(),
		showDateError: "none",
		hiddenPharmacies: true,
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
	};

	handleSortByGradeAscending = () => {
		Axios.get(
			BASE_URL +
				"/api/pharmacy/get-pharmacy-by-appointment-time/sort-by-grade-ascending/" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByGradeDescending = () => {
		Axios.get(
			BASE_URL +
				"/api/pharmacy/get-pharmacy-by-appointment-time/sort-by-grade-descending/" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceAscending = () => {
		Axios.get(
			BASE_URL +
				"/api/pharmacy/get-pharmacy-by-appointment-time/sort-by-price-ascending/" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ pharmacies: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceDescending = () => {
		Axios.get(
			BASE_URL +
				"/api/pharmacy/get-pharmacy-by-appointment-time/sort-by-price-descending/" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ pharmacies: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleMinutesChange = (event) => {
		this.setState({ minutes: event.target.value });
	};

	handleHoursChange = (event) => {
		this.setState({ hours: event.target.value });
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

		if (
			new Date(this.state.selectedDate.getFullYear(), this.state.selectedDate.getMonth(), this.state.selectedDate.getDate()).getTime() ===
			new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime()
		) {
			if (parseInt(this.state.hours) < today.getHours()) this.setState({ showDateError: "inline" });
			else if (parseInt(this.state.hours) === today.getHours() && parseInt(this.state.minutes) < today.getMinutes())
				this.setState({ showDateError: "inline" });
			else {
				Axios.get(
					BASE_URL +
						"/api/pharmacy/get-pharmacy-by-appointment-time/" +
						new Date(
							this.state.selectedDate.getFullYear(),
							this.state.selectedDate.getMonth(),
							this.state.selectedDate.getDate(),
							this.state.hours,
							this.state.minutes,
							0,
							0
						).getTime(),
					{ headers: { Authorization: getAuthHeader() } }
				)
					.then((res) => {
						this.setState({ pharmacies: res.data, hiddenPharmacies: false });
						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
			}
		} else {
			Axios.get(
				BASE_URL +
					"/api/pharmacy/get-pharmacy-by-appointment-time/" +
					new Date(
						this.state.selectedDate.getFullYear(),
						this.state.selectedDate.getMonth(),
						this.state.selectedDate.getDate(),
						this.state.hours,
						this.state.minutes,
						0,
						0
					).getTime(),
				{ headers: { Authorization: getAuthHeader() } }
			)
				.then((res) => {
					this.setState({ pharmacies: res.data, hiddenPharmacies: false });
					console.log(res.data);
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};

	handlePharmaciesBackIcon = () => {
		this.setState({ hiddenPharmacies: true });
	};

	handleResetSort = () => {
		Axios.get(
			BASE_URL +
				"/api/pharmacy/get-pharmacy-by-appointment-time/" +
				new Date(
					this.state.selectedDate.getFullYear(),
					this.state.selectedDate.getMonth(),
					this.state.selectedDate.getDate(),
					this.state.hours,
					this.state.minutes,
					0,
					0
				).getTime(),
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ pharmacies: res.data, showingSorted: false });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
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
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ pharmacists: res.data, hiddenPharmacists: false, hiddenPharmacies: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handlePharmacistBackIcon = () => {
		this.setState({ hiddenPharmacists: true, hiddenPharmacies: false });
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
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ pharmacists: res.data, showingPharmacistSorted: true });
				console.log(res.data);
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
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ pharmacists: res.data, showingPharmacistSorted: true });
				console.log(res.data);
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
			{ headers: { Authorization: getAuthHeader() } }
		)
			.then((res) => {
				this.setState({ pharmacists: res.data, showingPharmacistSorted: false });
				console.log(res.data);
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

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div hidden={!this.state.hiddenPharmacies || !this.state.hiddenPharmacists} className="container" style={{ marginTop: "8%" }}>
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
								Invalid date or time!
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

				<PharmaciesForDatePage
					consultationDate={this.state.consultationDate}
					onPharmacyClick={this.handlePhamacyClick}
					onPharmaciesBackIcon={this.handlePharmaciesBackIcon}
					hiddenPharmacies={this.state.hiddenPharmacies}
					pharmacies={this.state.pharmacies}
					showingSorted={this.state.showingSorted}
					handleResetSort={this.handleResetSort}
					handleSortByGradeAscending={this.handleSortByGradeAscending}
					handleSortByGradeDescending={this.handleSortByGradeDescending}
					handleSortByPriceAscending={this.handleSortByPriceAscending}
					handleSortByPriceDescending={this.handleSortByPriceDescending}
				/>
				<PharmacistForPharmacy
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

export default ConsultationTimeSelectPage;
