import React, { Component } from "react";
import { Button, Modal } from 'react-bootstrap';
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import DatePicker from "react-datepicker";
import ModalDialog from "../../components/ModalDialog";
import getAuthHeader from "../../GetHeader";
import { Redirect } from "react-router-dom";
import WorkTimeIcon from "../../static/schedule.png";


class AbsencePage extends Component {
    state = {
        openSuccessModal: false,
        selectedStartDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()),
        selectedEndDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() + 1),
        redirect: false,
        redirectUrl: '',
        absences: [],
    };

    componentDidMount() {
        Axios.get(BASE_URL + "/api/absence", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
            .then((res) => {
                if (res.status === 401) {
                    this.setState({
                        redirect: true,
                        redirectUrl: "/unauthorized"
                    });
                } else {
                    this.setState({ absences: res.data });
                    console.log(res.data);
                }
            })
            .catch((err) => {
                console.log(err);
            });
    };

    handleSuccessModalClose = () => {
        this.setState({ openSuccessModal: false });
    };

    handleStartDateChange = (date) => {
        this.setState({
            selectedStartDate: date,
        });

        if (date > this.state.selectedEndDate) {
            let nextDay = new Date(date.getFullYear(), date.getMonth(), date.getDate() + 1);
            this.setState({
                selectedEndDate: nextDay,
            });
        }
    }

    handleEndDateChange = (date) => {
        this.setState({ selectedEndDate: date });
    }

    handleRequestAbsence = () => {
        Axios.post(BASE_URL + "/api/absence/request",
            { startDate: this.state.selectedStartDate, endDate: this.state.selectedEndDate },
            { headers: { Authorization: getAuthHeader() } })
            .then((res) => {
                this.setState({ openSuccessModal: true });
                console.log(res.data);
                window.location.reload();
            })
            .catch((err) => {
                console.log(err);
            });
    }

    handleAbsenceBackground = (absenceStatus) => {
        if (absenceStatus == "ACCEPTED")
            return "#93c2aa";
        else if (absenceStatus == "REJECTED")
            return "#f56056";
    };

    render() {

        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

        return (
            <React.Fragment>
                <TopBar />
                <Header />

                <div className="container" style={{ marginTop: "10%" }}>
                    <h3 className=" text-left mb-5 mt-5">Select date range to request absence</h3>
                    <form >
                        <div className="control-group" style={{ width: '50%' }}>
                            <table>
                                <tr>
                                    <td>
                                        <h5 className="m-1">Date from:</h5>
                                    </td>
                                    <td>
                                        <DatePicker className="form-control" minDate={new Date()} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate} />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h5 className="m-1">Date to:</h5>
                                    </td>
                                    <td>
                                        <DatePicker className="form-control" minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate} />
                                    </td>
                                </tr>
                                <tr>
                                    <Button style={{ width: '100%' }} className="mt-5" onClick={() => this.handleRequestAbsence()} >Request absence</Button>
                                </tr>
                            </table>
                        </div>
                    </form>
                </div>

                <table className="table mt-5 ml-5" style={{ width: "60%" }}>
                    <tbody>
                        {this.state.absences.map(absence =>
                            <tr hidden={false} id={absence.Id} key={absence.Id} style={{ background: this.handleAbsenceBackground(absence.EntityDTO.absenceStatus)}}>
                                <td width="130em">
                                    <img className="img-fluid" src={WorkTimeIcon} width="100em" />
                                </td>
                                <td >
                                    <div><b>Date from:</b> {new Date(absence.EntityDTO.startDate).toDateString()}</div>
                                    <div><b>Date to:</b> {new Date(absence.EntityDTO.endDate).toDateString()}</div>
                                    <div><b>Absence status:</b> {absence.EntityDTO.absenceStatus}</div>
                                    <div hidden={absence.EntityDTO.absenceStatus != "REJECTED"}><b>Reject reason:</b> {absence.EntityDTO.rejectReason}</div>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>


                <ModalDialog
                    show={this.state.openSuccessModal}
                    onCloseModal={this.handleSuccessModalClose}
                    header="Successfully requested absence"
                    text="Absence needs to be approved by pharmacy admin."
                />
            </React.Fragment>
        );
    }
}

export default AbsencePage;
