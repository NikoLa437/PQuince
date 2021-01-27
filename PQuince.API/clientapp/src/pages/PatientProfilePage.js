import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { BASE_URL } from "../constants.js";
import Axios from "axios";
import PatientLogo from "../static/patientLogo.png";
import AppointmentIcon from "../static/appointment-icon.jpg";


class PatientProfilePage extends Component {
	state = {
		id: "",
		email: "",
		password: "",
		name: "",
		surname: "",
		address: "",
		phoneNumber: "",
		appointments: []
	};

	componentDidMount() {
		Axios.get(BASE_URL + "/api/users/patient/22793162-52d3-11eb-ae93-0242ac130002")
			.then((res) => {
				console.log(res.data);
				this.setState({
					id: res.data.Id,
					email: res.data.EntityDTO.email,
					name: res.data.EntityDTO.name,
					surname: res.data.EntityDTO.surname,
					address: res.data.EntityDTO.address,
					phoneNumber: res.data.EntityDTO.phoneNumber
                });
                console.log(this.state)
			})
			.catch((err) => {
				console.log(err);
            });

        Axios.get(BASE_URL + "/api/appointment/patient/22793162-52d3-11eb-ae93-0242ac130002")
        .then((res) => {
            this.setState({ appointments: res.data });
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        });
    }
    
    handleExamine = (appointmentId) => {
		console.log(appointmentId)
    };

    handleNotShowUp = (appointmentId) => {
		console.log(appointmentId)
    };    
    
    handleSchedule = () => {

    };

	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "8%" }}>
					<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
						Patient information
					</h5>

					<div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage">	
                                <img
                                    style={{float: "left"}}
									className="img-fluid"
									src={PatientLogo}
									width="150em"
								/>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Name: </b> {this.state.name}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Surname: </b> {this.state.surname}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Email address: </b> {this.state.email}
										</div>
									</div>
								</div>
								<div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Phone number: </b> {this.state.phoneNumber}
										</div>
									</div>
								</div>
                                <div className="control-group">
									<div
										className="form-group controls mb-0 pb-2"
										style={{ color: "#6c757d", opacity: 1 }}
									>
                                        <div>
											<b>Address: </b> {this.state.address.country + " " + this.state.address.city + " " + this.state.address.street}
										</div>
									</div>
								</div>
                                <br/>
                                <button
                                    type="button"
                                    style={{width: "80%"}}
									onClick={() =>
										this.handleSchedule()
									}
									className="btn btn-primary"
								>
							    	Schedule appointment
								</button>
								
							</form>
						</div>

                    <table className="table" style={{ width: "70%", marginTop: "3rem" }}>
                    <tbody>
                        {this.state.appointments.map((appointment) => (
                            <tr
                                id={appointment.Id}
                                key={appointment.Id}
                                className="rounded"
                                style={{ background: appointment.EntityDTO.appointmentStatus == "SCHEDULED" && appointment.EntityDTO.staff.Id == "25345678-52d3-11eb-ae93-0242ac130002" ? "#93c2aa" : "" }}
                            >
                                <td
                                    width="190em"
                                >
                                    <img
                                        className="img-fluid"
                                        src={AppointmentIcon}
                                        width="100em"
                                    />
                                </td>
                                <td>
                                    <div>
                                        <b>Date: </b>{" "}
                                        {new Date(
                                            appointment.EntityDTO.startDateTime
                                        ).toLocaleDateString("en-US", {
                                            day: "2-digit",
                                            month: "2-digit",
                                            year: "numeric",
                                        })}
                                    </div>
                                    <div>
                                        <b>Time from: </b>{" "}
                                        {new Date(
                                            appointment.EntityDTO.startDateTime
                                        ).toLocaleTimeString("en-US", {
                                            hour: "2-digit",
                                            minute: "2-digit",
                                        })}
                                    </div>
                                    <div>
                                        <b>Time to: </b>{" "}
                                        {new Date(
                                            appointment.EntityDTO.endDateTime
                                        ).toLocaleTimeString("en-US", {
                                            hour: "2-digit",
                                            minute: "2-digit",
                                        })}
                                    </div>
                                    <div>
                                        <b>Dermatologist: </b>{" "}
                                        {appointment.EntityDTO.staff.EntityDTO.name +
                                            " " + appointment.EntityDTO.staff.EntityDTO.surname}
                                    </div>
                                </td>
                                <td className="align-right" style={{ width: "35%"}}>
										<button
                                            type="button"
                                            style={{ width: "60%", float: "right"}}
											hidden={
												appointment.EntityDTO.appointmentStatus != "SCHEDULED" || appointment.EntityDTO.staff.Id != "25345678-52d3-11eb-ae93-0242ac130002"
											}
											onClick={() =>
												this.handleExamine(appointment.Id)
											}
											className="btn btn-primary"
										>
											Examine
										</button>
                                        <br/>
                                        <br/>
                                        <button
                                            type="button"
                                            style={{ width: "60%", float: "right", verticalAlign: "bottom"}}
											hidden={
												appointment.EntityDTO.appointmentStatus != "SCHEDULED" || appointment.EntityDTO.staff.Id != "25345678-52d3-11eb-ae93-0242ac130002"
											}
											onClick={() =>
												this.handleNotShowUp(appointment.Id)
											}
											className="btn btn-danger"
										>
											Did not show up
										</button>
									</td>
                            </tr>
                        ))}
                    </tbody>
                </table>

					</div>
				</div>
			</React.Fragment>
		);
	}
}

export default PatientProfilePage;
