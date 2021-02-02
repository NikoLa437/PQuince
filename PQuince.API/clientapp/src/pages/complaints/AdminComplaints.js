import React, { Component } from "react";
import AppointmentIcon from "../../static/complaint.png";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import ComplaintCreateModal from "../../components/ComplaintReplyModal";
import getAuthHeader from "../../GetHeader";

class AdminComplaints extends Component {
	state = {
		complaints: [],
		selectedStaffId: "",
		StaffName: "",
		StaffSurame: "",
		complaint: "",
		complaintId: "",
		text: "",
		grade: 0,
		appointments: [],
		showingSorted: false,
		showFeedbackModal: false,
		showModifyFeedbackModal: false,
		showComplaintModal: false,
		selectedStaffId: "",
		StaffName: "",
		StaffSurame: "",
		grade: 0,
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/staff/complaint", { headers: { Authorization: getAuthHeader() } })
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
		let complaintReplyDTO = {
			id: this.state.complaintId,
			reply: this.state.complaint
		};
		console.log(complaintReplyDTO, "COMPL");
		Axios.post(BASE_URL + "/api/staff/complaint/reply", complaintReplyDTO, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((resp) => {
				this.setState({ showFeedbackModal: false });
				Axios.get(BASE_URL + "/api/staff/complaint", { headers: { Authorization: getAuthHeader() } })
					.then((res) => {
						this.setState({ complaints: res.data });
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
	
	handleComplaintClick = (complaint) => {
		console.log(complaint);
	
		Axios.get(BASE_URL + "/api/users/" + complaint.EntityDTO.staffId, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data, "RESPONSE");
				console.log(res.data.EntityDTO.name);
				if (res.status === 404) {
					this.setState({
						selectedStaffId: res.data.Id,
						complaintId: complaint.Id,
						showFeedbackModal: true,
						StaffName: res.data.EntityDTO.name,
						StaffSurame: res.data.EntityDTO.surname,
						grade: 0,
					});
				} else if (res.status === 200) {
					this.setState({
						selectedStaffId: res.data.Id,
						showFeedbackModal: true,
						complaintId: complaint.Id,
						StaffName: res.data.EntityDTO.name,
						StaffSurame: res.data.EntityDTO.surname,
						grade: res.data.grade,
					});
				}
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
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Patient complaints </h5>

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.complaints.map((complaint) => (
								<tr id={complaint.Id} key={complaint.Id} className="rounded">
									<td width="190em">
										<img className="img-fluid" src={AppointmentIcon} width="150em" />
									</td>
									<td>
										<div>
											<b>Staff name: </b>{" "}
											{complaint.EntityDTO.staffName}
										</div>
										<div>
											<b>Staff surname: </b>{" "}
											{complaint.EntityDTO.staffSurname}
										</div>
										<div>
											<b>Profession: </b>{" "}
											{complaint.EntityDTO.profession}
										</div>
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
											onClick={() => this.handleComplaintClick(complaint)}
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
