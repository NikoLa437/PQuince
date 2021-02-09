import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";
import { Redirect } from "react-router-dom";

class EditStorageAmountForDrug extends Component {
    state = {
        newAmount:1, 
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",   
        unauthorizedRedirect: false,

    }

    handleNewAmountChange= (event) => {
        this.setState({newAmount:event.target.value});
    }

    handleAdd = () => {
        let editStorageAmountDTO = {
            drugInstanceId: this.props.drug, 
            amount:this.state.newAmount
        };

        if(this.state.newAmount<0){
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "Amount of storage must be 0 or positive number"
            });
        }else{
            Axios
            .put(BASE_URL + "/api/drug/edit-storage-amount-for-drug", editStorageAmountDTO, {
                validateStatus: () => true,
                headers: { Authorization: getAuthHeader() },
            }).then((res) =>{
                if (res.status === 204) {
                    this.setState({
                        hiddenSuccessAlert: false,
                        hiddenFailAlert:true,
                        successHeader: "Success",
                        successMessage: "You successfully add new amount for drug.",
                        newAmount:1, 
                    })
                    this.props.updateDrugs();
                }else if(res.status===400){
                    this.setState({ 
                        hiddenSuccessAlert: true,
                        hiddenFailAlert: false, 
                        failHeader: "Unsuccess", 
                        failMessage: "Currently not possible to change amount of the drug"
                    });
                }else if(res.status===401){
                    this.setState({ 
                        unauthorizedRedirect:true
                    });
                }
                console.log(res.data);
            }).catch((err) => {
            });
        }
    }

    handleClickOnClose = () => {
        this.setState({ 
            hiddenSuccessAlert: true,
            hiddenFailAlert: true, 
        });
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
                                                    <label>New amount:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="New price" className="form-control" style={{width: "12.8em"}} type="number" min="1" onChange={this.handleNewAmountChange} value={this.state.newAmount} />
                                                </td>
                                            </tr>

                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Modify storage amount</Button>
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
 
export default EditStorageAmountForDrug;