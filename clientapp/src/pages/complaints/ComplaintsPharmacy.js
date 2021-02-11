import React, { Component } from "react";
import AppointmentIcon from "../../static/complaint.png";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import { withRouter } from "react-router";
import ComplaintCreateModal from "../../components/ComplaintReplyModal";
import getAuthHeader from "../../GetHeader";
import { Redirect } from "react-router-dom";

class ComplaintsPharmacy extends Component {
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
        redirect: false,
        redirectUrl: '',
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/staff/complaint/pharmacy", { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				this.setState({ complaints: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});

		Axios.get(BASE_URL + "/api/users/sysadmin/auth", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
            .then((res) => {
				console.log(res.statusm, "TEST")
                if (res.status === 401) {
                    this.setState({
                        redirect: true,
                        redirectUrl: "/unauthorized"
                    });
                } else {
                    console.log(res.data);
                }
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
		Axios.post(BASE_URL + "/api/staff/complaint/reply-pharmacy", complaintReplyDTO, {
			headers: { Authorization: getAuthHeader() },
		})
			.then((resp) => {
				this.setState({ showFeedbackModal: false });
				Axios.get(BASE_URL + "/api/staff/complaint/pharmacy", { headers: { Authorization: getAuthHeader() } })
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
		this.setState({
			complaintId: complaint.Id,
			StaffName: complaint.EntityDTO.name,
			showFeedbackModal: true,
		})
	};
	

	render() {
		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Pharmacy complaints </h5>

					<table className="table" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.complaints.map((complaint) => (
								<tr id={complaint.Id} key={complaint.Id} className="rounded">
									<td width="190em">
										<img className="img-fluid" src={AppointmentIcon} width="150em" />
									</td>
									<td>
										<div>
											<b>Pharmacy name: </b>{" "}
											{complaint.EntityDTO.name}
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
					forWho="pharmacy"
					handleClickIcon={this.handleClickIcon}
					complaint={this.state.complaint}
				/>
			</React.Fragment>
		);
	}
}

export default withRouter(ComplaintsPharmacy);
