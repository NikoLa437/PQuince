import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";


class CreateAppointmentForDermatologistModal extends Component {
    state = {
        selectedDate:new Date(),
    }

    handleDateChange = (date) => {
        this.setState({selectedDate:date});
    }

    handleAdd = () => {
        let workTimeDTO = {
            forPharmacy : this.props.forPharmacy,
            forStaff: this.props.forStaff, 
            pharmacyName: '', 
            startDate: this.state.selectedStartDate, 
            endDate:this.state.selectedEndDate,
            startTime: this.state.timeFrom, 
            endTime:this.state.timeTo
        };

        Axios
        .post(BASE_URL + "/api/worktime/", workTimeDTO).then((res) =>{
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
                    <Modal.Title style={{marginLeft:'37%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>

                </Modal.Header>
                <Modal.Body>
                    <div >
                        <form >
                            <div style={{width:'100%'}} className="control-group">
                            <div >                        
                                    <div className="form-col" style={{color: "#6c757d",opacity: 1}}>
                                        <label style={{marginRight:'2%'}}>Dermatologist:</label>
                                        <DatePicker className="form-control mr-3"  minDate={new Date()} onChange={date => this.handleDateChange(date)} selected={this.state.selectedStartDate}/>
                                    </div>
                                </div>
                                <div >                        
                                    <div className="form-col" style={{color: "#6c757d",opacity: 1}}>
                                        <label style={{marginRight:'2%'}}>Date from:</label>
                                        <DatePicker className="form-control mr-3"  minDate={new Date()} onChange={date => this.handleDateChange(date)} selected={this.state.selectedStartDate}/>
                                    </div>
                                </div>
                                <div >                        
                                    <div className="form-col" style={{color: "#6c757d",opacity: 1}}>
                                        <label style={{marginRight:'2%'}}>Date from:</label>
                                        <DatePicker className="form-control mr-3"  minDate={new Date()} onChange={date => this.handleDateChange(date)} selected={this.state.selectedStartDate}/>
                                    </div>
                                </div>
                                    
                                <div  className="form-group text-center">
                                    <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add appointment</Button>
                                </div>
                            </div>
                        </form>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onCloseModal}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default CreateAppointmentForDermatologistModal;