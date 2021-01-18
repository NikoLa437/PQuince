import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import DermatologistLogo from '../static/dermatologistLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';

class PharmacyDermatologistModal extends Component {
    state = {
        dermatologist:''
    }

    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/pharmacy/get-dermatologist?pharmacyId=" + this.props.pharmacyId).then((response) =>{
            this.setState(
            {
                dermatologist : response.data,
            });          
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
                    <Modal.Title style={{marginLeft:'37%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="ml-3">
                            <div className="control-group">
                                    <div className="form-group controls" style={{color: "#6c757d",opacity: 1}}>
                                        <div className="form-row" width="130em">
                                            <div className="form-col" >
                                                <img className="img-fluid" src={DermatologistLogo} width="60em"/>
                                            </div>                        
                                            <div className="form-col" style={{marginLeft:'10%'}}>
                                                <div><b>Name:</b> Petar</div>
                                                <div><b>Surname:</b> Djuric</div>
                                                <div><b>Email:</b> petardjuric1998@hotmail.com</div>
                                            </div>
                                            <div className="form-col" style={{marginLeft:'15%'}}>
                                                <Button  className="mt-3">Create appointment</Button>
                                            </div>
                                        </div>
                                    </div>
                                
                                </div>
                   </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onCloseModal}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default PharmacyDermatologistModal;