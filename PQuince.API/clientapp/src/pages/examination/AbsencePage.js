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

class AbsencePage extends Component {
    state = {
        openSuccessModal: false,
        selectedStartDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()),
        selectedEndDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() + 1),
        redirect: false,
        redirectUrl: ''
    };

    componentDidMount() {
        Axios.get(BASE_URL + "/api/absence/auth", { validateStatus: () => true,headers: { Authorization: getAuthHeader() } })
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
                this.setState({openSuccessModal: true});
                console.log(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }

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
