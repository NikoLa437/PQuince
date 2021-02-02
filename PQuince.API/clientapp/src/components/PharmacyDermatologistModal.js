import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import DermatologistLogo from '../static/dermatologistLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";

class PharmacyDermatologistModal extends Component {
    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = "lg"
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                onHide={this.props.onCloseModal}
                >
                <Modal.Header closeButton >
                    <Modal.Title style={{marginLeft:'37%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <table className="table" style={{ width: "100%", marginTop: "3rem" }}>
                            <tbody>
                                {this.props.dermatologists.map((dermatologist) => (
                                    <tr id={dermatologist.Id} key={dermatologist.Id}>
                                        <td width="130em">
                                            <img
                                                className="img-fluid"
                                                src={DermatologistLogo}
                                                width="70em"
                                            />
                                        </td>
                                        <td>
                                            <div>
                                                <b>Name: </b> {dermatologist.EntityDTO.name}
                                            </div>
                                            <div>
                                                <b>Surname: </b> {dermatologist.EntityDTO.surname}
                                            </div>
                                            <div>
                                                <b>Email: </b> {dermatologist.EntityDTO.email}
                                            </div>
                                            <div>
                                                <b>Phone number: </b> {dermatologist.EntityDTO.phoneNumber}
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onCloseModal}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default PharmacyDermatologistModal;