import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import PharmacistLogo from '../static/pharmacistLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";

class PharmacyPharmacistsModal extends Component {
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
                                {this.props.pharmacists.map((pharmacist) => (
                                    <tr id={pharmacist.Id} key={pharmacist.Id}>
                                        <td width="130em">
                                            <img
                                                className="img-fluid"
                                                src={PharmacistLogo}
                                                width="70em"
                                            />
                                        </td>
                                        <td>
                                            <div>
                                                <b>Name: </b> {pharmacist.EntityDTO.name}
                                            </div>
                                            <div>
                                                <b>Surname: </b> {pharmacist.EntityDTO.surname}
                                            </div>
                                            <div>
                                                <b>Pharmacy: </b> {pharmacist.EntityDTO.pharmacyName}
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
 
export default PharmacyPharmacistsModal;