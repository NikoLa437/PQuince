import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import CapsuleLogo from '../static/capsuleLogo.png';
import DatePicker from "react-datepicker";

import Axios from 'axios';
import {BASE_URL} from '../constants.js';

class DrugsInPharmacyModal extends Component {

    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/drug/find-drug-by-pharmacy?pharmacyId="+ this.props.pharmacyId).then((res) =>{
            this.setState({drugs : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
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
                    <Modal.Title style={{marginLeft:'35%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="container">                        
                            <table className="table" style={{width:"100%"}}>
                                <tbody>
                                    {this.props.drugs.map(drug => 
                                            <tr hidden={false} id={drug.Id} key={drug.Id} >
                                                    <td width="130em">
                                                        <img className="img-fluid" src={CapsuleLogo} width="70em"/>
                                                    </td>
                                                    <td>
                                                        <div><b>Name:</b> {drug.EntityDTO.drugInstanceName}</div>
                                                        <div><b>Manufacturer:</b> {drug.EntityDTO.manufacturer.EntityDTO.name}</div>
                                                        <div><b>Quantity:</b> {drug.EntityDTO.quantity} <b>mg</b></div>
                                                    </td>
                                            </tr>
                                         )}
                                </tbody>
                            </table>
                    </div>

                    
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onCloseModal}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default DrugsInPharmacyModal;