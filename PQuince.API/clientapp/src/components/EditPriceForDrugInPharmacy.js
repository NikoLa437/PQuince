import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";
import DatePicker from "react-datepicker";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";
import { Redirect } from "react-router-dom";


class EditPriceForDrugInPharmacy extends Component {
    state = {
        newPrice:1, 
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",   
        unauthorizedRedirect: false,

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

    handleNewPriceChange= (event) => {
        this.setState({newPrice:event.target.value});
    }

    handleAdd = () => {
        let editPriceDTO = {
            drugInstanceId: this.props.drug, 
            price:this.state.newPrice,
            startDate:this.state.selectedStartDate,
            endDate:this.state.selectedEndDate,
        };

        if(this.validateDto(editPriceDTO)){
            Axios
            .put(BASE_URL + "/api/drug/edit-price-for-drug", editPriceDTO, {
                validateStatus: () => true,
                headers: { Authorization: getAuthHeader() },
            }).then((res) =>{
                if (res.status === 204) {
                    this.setState({
                        hiddenSuccessAlert: false,
                        hiddenFailAlert:true,
                        successHeader: "Success",
                        successMessage: "You successfully add new price for drug.",
                        newPrice:1, 
                        selectedStartDate:new Date(),
                        selectedEndDate:new Date(),
                    })
                    this.props.updateDrugs();
                    this.props.onCloseModalSuccess();
                }else if(res.status===400){
                    this.setState({ 
                        hiddenSuccessAlert: true,
                        hiddenFailAlert: false, 
                        failHeader: "Unsuccess", 
                        failMessage: "Currently not possible to change the price of the drug"
                    });
                }else if(res.status===401){
                    this.setState({ 
                        unauthorizedRedirect:true
                    });
                }
                console.log(res.data);
            }).catch((err) => {
                this.props.OnCloseModalUnsuccess();
            });
        }else{
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "End date of drug price must be after start date"});
        }
    }

    validateDto = (editPriceDTO) =>{
        if(editPriceDTO.price<1){
            return false
        }

        if(editPriceDTO.startDate>=editPriceDTO.endDate){
            return false
        }

        return true
    }

    handleClickOnClose = () => {
        this.props.onCloseModal();
    }

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    render() { 
        if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

        return ( 
            
            <Modal
                show = {this.props.show}
                size = {this.state.modalSize}
                dialogClassName="modal-120w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'37%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <HeadingSuccessAlert
						hidden={this.state.hiddenSuccessAlert}
						header={this.state.successHeader}
						message={this.state.successMessage}
						handleCloseAlert={this.handleCloseAlertSuccess}
					/>
                    <HeadingAlert
                            hidden={this.state.hiddenFailAlert}
                            header={this.state.failHeader}
                            message={this.state.failMessage}
                            handleCloseAlert={this.handleCloseAlertFail}
                    />
                        <form >
                            <div  className="control-group" >
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
                                                    <label>New price:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="New price" className="form-control" style={{width: "12.8em"}} type="number" min="1" onChange={this.handleNewPriceChange} value={this.state.newPrice} />
                                                </td>
                                            </tr>

                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Modify price</Button>
                                        </div>
                                    </div>
                                </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default EditPriceForDrugInPharmacy;