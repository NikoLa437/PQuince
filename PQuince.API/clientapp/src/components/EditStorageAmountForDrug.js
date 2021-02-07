import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";

class EditStorageAmountForDrug extends Component {
    state = {
        newAmount:1, 
    }

    handleNewAmountChange= (event) => {
        this.setState({newAmount:event.target.value});
    }

    handleAdd = () => {
        let editStorageAmountDTO = {
            drugInstanceId: this.props.drug, 
            amount:this.state.newAmount
        };

        if(this.state.newAmount<1){
            alert("Nije moguce staviti negativnu cenu")
        }else{
            Axios
            .put(BASE_URL + "/api/drug/edit-storage-amount-for-drug", editStorageAmountDTO, {
                headers: { Authorization: getAuthHeader() },
            }).then((res) =>{
                this.props.updateDrugs();
                this.props.onCloseModalSuccess();
                console.log(res.data);
            }).catch((err) => {
                this.props.OnCloseModalUnsuccess();
            });
        }
    }

    handleClickOnClose = () => {
        this.props.onCloseModal();
    }

    render() { 
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