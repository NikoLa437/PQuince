import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import CapsuleLogo from '../static/capsuleLogo.png';

class TherapyDrugModal extends Component {
    state = {
        numberOfDays:1,
    }

    handlenumberOfDaysChange = (event) => {
        this.setState({numberOfDays: event.target.value});
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
                    <form className="ml-3">
                            <div className="control-group">
                                    <div className="form-group controls" style={{color: "#6c757d",opacity: 1}}>
                                        <div className="form-row" width="130em">                        
                                            <div className="form-col" >
                                                <img className="img-fluid" src={CapsuleLogo} width="90em"/>
                                            </div>
                                            <div className="form-col">
                                                <div><b>Name:</b> {this.props.name}</div>
                                                <div><b>Manufacturer:</b> {this.props.manufacturer}</div>
                                                <div><b>Quantity:</b> {this.props.quantity} <b>mg</b></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="form-col">
                                                <label>Number of days:</label>
                                                <input placeholder="Drug amount" className="form-control mr-3" style={{width: "9em"}} type="number" min="1" max={this.props.maxnumberOfDays} onChange={this.handlenumberOfDaysChange} value={this.state.numberOfDays} />
                                        </div>
                                    </div>
                                    <div  className="form-group text-center">
                                        <Button className="mt-3"  onClick = {() => this.props.handleAddDrug(this.state.numberOfDays, this.state.selectedDate)} >Prescribe drug</Button>
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
 
export default TherapyDrugModal;