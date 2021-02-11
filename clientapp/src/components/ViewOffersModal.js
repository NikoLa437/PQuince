import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import PharmacistLogo from '../static/pharmacistLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";
import getAuthHeader from "../GetHeader";
import OrderLogo from "../static/order.png";

class ViewOffersModal extends Component {
    state = {
    }

    componentDidMount() {
    }


    handleClickOnClose = () => {
        this.setState({
            showAddWorkTime: false, 
            modalSize:'lg',
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            timeFrom:1,
            timeTo:2,
        });
        this.props.onCloseModal();
    }

    onAcceptOffer = (id) => {
        let acceptOfferForOrderDTO = {
            offerId :id,
            orderId: this.props.orderId,
        };

        Axios
        .put(BASE_URL + "/api/offer/accept", acceptOfferForOrderDTO, {
            headers: { Authorization: getAuthHeader() },
        }).then((res) =>{
            this.props.acceptedOffer();
            console.log(res.data);
        }).catch((err) => {
        });
    }

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = "lg"
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'45%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <div className="container">      
                    <table hidden={this.state.showAddWorkTime} className="table" style={{ width: "100%", marginTop: "3rem" }}>
                        <tbody>
                            {this.props.offers.map((offer) => (
                                <tr id={offer.Id} key={offer.Id}>
                                    <td width="130em">
                                        <img
                                            className="img-fluid"
                                            src={OrderLogo}
                                            width="70em"
                                        />
                                    </td>
                                    <td>
                                        <div>
                                            <b>Date to delivery: </b> {new Date(offer.EntityDTO.dateToDelivery).toDateString()}
                                        </div>
                                        <div>
                                            <b>Price: </b> {offer.EntityDTO.price}
                                        </div>
                                    </td>
                                    <td >
                                        <div style={{marginLeft:'25%'}}>
                                            <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'12%'}} className="btn btn-primary btn-xl" onClick={() => this.onAcceptOffer(offer.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Accept offer</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                 </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default ViewOffersModal;