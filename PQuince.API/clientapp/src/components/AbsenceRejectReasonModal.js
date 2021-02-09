import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";
import DatePicker from "react-datepicker";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";
import { Redirect } from "react-router-dom";


class AbsenceRejectReasonModal extends Component {
    state = {
        rejectReason:'', 
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",   
        unauthorizedRedirect: false,
    }

    
    handleReasonChange= (event) => {
        this.setState({rejectReason:event.target.value});
    }

    handleReject = () => {
        let rejectDTO = {
            id: this.props.absenceId, 
            reason: this.state.rejectReason,
        };

        if(this.validateDto(rejectDTO)){
            Axios
            .put(BASE_URL + "/api/absence/reject-absence", rejectDTO, {
                validateStatus: () => true,
                headers: { Authorization: getAuthHeader() },
            }).then((res) =>{
                if (res.status === 204) {
                    this.props.onCloseWithRejectSuccess();
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
            });
        }else{
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "Must entry reason"
            });
        }
    }

    validateDto = (rejectDTO) =>{
        if(rejectDTO.reason===''){
            return false
        }

        return true
    }

    handleClickOnClose = () => {
        this.setState({
            hiddenSuccessAlert:true,
            hiddenFailAlert:true,
            rejectReason:'',
        })
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
                                                    <label>Reason:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Entry reason" className="form-control" style={{width: "12.8em"}} type="text"  onChange={this.handleReasonChange} value={this.state.rejectReason} />
                                                </td>
                                            </tr>

                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleReject()} >Reject</Button>
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
 
export default AbsenceRejectReasonModal;