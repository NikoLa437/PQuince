import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";

class DrugsPage extends Component {
	state = {
		drugs: [],
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/drug")
			.then((res) => {
				this.setState({ drugs: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}

	render() {
		return (
			<div hidden={this.props.hidden}>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Choose drug</h5>

					<table
						className="table table-hover"
						style={{ width: "100%", marginTop: "3rem" }}
					>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr
									id={drug.Id}
									key={drug.Id}
									onClick={() => this.props.onDrugSelect(drug)}
									style={{ cursor: "pointer" }}
								>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="70em" />
									</td>
									<td>
										<div>
											<b>Name:</b> {drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Manufacturer:</b>{" "}
											{drug.EntityDTO.manufacturer.EntityDTO.name}
										</div>
										<div>
											<b>Quantity:</b> {drug.EntityDTO.quantity} <b>mg</b>
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

export default DrugsPage;
