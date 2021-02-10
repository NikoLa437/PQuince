import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import { Calendar, momentLocalizer, Views } from 'react-big-calendar'
import moment from 'moment'
import 'react-big-calendar/lib/css/react-big-calendar.css';
import EventDetailsModal from "../../components/EventDetailsModal";
import { Redirect } from "react-router-dom";
import ModalDialog from "../../components/ModalDialog";

const localizer = momentLocalizer(moment)

class CalendarPage extends Component {
    state = {
        pharmacies: [],
        events: [],
        appointments: [],
        openModalInfo: false,
        name: "",
        surname: "",
        startDateTime: "",
        endDateTime: "",
        price: "",
        pharmacy: {},
        pharmacyName : "",
        redirect: false,
        redirectUrl: '',
        id: '',
        openModalSuccess: false,
        appointmentStatus: ""
    };

    hasRole = (reqRole) => {
		let roles = JSON.parse(localStorage.getItem("keyRole"));

		if (roles === null) return false;

		if (reqRole === "*") return true;

		for (let role of roles) {
			if (role === reqRole) return true;
		}
		return false;
	};

    handleModalInfoClose = () => {
        this.setState({ openModalInfo: false });
    };

    handleEventClick = (appointment) => {
        console.log(appointment);
        let name = appointment.EntityDTO.patient == undefined || null ? "" : appointment.EntityDTO.patient.EntityDTO.name;
        let surname = appointment.EntityDTO.patient == undefined || null ? "" : appointment.EntityDTO.patient.EntityDTO.surname;

        this.setState({
            name: name,
            surname: surname,
            startDateTime: appointment.EntityDTO.startDateTime,
            endDateTime: appointment.EntityDTO.endDateTime,
            price: appointment.EntityDTO.price,
            openModalInfo: true,
            id: appointment.Id,
            appointmentStatus: appointment.EntityDTO.appointmentStatus
        });
    };

    generateEventTitle = (appointment) => {
        let name = appointment.EntityDTO.patient == null ? "" : appointment.EntityDTO.patient.EntityDTO.name;
        let surname = appointment.EntityDTO.patient == null ? "" : appointment.EntityDTO.patient.EntityDTO.surname;
        if (name === "" && surname === "")
            return "Free appointment"
        else
            return name + " " + surname;
    };

    mapAppointmentsToEvents = () => {
        this.setState({ events: this.state.appointments.map(appointment => ({ start: new Date(appointment.EntityDTO.startDateTime), end: new Date(appointment.EntityDTO.endDateTime), title: this.generateEventTitle(appointment), allDay: false, resource: appointment })) });
        console.log(this.state.events);
    };

    fetchDermatologistCalendar = () => {
        Axios.get(BASE_URL + "/api/users/dermatologist/pharmacies", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
        .then((res) => {

            if (res.status === 401) {
                this.setState({
                    redirect: true,
                    redirectUrl: "/unauthorized"
                });
            } else {

                this.setState({ pharmacies: res.data, pharmacy: res.data[0], pharmacyName: res.data[0].EntityDTO.name });
                console.log(res.data);

                Axios.get(BASE_URL + "/api/appointment/dermatologist/calendar-for-pharmacy/" + this.state.pharmacy.Id, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
                    .then((res) => {
                        this.setState({ appointments: res.data });
                        console.log(res.data);
                        this.mapAppointmentsToEvents();
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            }
        })
        .catch((err) => {
            console.log(err);
        });
    };

    fetchFarmacistCalendar = () => {
        Axios.get(BASE_URL + "/api/users/pharmacist/pharmacy", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
        .then((res) => {

            if (res.status === 401) {
                this.setState({
                    redirect: true,
                    redirectUrl: "/unauthorized"
                });
            } else {

                this.setState({ pharmacy: res.data, pharmacyName: res.data.EntityDTO.name });
                console.log(res.data);

                Axios.get(BASE_URL + "/api/appointment/pharmacist/calendar/" + this.state.pharmacy.Id, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
                    .then((res) => {
                        this.setState({ appointments: res.data });
                        console.log(res.data);
                        this.mapAppointmentsToEvents();
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            }
        })
        .catch((err) => {
            console.log(err);
        });
    };

    componentDidMount() {
        if(this.hasRole("ROLE_DERMATHOLOGIST"))
            this.fetchDermatologistCalendar();
        else
            this.fetchFarmacistCalendar();
    }

    handlePharmacyChange = (event) => {
        this.setState({ pharmacy: event.target.value }, () => {
            Axios.get(BASE_URL + "/api/appointment/dermatologist/calendar-for-pharmacy/" + this.state.pharmacy, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
            .then((res) => {
                this.setState({ appointments: res.data });
                console.log(res.data);
                this.mapAppointmentsToEvents();
            })
            .catch((err) => {
                console.log(err);
            });
        });
    };

    handleExamine = () => {
		this.setState({
			redirect: true,
			redirectUrl: "/treatment-report/" + this.state.id
		});
	};

	handleDidNotShowUp = () => {
		Axios.put(BASE_URL + "/api/appointment/did-not-show-up",
			{ id: this.state.id },
			{ headers: { Authorization: getAuthHeader() } })
			.then((res) => {
                this.handleModalInfoClose();
				if(this.hasRole("ROLE_DERMATHOLOGIST"))
                    this.fetchDermatologistCalendar();
                else
                    this.fetchFarmacistCalendar();
                console.log(res.data);
                this.setState({ openModalSuccess: true});
			})
			.catch((err) => {
				console.log(err);
			});
    };
    
    handleModalSuccessClose = () => {
		this.setState({
			openModalSuccess: false,
		});
	};

    render() {

        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

        return (
            <React.Fragment>
                <TopBar />
                <Header />
                <div className="container" style={{ marginTop: "10%" }}>
                    <h4 className="text-center mb-5 mt-2 text-uppercase">Calendar</h4>
                    <div hidden={!this.hasRole("ROLE_DERMATHOLOGIST")}>
                        <h5 className="text-left mt-2">Working calendar for selected pharmacy</h5>
                        <div className="mb-5 mt-2">
                            <select onChange={this.handlePharmacyChange} className="form-control" >
                                {this.state.pharmacies.map((pharmacy) => <option key={pharmacy.Id} value={pharmacy.Id}>Dr {pharmacy.EntityDTO.name}</option>)}
                            </select>
                        </div>
                    </div>
                    <div hidden={!this.hasRole("ROLE_PHARMACIST")}>
                    <h5 className="text-left mb-5 mt-2">{"Working calendar for " + this.state.pharmacyName + " pharmacy"}</h5>
                    </div>
                    
                    <Calendar
                        selectable
                        onSelectEvent={event => this.handleEventClick(event.resource)}
                        popup
                        defaultView={Views.DAY}
                        localizer={localizer}
                        events={this.state.events}
                        startAccessor="start"
                        endAccessor="end"
                        style={{ height: 500 }}
                    />
                </div>

                <EventDetailsModal
                    header="Appointment information"
                    show={this.state.openModalInfo}
                    onCloseModal={this.handleModalInfoClose}
                    name={this.state.name}
                    surname={this.state.surname}
                    startDateTime={this.state.startDateTime}
                    endDateTime={this.state.endDateTime}
                    price={this.state.price}
                    handleExamine={this.handleExamine}
                    handleDidNotShowUp={this.handleDidNotShowUp}
                    appointmentStatus={this.state.appointmentStatus}
                />
                <ModalDialog
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully added penalty to patient"
					text="You can start examination for another patient."
				/>
            </React.Fragment>
        );
    }
}

export default CalendarPage;