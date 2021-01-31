import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import DermatologistLogo from '../static/dermatologistLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";

class AddDermatologistToPharmacy extends Component {
    state = {
        dermatologists:[],
        showAddWorkTime:false,
        dermatologistIdToAdd:''
    }

    componentDidMount() {
		Axios.get(BASE_URL + "/api/users/dermatologist-for-emplooye-in-pharmacy/cafeddee-56cb-11eb-ae93-0242ac130202")
			.then((res) => {
				this.setState({ dermatologists: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
    }

    onAddClick = (id) =>{
        this.setState({
            showAddWorkTime: true,
            dermatologistIdToAdd:id,
        });
    }

    handleAdd = () => {
        alert("IMPLEMENT");
    }

    handleBack = (event) =>{
        this.setState({showAddWorkTime: false});
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
                <div className="container">      
                    <table hidden={this.state.showAddWorkTime} className="table" style={{ width: "100%", marginTop: "3rem" }}>
                        <tbody>
                            {this.state.dermatologists.map((dermatologist) => (
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
                                    <td >
                                        <div style={{marginLeft:'25%'}}>
                                            <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'17%'}} className="btn btn-primary btn-xl" onClick={() => this.onAddClick(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Add</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
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
                                    <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add dermatologist</Button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onCloseModal}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default AddDermatologistToPharmacy;