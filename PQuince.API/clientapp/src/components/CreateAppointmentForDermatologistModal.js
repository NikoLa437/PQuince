import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";
import getAuthHeader from "../GetHeader";


class CreateAppointmentForDermatologistModal extends Component {
    state = {
        selectedDate:new Date(),
        dermatologist:'',
        duration:'',
        periods:[],
        selectedPeriod: null,
        price:1

    }

    handleDateChange = (date) => {
        this.setState({selectedDate:date});
    }

    handleAddAppointment = () => {
        
        if(this.state.dermatologist!='' && this.state.duration !='' && this.state.selectedPeriod!=''){
            let appointmentDTO = {
                staff : this.state.dermatologist,
                pharmacy: this.props.pharmacyId, 
                patient: null, 
                startDateTime: this.state.selectedPeriod.startDate, 
                endDateTime:this.state.selectedPeriod.endDate,
                price: this.state.price, 
                appointmentStatus: 0
            };

        Axios
        .post(BASE_URL + "/api/appointment/create-appointment-for-dermatologist", appointmentDTO, {
            headers: { Authorization: getAuthHeader() },
        }).then((res) =>{
            console.log(res.data);
                
                alert('Uspesno kreiran appointment')
                this.handleCloseAppointment();
        }).catch((err) => {
            alert('Nije moguce kreirati termin u naznacenom roku');
        });        
        }
        else{
            alert("MORATE UNETI NEOPHODNE PODATKE")
        }
    }

    handleCloseAppointment= () => {
        this.setState({
            selectedDate:new Date(),
            duration:'',
            dermatologist:'',
            periods:[],
            selectedPeriod:'',
            price:1
        });
        this.props.onCloseModal();
    }

    handleDermatologistChange = (event) => {
        this.setState({ dermatologist: event.target.value });
    };

    handlePeriodsChange = (event) => {
        this.setState({ selectedPeriod: this.state.periods[event.target.value] });
    }

    handleDateChange = (date) => {
        this.setState({
            selectedDate:date,
        });
    }
    
    
    handlePriceChange = (event) =>{

        if(event.target.value<1){
            this.setState({
                price:1,
            });
        }else{
            this.setState({
                price:event.target.value,
            });
        }
    }

    convertDate = str => {
        str = str.toString();
        let parts = str.split(" ");
        let months = {
          Jan: "01",
          Feb: "02",
          Mar: "03",
          Apr: "04",
          May: "05",
          Jun: "06",
          Jul: "07",
          Aug: "08",
          Sep: "09",
          Oct: "10",
          Nov: "11",
          Dec: "12"
        };
        return months[parts[1]] + "/" + parts[2] + "/" + parts[3];
      };

    handleCloseModal = () => {
        this.setState({
            price:1,
        });    
    }

    handleSelectDurationChange = (event) => {
        this.setState({
            duration:event.target.value,
        });
    
        if(event.target.value>=10 && event.target.value<=60){
            Axios.get(BASE_URL + "/api/appointment/getFreePeriod", {
                params:{
                    dermatologistId: this.state.dermatologist,
                    pharmacyId:this.props.pharmacyId,
                    date: this.convertDate(this.state.selectedDate),
                    duration: event.target.value
                },
                headers: { Authorization: getAuthHeader() }
            }).then((res) => {
                    this.setState({ periods: res.data });
                    console.log(res.data);
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    }

    render() { 
        return ( 
            <Modal
                onCloseModal={this.handleCloseModal}
                show = {this.props.show}
                size = "md"
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered>
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'28%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>

                </Modal.Header>
                <Modal.Body>
                    <div >
                        <form >
                            <div  className="control-group" >
                                        <table style={{width:'100%'},{marginLeft:'7%'}}>
                                            <tr>
                                                <td>
                                                    <label >Select dermatologist:</label>
                                                </td>
                                                <td>
                                                    <select onChange={this.handleDermatologistChange} className="form-control" ><option key="1" value=""> </option>{this.props.dermatologists.map((dermatologist) => <option key={dermatologist.Id} value={dermatologist.Id}>Dr {dermatologist.EntityDTO.name} {dermatologist.EntityDTO.surname}</option>)}</select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Select date:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "18em"}} minDate={new Date()} onChange={date => this.handleDateChange(date)} selected={this.state.selectedDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Select duration:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Duration" className="form-control" style={{width: "15.8em"}} type="number" min="10" max="60" onChange={this.handleSelectDurationChange} value={this.state.duration} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Select period:</label>
                                                </td>
                                                <td>
                                                    <select onChange={this.handlePeriodsChange} className="form-control" ><option key="1" value=""> </option>{this.state.periods.map((period,index) => <option key={index} value={index}>{new Date(period.startDate).toLocaleTimeString()} - {new Date(period.endDate).toLocaleTimeString()}</option>)}</select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Price:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Price" className="form-control" style={{width: "15.8em"}} type="number" min="1" onChange={this.handlePriceChange} value={this.state.price} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAddAppointment()} >Add appointment</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleCloseAppointment()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default CreateAppointmentForDermatologistModal;