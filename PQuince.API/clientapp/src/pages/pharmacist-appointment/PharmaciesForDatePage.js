import React, { Component } from "react";
import PharmacyLogo from "../../static/pharmacyLogo.png";

class PharmaciesForDatePage extends Component {
	render() {
		return (
			<div hidden={this.props.hiddenPharmacies}>
				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Available Pharmacies</h5>

					<div className="form-group">
						<div className="form-group controls mb-0 pb-2">
							<div className="form-row">
								<button onClick={this.props.onPharmaciesBackIcon} className="btn btn-link btn-xl" type="button">
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
											<button className="dropdown-item" type="button" onClick={this.props.handleSortByGradeAscending}>
												Grade ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.props.handleSortByGradeDescending}>
												Grade descending
											</button>
											<button className="dropdown-item" type="button" onClick={this.props.handleSortByPriceAscending}>
												Consultation price ascending
											</button>
											<button className="dropdown-item" type="button" onClick={this.props.handleSortByPriceDescending}>
												Consultation price descending
											</button>
										</div>
									</div>
								</div>
								<div className="form-col ml-3">
									<div className={this.props.showingSorted ? "form-group" : "form-group collapse"}>
										<button type="button" className="btn btn-outline-secondary" onClick={this.props.handleResetSort}>
											<i className="icofont-close-line mr-1"></i>Reset criteria
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>

					<table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.props.pharmacies.map((pharmacy) => (
								<tr
									id={pharmacy.Id}
									key={pharmacy.Id}
									onClick={() => this.props.onPharmacyClick(pharmacy)}
									style={{ cursor: "pointer" }}
								>
									<td width="150em">
										<img
											className="img-fluid"
											src={PharmacyLogo}
											width={pharmacy.EntityDTO.price === pharmacy.EntityDTO.discountPrice ? "90em" : "110em"}
										/>
									</td>
									<td>
										<div>
											<b>Name: </b> {pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {pharmacy.EntityDTO.address.street}, {pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.address.country}
										</div>
										<div>
											<b>Grade: </b> {pharmacy.EntityDTO.grade}
											<i className="icofont-star" style={{ color: "#1977cc" }}></i>
										</div>
										<div>
											<b>Consultation price::</b>{" "}
											<span
												style={
													pharmacy.EntityDTO.price !== pharmacy.EntityDTO.discountPrice
														? { textDecoration: "line-through" }
														: {}
												}
											>
												{" "}
												{(Math.round(pharmacy.EntityDTO.price * 100) / 100).toFixed(2)}
											</span>
											<b> din</b>
										</div>
										<div hidden={pharmacy.EntityDTO.price === pharmacy.EntityDTO.discountPrice}>
											<b>Consultation price with discount: </b>{" "}
											{(Math.round(pharmacy.EntityDTO.discountPrice * 100) / 100).toFixed(2)}
											<b> din</b>
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
			</div>
		);
	}
}

export default PharmaciesForDatePage;
