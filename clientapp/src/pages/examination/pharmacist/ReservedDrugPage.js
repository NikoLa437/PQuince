import React, { Component } from "react";
import TopBar from "../../../components/TopBar";
import Header from "../../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../../constants.js";
import "react-datepicker/dist/react-datepicker.css";
import ModalDialog from "../../../components/ModalDialog";
import { Redirect } from "react-router-dom";
import getAuthHeader from "../../../GetHeader";
import CapsuleLogo from "../../../static/capsuleLogo.png";

class ReservedDrugPage extends Component {
    state = {
        openModalSuccess: false,
        redirect: false,
        redirectUrl: '',
        showError: "none",
        reservationCode: "",
        drugName: "",
        drugManufacturer: "",
        drugQuantity: "",
        drugPrice: "",
        endDate: "",
        drugAmount: "",
        reservationStatus: "",
        displayDrug: "none",
        id: ""
    };

    componentDidMount() {
        Axios.get(BASE_URL + "/api/users/pharmacist/auth", { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
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

    handleSearch = () => {
        this.setState({
            showError: 'none'
        });
        Axios.get(BASE_URL + "/api/drug/reservation/" + this.state.reservationCode,
            { validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
            .then((res) => {
                if (res.status === 400) {
                    this.setState({
                        showError: 'inline'
                    });
                } else if (res.status === 200) {
                    this.setState({
                        drugName: res.data.EntityDTO.drugInstance.EntityDTO.drugInstanceName,
                        drugManufacturer: res.data.EntityDTO.drugInstance.EntityDTO.manufacturer.EntityDTO.name,
                        drugQuantity: res.data.EntityDTO.drugInstance.EntityDTO.quantity,
                        drugPrice: res.data.EntityDTO.drugPeacePrice,
                        endDate: res.data.EntityDTO.endDate,
                        drugAmount: res.data.EntityDTO.amount,
                        reservationStatus: res.data.EntityDTO.reservationStatus,
                        displayDrug: 'inline',
                        id: res.data.Id
                    });
                    console.log(this.state)
                    console.log(res.data);
                }
            })
            .catch((err) => {
                console.log(err);
            });
    };

    handleProcessReservation = () => {
        Axios.put(BASE_URL + "/api/drug/process-reservation",
			{ id: this.state.id },
			{ validateStatus: () => true, headers: { Authorization: getAuthHeader() } })
			.then((res) => {
                if (res.status === 200)
				    this.setState({ openModalSuccess: true});
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
    };

    handleReservationCode = (event) => {
        this.setState({ reservationCode: event.target.value });
    };

    handleModalSuccessClose = () => {
        this.setState({
            openModalSuccess: false
        });
        window.location.reload();
    };

    render() {

        if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

        return (
            <React.Fragment>
                <TopBar />
                <Header />

                <div className="container" style={{ marginTop: "10%", width: "50%" }}>
                    <h3 className="text-center">
                        Search for drug reservations
					</h3>

                    <div className="mt-5">
                        <h5>Enter drug reservation code:</h5>
                        <input
                            className="form-control"
                            type="text"
                            onChange={this.handleReservationCode}
                            value={this.state.reservationCode}
                        />
                        <h5 className="text-danger m-1" style={{ display: this.state.showError }}>Active reservation not found for input</h5>
                        <div>
                        <button type="button" className="btn btn-primary mt-5" onClick={this.handleSearch}>
                            <i className="icofont-search mr-1"></i>
                            Search for drug reservation
                        </button>
                        </div>
                    </div>
                    <form className="ml-3 mt-5" style={{ display: this.state.displayDrug }}>
                        <div className="control-group">
                            <div className="form-group controls">
                                <div className="form-row" width="130em">
                                    <div className="form-col">
                                        <img className="img-fluid" src={CapsuleLogo} width="90em" />
                                    </div>
                                    <div className="form-col ml-3">
                                        <div>
                                            <b>Name: </b> {this.state.drugName}
                                        </div>
                                        <div>
                                            <b>Manufacturer: </b> {this.state.drugManufacturer}
                                        </div>
                                        <div>
                                            <b>Quantity: </b> {this.state.drugQuantity} <b>mg</b>
                                        </div>
                                        <div>
                                            <b>Price by peace: </b> {(Math.round(this.state.drugPrice * 100) / 100).toFixed(2)} <b>din</b>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br />
                            <div className="form-row">
                                <div className="form-col">
                                    <div>
                                        <b>Reserved until: </b>{" "}
                                        {new Date(this.state.endDate).toLocaleDateString("en-US", {
                                            day: "2-digit",
                                            month: "2-digit",
                                            year: "numeric",
                                            hour: "2-digit",
                                            minute: "2-digit",
                                        })}{" "}
                                    </div>
                                </div>
                            </div>
                            <div className="form-row">
                                <div className="form-col">
                                    <div>
                                        <b>Drug amount: </b> {this.state.drugAmount}
                                    </div>
                                </div>
                            </div>
                            <div className="form-row">
                                <div className="form-col">
                                    <div>
                                        <b>Total price: </b> {(Math.round(this.state.drugAmount * this.state.drugPrice * 100) / 100).toFixed(2)}{" "}
                                        <b>din</b>
                                    </div>
                                </div>
                            </div>
                            <div className="form-row">
                                <div className="form-col">
                                    <div>
                                        <b>Reservation status: </b> {this.state.reservationStatus}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button type="button" className="btn btn-primary mt-5" onClick={this.handleProcessReservation}>
                                    Process reservation
                            </button>
                        </div>
                    </form>
                </div>
                
                <ModalDialog
                    show={this.state.openModalSuccess}
                    onCloseModal={this.handleModalSuccessClose}
                    header="Successfully issued drug reservation"
                    text="Search for another one."
                />
            </React.Fragment>
        );
    }
}

export default ReservedDrugPage;