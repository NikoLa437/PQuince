import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import PharmacistLogo from '../static/pharmacistLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";
import getAuthHeader from "../GetHeader";

class AddPharmacistToPharmacy extends Component {
    state = {
        pharmacists:[],
        showAddWorkTime:false,
        pharmacistIdToAdd:'',
        modalSize:'lg',    
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        timeFrom:1,
        timeTo:2,    
        pharmacyId:''
    }

    componentDidMount() {
    }

    onAddClick = (id) =>{
        this.setState({
            showAddWorkTime: true,
            pharmacistIdToAdd:id,
            modalSize:'md'
        });
    }

    handleAdd = () => {
        let addPharmacistToPharmacyDTO = {
            pharmacyId : this.props.pharmacyId,
            pharmacistId: this.state.pharmacistIdToAdd, 
            startDate: this.state.selectedStartDate, 
            endDate:this.state.selectedEndDate,
            startTime: this.state.timeFrom, 
            endTime:this.state.timeTo
        };

        Axios
        .put(BASE_URL + "/api/users/add-pharmacist-to-pharmacy", addPharmacistToPharmacyDTO, {
            headers: { Authorization: getAuthHeader() },
        }).then((res) =>{
            console.log(res.data);
            this.props.updatePharmacist();
            this.setState({showAddWorkTime: false, modalSize:'lg'});
            alert("Uspesno dodat dermatolog u apoteku")
            this.handleClickOnClose();
        }).catch((err) => {
            alert('Nije moguce dodati dermatologa');
        });
    }

    handleBack = (event) =>{
        this.setState({showAddWorkTime: false,modalSize:'lg'});
    }

    handleStartDateChange = (date) => {
        this.setState({
            selectedStartDate:date,
        });

        if(date>this.state.selectedEndDate){
            this.setState({
                selectedEndDate:date,
            }); 
        }
    }


    handleEndDateChange = (date) => {
        this.setState({selectedEndDate:date});
    }

    handleTimeFromChange= (event) => {
        if(event.target.value > 23){
            this.setState({timeFrom:23});
        }else if(event.target.value < 1){
            this.setState({timeFrom:1});
        }
        
        if(event.target.value >= this.state.timeTo){
            this.setState({
                timeFrom:event.target.value,
                timeTo: event.target.value++
            });
        }else{
            this.setState({timeFrom:event.target.value});
        }
    }

    handleTimeToChange = (event) => {
            if(event.target.value > 24){
                this.setState({timeTo:24});
            }else if(event.target.value < 2){
                this.setState({timeTo:2});
            }
            
            if(event.target.value <= this.state.timeFrom){
                this.setState({
                    timeTo:event.target.value,
                    timeFrom: event.target.value--
                });
            }else{
                this.setState({timeTo:event.target.value});
            }
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

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = {this.state.modalSize}
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'37%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <div className="container">      
                    <table hidden={this.state.showAddWorkTime} className="table" style={{ width: "100%", marginTop: "3rem" }}>
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
                                    </td>
                                    <td >
                                        <div style={{marginLeft:'25%'}}>
                                            <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'17%'}} className="btn btn-primary btn-xl" onClick={() => this.onAddClick(pharmacist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Add</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div hidden={!this.state.showAddWorkTime}>
                                <form >
                                    <div  className="control-group" >
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link btn-xl" type="button">
                                                <i className="icofont-rounded-left mr-1"></i>
                                                Back
                                            </button>                   
                                        </div>
                                        <table style={{width:'100%'},{marginLeft:'17%'}}>
                                            <tr>
                                                <td>
                                                    <label >Date from:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "15em"}} minDate={new Date()} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Date to:</label>
                                                </td>
                                                <td>
                                                    <DatePicker  className="form-control" style={{width: "15em"}}  minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Time from:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time from" className="form-control" style={{width: "12.8em"}} type="number" min="1" max="23" onChange={this.handleTimeFromChange} value={this.state.timeFrom} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Time to:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time to" className="form-control" style={{width: "12.8em"}} type="number" min="2" max="24" onChange={this.handleTimeToChange} value={this.state.timeTo} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add pharmacist</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
                </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default AddPharmacistToPharmacy;