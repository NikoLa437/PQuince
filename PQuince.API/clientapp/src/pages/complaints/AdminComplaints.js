import React, { Component } from "react";
import AppointmentIcon from "../../static/appointment-icon.jpg";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ComplaintCreateModal from "../../components/ComplaintCreateModal";

class AdminComplaints extends Component {
	state = {
		complaints: [],
		showingSorted: false,
		showFeedbackModal: false,
		selectedStaffId: "",
		StaffName: "",
		StaffSurame: "",
		complaint: "",
		text: "",
		grade: 0,
	};

	componentDidMount() {
		Axios.get(BASE_URL + "api/staff/complaint")
			.then((res) => {
				this.setState({ complaints: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	}


	handleComplaintChange = (event) => {
		this.setState({ complaint: event.target.value });
	};
	
	
	handleFeedbackClick = (complaint) => {
		console.log(complaint);
		Axios.get(BASE_URL + "/api/staff/feedback/" + complaint.Id, { validateStatus: () => true })
			.then((res) => {
				console.log(res.data);
				if (res.status === 404) {
					this.setState({
						selectedStaffId: complaint.Id,
						showFeedbackModal: true
					});
				} else {
					this.setState({
						selectedStaffId: complaint.Id,
						showFeedbackModal: true
					});
				}
			})
			.catch((err) => {
				console.log(err);
			});
	};

	handleFeedbackModalClose = () => {
		this.setState({ showFeedbackModal: false });
	};

	handleComaplaint = () => {
		let entityDTO = {
			staffId: this.state.selectedStaffId,
			date: new Date(),
			text: this.state.complaint,
		};
		Axios.post(BASE_URL + "/api/staff/complaint", entityDTO)
			.then((resp) => {
				Axios.get(BASE_URL + "api/staff/complaint")
					.then((res) => {
						this.setState({ complaints: res.data, showFeedbackModal: false });
						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
			})
			.catch((err) => {
				console.log(err);
			});
	};


	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Dermatologist Appointment History</h5>

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.complaints.map((complaint) => (
								<tr id={complaint.Id} key={complaint.Id} className="rounded">
									<td width="190em">
										<img className="img-fluid" src={AppointmentIcon} width="150em" />
									</td>
									<td>
										
										sdsadasd
										<div>
											<b>Complant text: </b>{" "}
											{complaint.EntityDTO.text}
										</div>
										<div>
											<b>Complant reply: </b>{" "}
											{complaint.EntityDTO.reply}
										</div>
									</td>
									<td className="align-middle">
										<button
											type="button"
											className="btn btn-outline-secondary"
										>
											Reply to complaint
										</button>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>

				<ComplaintCreateModal
					buttonName="Reply to complaint"
					header="Reply to complaint"
					handleComplaintChange={this.handleComplaintChange}
					show={this.state.showFeedbackModal}
					onCloseModal={this.handleFeedbackModalClose}
					giveFeedback={this.handleComaplaint}
					name={this.state.StaffName + " " + this.state.StaffSurame}
					forWho="dermatologist"
					handleClickIcon={this.handleClickIcon}
					complaint={this.state.complaint}
				/>
			</React.Fragment>
		);
	}
}

export default AdminComplaints;
