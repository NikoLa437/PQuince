import React, { Component } from "react";
import TopBar from "../../../components/TopBar";
import Header from "../../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../../constants.js";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import ModalDialog from "../../../components/ModalDialog";
import { Redirect } from "react-router-dom";
import getAuthHeader from "../../../GetHeader";
import { withRouter } from "react-router";

class NewAppointmentPage extends Component {
    state = {
        id: "",
        selectedDate: new Date(),
		hours: new Date().getHours(),
        minutes: new Date().getMinutes(),
        showDateError: "none",
        openModalSuccess: false,
        redirect: false,
        redirectUrl: ''
    };

    handleDateChange = (date) => {
		this.setState({ selectedDate: date });
	};

	handleMinutesChange = (event) => {
		if (event.target.value > 59) this.setState({ minutes: 59 });
		else if (event.target.value < 0) this.setState({ minutes: 0 });
		else this.setState({ minutes: event.target.value });
	};

	handleHoursChange = (event) => {
		if (event.target.value > 23) this.setState({ hours: 23 });
		else if (event.target.value < 0) this.setState({ hours: 0 });
		else this.setState({ hours: event.target.value });
    };
    
    handleCheckAvailability = () => {
        let today = new Date();
        let consultationDate = new Date(this.state.selectedDate.getFullYear(),this.state.selectedDate.getMonth(),this.state.selectedDate.getDate(),this.state.hours,this.state.minutes,0,0);
		this.setState({
			showDateError: "none",
        });
        if ( new Date(this.state.selectedDate.getFullYear(), this.state.selectedDate.getMonth(), this.state.selectedDate.getDate()).getTime() <
             new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime() 
        ) {
            console.log("selected date is before today");
            this.setState({ showDateError: "inline" });
        }
		else if (
			new Date(this.state.selectedDate.getFullYear(), this.state.selectedDate.getMonth(), this.state.selectedDate.getDate()).getTime() ===
			new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime()
		) {
			if (parseInt(this.state.hours) < today.getHours()) this.setState({ showDateError: "inline" });
			else if (parseInt(this.state.hours) === today.getHours() && parseInt(this.state.minutes) < today.getMinutes())
				this.setState({ showDateError: "inline" });
			else {
                let EntityDTO = {
                    patientId: this.state.id,
                    startDateTime: consultationDate
                };
				Axios.post(BASE_URL + "/api/appointment/pharmacist/new/", EntityDTO, {
                    validateStatus: () => true,
                    headers: { Authorization: getAuthHeader() },
                })
                    .then((res) => {
                        if (res.status === 400) {
                            this.setState({ showDateError: "inline" });
                        } else if (res.status === 500) {
                            this.setState({ showDateError: "inline" });
                        } else if (res.status === 201) {
                            this.setState({ openModalSuccess: true });
                            console.log(res);
                        }
                    })
                    .catch((err) => {
                        this.setState({ showDateError: "inline" });
                        console.log(err);
                    });
			}
		}
	};

    componentDidMount () {
        const id = this.props.match.params.id;
        this.setState({
			id: id
		});

        Axios.get(BASE_URL + "/api/users/pharmacist/auth", { validateStatus: () => true, headers: { Authorization: getAuthHeader() }})
            .then((res) => {
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

    handleModalSuccessClose = () => {
		this.setState({
			openModalSuccess: false,
			redirect: true,
			redirectUrl: "/patient-profile/" + this.state.id
		});
	};

    render() {

        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

        return (
            <React.Fragment>
				<TopBar />
				<Header />

                <div className="container" style={{ marginTop: "15%" }}>
					<h3 className="text-center mb-0">
						Select the perfect time for new consultation in just a few steps
					</h3>
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
								Can't schedule new appointment in selected date and time!
							</div>
						</div>
						<div className="form-row justify-content-center mt-4">
							<button className="btn btn-primary btn-lg" type="button" onClick={this.handleCheckAvailability}>
								Schedule new appointment
							</button>
						</div>
					</div>
				</div>

                <ModalDialog
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully created and scheduled appointment for patient"
					text="Start consultation for scheduled appointment."
				/>
            </React.Fragment>
        );
    }
}

export default withRouter(NewAppointmentPage);