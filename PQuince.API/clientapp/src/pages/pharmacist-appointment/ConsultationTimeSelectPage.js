import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import PharmaciesForDatePage from "./PharmaciesForDatePage";

class ConsultationTimeSelectPage extends Component {
	state = {
		pharmacies: [],
		showingSorted: false,
		selectedDate: new Date(),
		hours: new Date().getHours(),
		minutes: new Date().getMinutes(),
		showDateError: "none",
		hiddenPharmacies: true,
	};

	handleSortByGradeAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-date-ascending")
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByGradeDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-date-descending")
			.then((res) => {
				console.log(res.data);
				this.setState({ pharmacies: res.data, showingSorted: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceAscending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-price-ascending")
			.then((res) => {
				this.setState({ pharmacies: res.data, showingSorted: true });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleSortByPriceDescending = () => {
		Axios.get(BASE_URL + "/api/appointment/dermatologist-history/sort-by-price-descending")
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
		this.setState({ showDateError: "none" });

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
						).getTime()
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
					).getTime()
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

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div hidden={!this.state.hiddenPharmacies} className="container" style={{ marginTop: "8%" }}>
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
					onPharmaciesBackIcon={this.handlePharmaciesBackIcon}
					hiddenPharmacies={this.state.hiddenPharmacies}
					pharmacies={this.state.pharmacies}
					showingSorted={this.state.showingSorted}
					handleSortByGradeAscending={this.handleSortByGradeAscending}
					handleSortByGradeDescending={this.handleSortByGradeDescending}
					handleSortByPriceAscending={this.handleSortByPriceAscending}
					handleSortByPriceDescending={this.handleSortByPriceDescending}
				/>
			</React.Fragment>
		);
	}
}

export default ConsultationTimeSelectPage;
