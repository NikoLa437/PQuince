import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import PharmacyLogo from "../static/pharmacyLogo.png";


class PharmaciesForDermatologistModal extends Component {
    state = {
        pharmacies:[]
    }

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
							{this.props.pharmacies.map((pharmacy) => (
								<tr id={pharmacy.Id} key={pharmacy.Id} onClick={this.handle}>
									<td width="130em">
										<img
											className="img-fluid"
											src={PharmacyLogo}
											width="70em"
										/>
									</td>
									<td>
										<div>
											<b>Name: </b> {pharmacy.EntityDTO.name}
										</div>
										<div>
											<b>Address: </b> {pharmacy.EntityDTO.address.street},{" "}
											{pharmacy.EntityDTO.address.city},{" "}
											{pharmacy.EntityDTO.address.country}
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
 
export default PharmaciesForDermatologistModal;