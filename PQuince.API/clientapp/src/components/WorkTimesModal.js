import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import DermatologistLogo from '../static/dermatologistLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";


class WorkTimesModal extends Component {
    state = {
        workTimes:[],
        showAddWorkTime:false,
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        timeFrom:1,
        timeTo:1,
        modalSize:'lg',        
    }

    handleBack = (event) =>{
        this.setState({showAddWorkTime: false, modalSize:'lg'});
    }

    handleAddFeedbackModal = (event) =>{
        this.setState({showAddWorkTime: true, modalSize:'sm'});
    }

    handleStartDateChange = (date) => {
        this.setState({selectedStartDate:date});
    }


    handleEndDateChange = (date) => {
        this.setState({selectedEndDate:date});
    }

    handleTimeFromChange= (event) => {
        if(event.target.value > 24){
            this.setState({timeFrom:24});
        }else if(event.target.value < 1){
            this.setState({timeFrom:1});
        }
        else{
            this.setState({timeFrom:event.target.value});
        }
    }

    handleTimeToChange = (event) => {
            if(event.target.value > 24){
                this.setState({timeTo:24});
            }else if(event.target.value < 1){
                this.setState({timeTo:1});
            }
            else{
                this.setState({timeTo:event.target.value});
            } 
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
            this.setState({showAddWorkTime: false, modalSize:'lg'});
        }).catch((err) => {console.log(err);});
    }

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = {this.state.modalSize}
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
                    <div hidden={this.state.showAddWorkTime}>
                        <Button style={{marginBottom:'3%'}} onClick = {() => this.handleAddFeedbackModal()}>Add worktime</Button>

                            <table  border='1' style={{width:'100%'}}>
                                <tr>
                                    <th>Pharmacy</th>
                                    <th>StartDate</th>
                                    <th>EndDate</th>
                                    <th>StartTime</th>
                                    <th>EndTime</th>
                                </tr>
                                {this.props.workTimesForStaff.map((workTime) => (
                                
                                <tr>
                                    <td>{workTime.EntityDTO.pharmacyName}</td>
                                    <td>{new Date(workTime.EntityDTO.startDate).toDateString()}</td>
                                    <td>{new Date(workTime.EntityDTO.endDate).toDateString()}</td>
                                    <td>{workTime.EntityDTO.startTime}</td>
                                    <td>{workTime.EntityDTO.endTime}</td>
                                </tr>
                            ))}
                            </table>
                        </div>
                    
                    
                    <div hidden={!this.state.showAddWorkTime}>
                    <form >
                                    <div  className="control-group">
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link btn-xl" type="button">
                                                <i className="icofont-rounded-left mr-1"></i>
                                                Back
                                            </button>                   
                                        </div>
                                        <div >                        
                                            <div className="form-col" style={{color: "#6c757d",opacity: 1}}>
                                                <label style={{marginRight:'2%'}}>Date from:</label>
                                                <DatePicker className="form-control mr-3"  minDate={new Date()} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                            </div>
                                        </div>
                                        <div>                        
                                            <div className="form-col" style={{color: "#6c757d",opacity: 1}}>
                                                <label style={{marginRight:'2%'}}>Date to:</label>
                                                <DatePicker style={{marginLeft:'15px'}} className="form-control mr-3"  minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                            </div>
                                        </div>
                                        <div >
                                            <div className="form-col">
                                                <label >Time from:</label>
                                                 <input placeholder="Time from" className="form-control mr-3" style={{width: "9em"}} type="number" min="1" max="24" onChange={this.handleTimeFromChange} value={this.state.timeFrom} />
                                            </div>
                                        </div>
                                        <div>
                                            <div className="form-col">
                                                <label style={{marginRight:'2%'}}>Time to:</label>
                                                <input placeholder="Time to" className="form-control mr-3" style={{width: "9em"}} type="number" min="1" max="24" onChange={this.handleTimeToChange} value={this.state.timeTo} />
                                            </div>
                                        </div>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add worktime</Button>
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
 
export default WorkTimesModal;