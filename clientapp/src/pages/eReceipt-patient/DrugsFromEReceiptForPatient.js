import React, { Component } from "react";
import { NavLink, Redirect } from "react-router-dom";
import EReceiptsForDrugModal from "../../components/EReceiptsForDrugModal";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import CapsuleLogo from "../../static/capsuleLogo.png";

class DrugsFromEReceiptForPatient extends Component {
	state = {
		drugs: [],
		unauthorizedRedirect: false,
		selectedDrugEReceipts: [],
		showModalInfo: false,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/ereceipt/processed-drugs", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if (res.status === 401) {
					this.setState({ unauthorizedRedirect: true });
				} else {
					this.setState({ drugs: res.data });
					console.log(res.data);
				}
			})
			.catch((err) => {
				console.log(err);
			});
	}

	handleModalClose = () => {
		this.setState({ showModalInfo: false });
	};

	handleDrugClick = (drug) => {
		this.setState({ selectedDrugEReceipts: drug.EntityDTO.eReceipts, showModalInfo: true });
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
						<NavLink className="nav-link" exact to="/patient-ereceipts">
							All e-Receipts
						</NavLink>
						<NavLink className="nav-link active" exact to="/patient-ereceipt-drugs">
							Processed drugs from e-Receipts{" "}
						</NavLink>
					</nav>
					<p className="mb-4 mt-4 text-uppercase">Click on drug to see processed e-Receipts</p>

					<table className="table table-hover" style={{ width: "100%" }}>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr id={drug.Id} key={drug.Id} style={{ cursor: "pointer" }} onClick={() => this.handleDrugClick(drug)}>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="70em" />
									</td>
									<td>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Kind:</b> {drug.EntityDTO.drugKind}
										</div>
										<div>
											<b>Format:</b> {drug.EntityDTO.drugFormat}
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<EReceiptsForDrugModal
					show={this.state.showModalInfo}
					header="eReceipts"
					ereceipts={this.state.selectedDrugEReceipts}
					onCloseModal={this.handleModalClose}
				/>
			</React.Fragment>
		);
	}
}

export default DrugsFromEReceiptForPatient;
