import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';

class AllergensModal extends Component {
    state = {
        allAllergens:[],
    }

    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/allergens").then((res) =>{
            this.setState({allAllergens : res.data});
        }).catch((err) => {console.log(err);});
    }

    doesUserContains = (allergenId) => {
        console.log(this.props.userAllergens);
        for(let allergen of this.props.userAllergens){
            if(allergen.Id === allergenId)
                return true;
        }
        return false;
    }

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size="lg"
                dialogClassName="modal-80w-80h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                onHide={this.props.onCloseModal}
                >
                <Modal.Header closeButton >
                    <Modal.Title id="contained-modal-title-vcenter">
                    {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h4>{this.props.subheader}</h4>
                    <table className="table" style={{width:"100%"}}>
                        <thead>
                            <td className="col-md-3">Allergen name</td>
                            <td className="col-md-1"></td>
                        </thead>
                        <tbody>
                            {this.state.allAllergens.map(allergen => 
                                <tr id={allergen.Id} key={allergen.Id}>
                                    <td>
                                        {allergen.EntityDTO.name}
                                    </td>
                                    <td>
                                        {this.doesUserContains(allergen.Id) === true ? <Button onClick={() => this.props.onAllergenRemove(allergen)} variant="danger" style={{width:"100%"}}>Remove</Button> : <Button onClick={() => this.props.onAllergenAdd(allergen)} style={{width:"100%"}} variant="primary">Add</Button>}
                                    </td>
                                </tr>)}

                        </tbody>
                    </table>
                </Modal.Body>
                <Modal.Footer>
                <Button onClick={this.props.onCloseModal}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default AllergensModal;