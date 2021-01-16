import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import CapsuleLogo from '../static/capsuleLogo.png';
import DatePicker from "react-datepicker";

class DrugReservationModal extends Component {
    state = {
        allAllergens:[],
    }

    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/allergens").then((res) =>{
            this.setState({allAllergens : res.data});
        }).catch((err) => {console.log(err);});
    }

    doesUserContains = (allergenId) => {
        console.log(this.props.userAllergens);
        for(let allergen of this.props.userAllergens){
            if(allergen.Id === allergenId)
                return true;
        }
        return false;
    }

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                onHide={this.props.onCloseModal}
                >
                <Modal.Header closeButton >
                    <Modal.Title id="contained-modal-title-vcenter">
                    {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="ml-3 mb-4">
                            <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <div className="form-row mt-4" width="130em">                        
                                            <div className="form-col" >
                                                <img className="img-fluid" src={CapsuleLogo} width="70em"/>
                                            </div>
                                            <div className="form-col">
                                                <div><b>Name:</b> {this.props.drugName}</div>
                                                <div><b>Manufacturer:</b> {this.props.drugManufacturer}</div>
                                                <div><b>Quantity:</b> {this.props.drugQuantity} <b>mg</b></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form-row">                        
                                            <div className="form-col" style={{color: "#6c757d",opacity: 1}}>
                                                <label>Date of drug pickup:</label>
                                                <DatePicker />
                                            </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="form-col">
                                                <label>Drug amount:</label>
                                                <input placeholder="Drug amount" class="form-control mr-3" style={{width: "9em"}} type="number" min="1" max="5" onChange={this.handleGradeFromChange} value={this.state.gradeFrom} />
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
 
export default DrugReservationModal;