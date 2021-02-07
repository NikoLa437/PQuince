import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";
import getAuthHeader from "../GetHeader";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";
import { Redirect } from "react-router-dom";

class AddActionAndPromotionsModal extends Component {
    state = {
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        percentOfDiscount:0,
        discountFor:'DRUGDISCOUNT',
        text:'',
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",    
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

    handlePercentOfDiscountChange= (event) => {
        this.setState({percentOfDiscount:event.target.value});
        
    }

    handleAdd = () => {
        let actionAndPromotionsDTO = {
            forPharmacy : this.props.forPharmacy,
            dateFrom: this.state.selectedStartDate, 
            dateTo:this.state.selectedEndDate,
            percentOfDiscount:this.state.percentOfDiscount,
            actionAndPromotionType: this.state.discountFor, 
        };

        Axios
        .post(BASE_URL + "/api/pharmacy/action-and-promotions", actionAndPromotionsDTO, {
            headers: { Authorization: getAuthHeader() },
        }).then((res) =>{
            this.setState({
                hiddenSuccessAlert: false,
                hiddenFailAlert:true,
                successHeader: "Success",
                successMessage: "You successfully add action and promotion for pharmacy.",
            })

        }).catch((err) => {
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "It is not possible to add action"});
        });
    }

    handleClickOnClose = () => {
        this.setState({
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            percentOfDiscount:'',
            discountFor:'',
            hiddenSuccessAlert:true,
            hiddenFailAlert:true,
        });
        this.props.onCloseModal();
    }

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    handleDiscountForChange = (event) => {
		this.setState({ discountFor: event.target.value });
	};

    render() { 
        if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

        return ( 
            <Modal
                show = {this.props.show}
                size = "lg"
                dialogClassName="modal-120w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'33%'}} id="contained-modal-title-vcenter">
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
                                        <table style={{width:'100%'},{marginLeft:'27%'}}>
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
                                                    <label>Percent of discount:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time from" className="form-control" style={{width: "12.8em"}} type="number" min="1" max="99" onChange={this.handlePercentOfDiscountChange} value={this.state.percentOfDiscount} />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Discount for</label>
                                                </td>
                                                <td>
                                                <select onChange={this.handleDiscountForChange} value={this.state.discountFor} style={{ width: "9em" }} className="form-control mr-3">
								                    <option key="1" value="DRUGDISCOUNT">Drugs</option>
                                                    <option key="2" value="EXAMINATIONDISCOUNT">Examinations</option>
								                    <option key="3" value="CONSULTATIONDISCOUNT">Consultations</option>
								                    <option key="4" value="DISCOUNTFORALL">For all</option>
							                    </select>
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add action and promotion</Button>
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
 
export default AddActionAndPromotionsModal;