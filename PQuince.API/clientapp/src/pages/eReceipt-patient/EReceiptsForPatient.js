import React, { Component } from "react";
import { NavLink, Redirect } from "react-router-dom";
import receiptLogo from "../../static/receiptLogo.png";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import EReceiptDrugsModal from "../../components/EReceiptDrugsModal";

class EReceiptsForPatient extends Component {
	state = {
		eReceipts: [],
		unauthorizedRedirect: false,
		showingSorted: false,
		showingSearched: false,
		selectedReceiptDrugs: [],
		showModalInfo: false,
		formShowed: false,
		sortIndicator: 0,
		searchStatus: "Status",
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/ereceipt/for-patient", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else {
					this.setState({ eReceipts: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleSortByDateAscending = () => {
		this.setState({ showingSorted: true, sortIndicator: 1 }, () => {
			if (this.state.showingSearched === true) {
				this.handleSearch();
			} else {
				Axios.get(BASE_URL + "/api/ereceipt/for-patient/sort-by-date-ascending", {
					validateStatus: () => true,
					headers: { Authorization: getAuthHeader() },
				})
					.then((res) => {
						if (res.status === 401) {
							this.setState({ unauthorizedRedirect: true });
						} else {
							this.setState({ eReceipts: res.data });
							console.log(res.data);
						}
					})
					.catch((err) => {
						console.log(err);
					});
			}
		});
	};

	handleSortByDateDescending = () => {
		this.setState({ showingSorted: true, sortIndicator: 2 }, () => {
			if (this.state.showingSearched === true) {
				this.handleSearch();
			} else {
				Axios.get(BASE_URL + "/api/ereceipt/for-patient/sort-by-date-descending", {
					validateStatus: () => true,
					headers: { Authorization: getAuthHeader() },
				})
					.then((res) => {
						if (res.status === 401) {
							this.setState({ unauthorizedRedirect: true });
						} else {
							this.setState({ eReceipts: res.data });
							console.log(res.data);
						}
					})
					.catch((err) => {
						console.log(err);
					});
			}
		});
	};

	handleResetSort = () => {
		this.setState({ showingSorted: false, sortIndicator: 0 }, () => {
			if (this.state.showingSearched === true) {
				this.handleSearch();
			} else {
				Axios.get(BASE_URL + "/api/ereceipt/for-patient", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
					.then((res) => {
						if (res.status === 401) {
							this.setState({ unauthorizedRedirect: true });
						} else {
							this.setState({ eReceipts: res.data });
							console.log(res.data);
						}
					})
					.catch((err) => {
						console.log(err);
					});
			}
		});
	};

	handleClickOnEReceipt = (eReceipt) => {
		this.setState({ selectedReceiptDrugs: eReceipt.EntityDTO.drugs, showModalInfo: true });
	};

	handleModalClose = () => {
		this.setState({ showModalInfo: false });
	};

	handleResetSearch = () => {
		this.setState({ showingSearched: false, searchStatus: "Status", formShowed: false });
		if (this.state.showingSorted === true) {
			if (this.state.sortIndicator === 1) {
				this.handleSortByDateAscending();
			} else {
				this.handleSortByDateDescending();
			}
		} else {
			Axios.get(BASE_URL + "/api/ereceipt/for-patient", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
				.then((res) => {
					if (res.status === 401) {
						this.setState({ unauthorizedRedirect: true });
					} else {
						this.setState({ eReceipts: res.data });
						console.log(res.data);
					}
				})
				.catch((err) => {
					console.log(err);
				});
		}
	};

	handleSearch = () => {
		let url = BASE_URL + "/api/ereceipt";
		console.log(this.state);

		if (this.state.showingSorted === true) {
			if (this.state.sortIndicator === 1) {
				console.log("USO");
				url += "/for-patient/sort-by-date-ascending/search" + "?status=" + this.state.searchStatus;
			} else {
				console.log("USO1");
				url += "/for-patient/sort-by-date-descending/search" + "?status=" + this.state.searchStatus;
			}
		} else {
			url += "/for-patient/search" + "?status=" + this.state.searchStatus;
		}

		Axios.get(url, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data);
				this.setState({ eReceipts: res.data, showingSearched: true });
			})
			.catch((err) => {
				console.log(err);
			});
	};

	hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
	};

	handlePharmacyChange = (event) => {
		this.setState({ searchStatus: event.target.value });
	};

	render() {
		if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">E-Receipts</h5>
					<nav className="nav nav-pills nav-justified justify-content-center mt-5">
						<NavLink className="nav-link active" exact to="/patient-ereceipts">
							All e-Receipts
						</NavLink>
						<NavLink className="nav-link" exact to="/patient-ereceipt-drugs">
							Processed drugs from e-Receipts{" "}
						</NavLink>
					</nav>

					<form className="form-inline mt-3" width="100%" id="formCollapse"></form>

					<div className="form-group">
						<div className="form-group controls mb-0 pb-2">
							<div className="form-row mt-3">
								<div className="form-col">
									<div className="dropdown">
										<button
											className="btn btn-primary dropdown-toggle"
											type="button"
											id="dropdownMenu2"
											data-toggle="dropdown"
											aria-haspopup="true"
											aria-expanded="false"
										>
											Sort by
										</button>
										<div className="dropdown-menu" aria-labelledby="dropdownMenu2">
											<button className="dropdown-item" type="button" onClick={this.handleSortByDateAscending}>
												Date ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.handleSortByDateDescending}>
												Date descending
											</button>
										</div>
									</div>
								</div>
								<div className="form-col ml-3">
									<div className={this.state.showingSorted ? "form-group" : "form-group collapse"}>
										<button type="button" className="btn btn-outline-secondary" onClick={this.handleResetSort}>
											<i className="icofont-close-line mr-1"></i>Reset sort criteria
										</button>
									</div>
								</div>
								<div className="form-col ml-3">
									<select
										placeholder="Status"
										onChange={this.handlePharmacyChange}
										value={this.state.searchStatus}
										style={{ width: "9em" }}
										className="form-control mr-3"
									>
										<option key="1" value="Status" selected disabled>
											Status
										</option>
										<option key="2" value="NEW">
											New
										</option>
										<option key="3" value="REJECTED">
											Rejected
										</option>
										<option key="4" value="PROCESSED">
											Processed
										</option>
									</select>
								</div>
								<div className="form-col ml-3">
									<button
										style={{ background: "#1977cc" }}
										onClick={this.handleSearch}
										className="btn btn-primary btn-xl mr-2"
										type="button"
									>
										<i className="icofont-search mr-3"></i>
										Search
									</button>
									<button
										hidden={!this.state.showingSearched}
										className="ml-2"
										type="button"
										className="btn btn-outline-secondary"
										onClick={this.handleResetSearch}
									>
										<i className="icofont-close-line mr-1"></i>Reset search criteria
									</button>
								</div>
							</div>
						</div>
					</div>

					<p className="mb-4 mt-4 text-uppercase">Click on eReceipt to see drugs</p>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.eReceipts.map((eReceipt) => (
								<tr
									id={eReceipt.Id}
									key={eReceipt.Id}
									style={{ cursor: "pointer" }}
									onClick={() => this.handleClickOnEReceipt(eReceipt)}
								>
									<td width="130em">
										<img className="img-fluid" src={receiptLogo} width="70em" />
									</td>
									<td>
										<div>
											<b>Creation Date: </b>{" "}
											{new Date(eReceipt.EntityDTO.creationDate).toLocaleDateString("en-US", {
												day: "2-digit",
												month: "2-digit",
												year: "numeric",
											})}
										</div>
										<div>
											<b>
												Status: <span style={{ color: "#1977cc" }}>{eReceipt.EntityDTO.status}</span>
											</b>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<EReceiptDrugsModal
					show={this.state.showModalInfo}
					header="eReceipt"
					subheader="Drugs"
					drugs={this.state.selectedReceiptDrugs}
					onCloseModal={this.handleModalClose}
				/>
			</React.Fragment>
		);
	}
}

export default EReceiptsForPatient;
