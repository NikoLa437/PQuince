import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import CapsuleLogo from '../static/capsuleLogo.png';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";
import getAuthHeader from "../GetHeader";

class AddDrugToPharmacy extends Component {
    state = {
        showAddStorageAndPrice:false,
        drugIdToAdd:'',
        modalSize:'lg',    
        amount:1,
        price:1,    
        pharmacyId:''
    }

    componentDidMount() {
    }

    onAddClick = (id) =>{
        this.setState({
            showAddStorageAndPrice: true,
            drugIdToAdd:id,
            modalSize:'md'
        });
    }

    handleAdd = () => {
        let addDrugToPharmacyDTO = {
            drugId: this.state.drugIdToAdd, 
            amount:this.state.amount,
            price: this.state.price, 
        };

        if(this.isValidData(addDrugToPharmacyDTO)){
            Axios
            .post(BASE_URL + "/api/drug/add-drug-to-pharmacy", addDrugToPharmacyDTO, {
            headers: { Authorization: getAuthHeader() },}).then((res) =>{
                this.props.updateDrugs();
                this.setState({showAddStorageAndPrice: false, modalSize:'lg'});
                alert("Uspesno dodat dermatolog u apoteku")
                this.handleClickOnClose();
            }).catch((err) => {
                alert('Nije moguce dodati dermatologa');
            });
        }else{
            // aktivirati error
            alert("ERROR");
        }
    
    }

    isValidData = (addDrugToPharmacyDTO) =>{
        if(addDrugToPharmacyDTO.drugId===''){
            return false
        }

        if(addDrugToPharmacyDTO.dateTo===new Date()){
            return false
        }

        if(addDrugToPharmacyDTO.amount<1){
            return false
        }

        if(addDrugToPharmacyDTO.price<1){
            return false
        }

        return true
    }

    handleBack = (event) =>{
        this.setState({showAddStorageAndPrice: false,modalSize:'lg'});
    }

    handleDateChange = (date) => {
        this.setState({
            selectedDate:date,
        });

    }

    handlePriceChange = (event) => {
        this.setState({price:event.target.value});
    }

    handleAmountChange = (event) => {
        this.setState({amount:event.target.value});
    }

    handleClickOnClose = () => {
        this.setState({
            showAddStorageAndPrice: false, 
            modalSize:'lg',
            selectedDate:new Date(),
            price:1,
            amount:2,
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
                    <table hidden={this.state.showAddStorageAndPrice} className="table" style={{ width: "100%", marginTop: "3rem" }}>
                        <tbody>
                            {this.props.drugs.map((drug) => (
                                <tr id={drug.Id} key={drug.Id}>
                                    <td width="130em">
                                        <img
                                            className="img-fluid mt-2"
                                            src={CapsuleLogo}
                                            width="70em"
                                        />
                                    </td>
                                    <td>
                                            <div><b>Drug:</b> {drug.EntityDTO.name}</div>
                                            <div><b>Name:</b> {drug.EntityDTO.drugInstanceName}</div>
                                            <div><b>Manufacturer:</b> {drug.EntityDTO.manufacturer.EntityDTO.name}</div>
                                            <div><b>Quantity:</b> {drug.EntityDTO.quantity} <b>mg</b></div>
                                    </td>
                                    <td >
                                        <div style={{marginLeft:'25%'}}>
                                            <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-primary btn-xl mt-4" onClick={() => this.onAddClick(drug.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Add</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div hidden={!this.state.showAddStorageAndPrice}>
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
                                                    <label>Amount:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Amount" className="form-control" style={{width: "12.8em"}} type="number" min="1" onChange={this.handleAmountChange} value={this.state.amount} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Price:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Price" className="form-control" style={{width: "12.8em"}} type="number" min="1" onChange={this.handlePriceChange} value={this.state.price} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add drug</Button>
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
 
export default AddDrugToPharmacy;