import React, { Component } from "react";
import PharmacyLogo from "../../static/pharmacyLogo.png";
import PharmacistLogo from "../../static/pharmacistLogo.png";

class PharmacistForPharmacy extends Component {
	render() {
		return (
			<div hidden={this.props.hidden} className="container" style={{ marginTop: "10%" }}>
				<h5 className=" text-center mb-0 mt-2 text-uppercase">Available pharmacists</h5>
				<form>
					<div className="control-group">
						<div className="form-group controls mb-0 pb-2">
							<div className="form-row">
								<button onClick={this.props.onPharmacistBackIcon} className="btn btn-link btn-xl" type="button">
									<i className="icofont-rounded-left mr-1"></i>
									Back
								</button>
							</div>
							<p className="mt-2 text-uppercase" style={{ fontSize: "1.5em" }}>
								Consultation date:{" "}
								{new Date(this.props.consultationDate).toLocaleDateString("en-US", {
									day: "2-digit",
									month: "2-digit",
									year: "numeric",
									hour: "2-digit",
									minute: "2-digit",
								})}
							</p>
							<div className="form-row mt-4" width="140em" style={{ color: "#6c757d", opacity: 1 }}>
								<div className="form-col">
									<img className="img-fluid" src={PharmacyLogo} width="100em" />
								</div>
								<div className="form-col">
									<div>
										<b>Name: </b> {this.props.pharmacyName}
									</div>
									<div>
										<b>Address: </b> {this.props.pharmacyAddress}
									</div>
									<div>
										<b>Grade: </b> {this.props.pharmacyGrade}
										<i className="icofont-star" style={{ color: "#1977cc" }}></i>
									</div>
									<div>
										<b>Consultation price: </b> {(Math.round(this.props.pharmacyPrice * 100) / 100).toFixed(2)}
									</div>
								</div>
							</div>

							<p className="mb-2 mt-3 text-uppercase">Click on pharmacist to make reservation</p>

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
											<button className="dropdown-item" type="button" onClick={this.props.handleSortPharmacstByGradeAscending}>
												Grade ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.props.handleSortPharmacstByGradeDescending}>
												Grade descending
											</button>
										</div>
									</div>
								</div>
								<div className="form-col ml-3">
									<div className={this.props.showingPharmacistSorted ? "form-group" : "form-group collapse"}>
										<button type="button" className="btn btn-outline-secondary" onClick={this.props.handleResetPharmacistSort}>
											<i className="icofont-close-line mr-1"></i>Reset criteria
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
				<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
					<tbody>
						{this.props.pharmacists.map((pharmacist) => (
							<tr
								id={pharmacist.Id}
								key={pharmacist.Id}
								style={{ cursor: "pointer" }}
								onClick={() => this.props.onPharmacistClick(pharmacist.Id)}
							>
								<td width="130em">
									<img className="img-fluid" src={PharmacistLogo} width="70em" />
								</td>
								<td>
									<div>
										<b>Name: </b> {pharmacist.EntityDTO.name}
									</div>
									<div>
										<b>Surname: </b> {pharmacist.EntityDTO.surname}
									</div>
									<div>
										<b>Grade: </b> {pharmacist.EntityDTO.grade}
										<i className="icofont-star" style={{ color: "#1977cc" }}></i>
									</div>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			</div>
		);
	}
}

export default PharmacistForPharmacy;
