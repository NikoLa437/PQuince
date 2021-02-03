import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import { BASE_URL } from "../constants.js";
import Axios from "axios";
import getAuthHeader from "../GetHeader";
import { Calendar, momentLocalizer, Views } from 'react-big-calendar'
import moment from 'moment'
import 'react-big-calendar/lib/css/react-big-calendar.css';
import EventDetailsModal from "../components/EventDetailsModal";


const localizer = momentLocalizer(moment)

class DermatologistCalendarPage extends Component {
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
        pharmacy: {}
    };

    handleModalInfoClose = () => {
        this.setState({ openModalInfo: false });
    };

    handleEventClick = (appointment) => {
        let name = appointment.EntityDTO.patient == null ? "" : appointment.EntityDTO.patient.EntityDTO.name;
        let surname = appointment.EntityDTO.patient == null ? "" : appointment.EntityDTO.patient.EntityDTO.surname;

        this.setState({
            name: name,
            surname: surname,
            startDateTime: appointment.EntityDTO.startDateTime,
            endDateTime: appointment.EntityDTO.endDateTime,
            price: appointment.EntityDTO.price,
            openModalInfo: true,
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

    componentDidMount() {

        Axios.get(BASE_URL + "/api/users/dermatologist/pharmacies", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
            .then((res) => {
                this.setState({ pharmacies: res.data, pharmacy: res.data[0] });
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

            })
            .catch((err) => {
                console.log(err);
            });
    }

    handlePharmacyChange = (event) => {
        this.setState({ pharmacy: event.target.value });

        Axios.get(BASE_URL + "/api/appointment/dermatologist/calendar-for-pharmacy/" + this.state.pharmacy.Id, { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
            .then((res) => {
                this.setState({ appointments: res.data });
                console.log(res.data);
                this.mapAppointmentsToEvents();
            })
            .catch((err) => {
                console.log(err);
            });
    };

    /*eventStyleGetter = (event, start, end, isSelected) => {
        var backgroundColor = event.resource.EntityDTO.appointmentStatus === "SCHEDULED" ? "#3C00FF" : "#948a8a";
        var style = {
            backgroundColor: backgroundColor,
            borderRadius: '0px',
            opacity: 0.8,
            color: 'black',
            border: '0px',
            display: 'block'
        };
        return {
            style: style
        };
    }*/

    render() {
        return (
            <React.Fragment>
                <TopBar />
                <Header />
                <div className="container" style={{ marginTop: "10%" }}>
                    <h4 className="text-center mb-5 mt-2 text-uppercase">Calendar</h4>
                    <h5 className="text-left mt-2">Working calendar for selected pharmacy</h5>
                    <div className="mb-5 mt-2">
                        <select onChange={this.handlePharmacyChange} className="form-control" >
                            {this.state.pharmacies.map((pharmacy) => <option key={pharmacy.Id} value={pharmacy.Id}>Dr {pharmacy.EntityDTO.name}</option>)}
                        </select>
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
                    //eventPropGetter={(this.eventStyleGetter)}
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
                />
            </React.Fragment>
        );
    }
}

export default DermatologistCalendarPage;