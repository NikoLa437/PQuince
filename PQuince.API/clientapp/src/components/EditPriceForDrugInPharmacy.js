import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import getAuthHeader from "../GetHeader";

class EditPriceForDrugInPharmacy extends Component {
    state = {
        newPrice:1, 
    }

    handleNewPriceChange= (event) => {
        this.setState({newPrice:event.target.value});
    }

    handleAdd = () => {
        let editPriceDTO = {
            drugInstanceId: this.props.drug, 
            price:this.state.newPrice
        };

        if(this.state.price<1){
            alert("Nije moguce staviti negativnu cenu")
        }else{
            Axios
            .put(BASE_URL + "/api/drug/edit-price-for-drug", editPriceDTO, {
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