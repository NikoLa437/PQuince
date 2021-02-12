import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import OrderLogo from "../static/order.png";


class ViewDrugRequestsModal extends Component {
    state = {

    }

    componentDidMount() {
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
        return ( 
            <Modal
                show = {this.props.show}
                size = "lg"
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'45%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <div className="container">      
                    <table hidden={this.state.showAddWorkTime} className="table" style={{ width: "100%", marginTop: "3rem" }}>
                        <tbody>
                            {this.props.drugRequests.map((drug) => (
                                <tr id={drug.Id} key={drug.Id}>
                                    <td width="130em">
                                        <img
                                            className="img-fluid"
                                            src={OrderLogo}
                                            width="70em"
                                        />
                                    </td>
                                    <td>
                                        <div>
                                            <b>Drug name: </b> {drug.drugInstanceName}
                                        </div>
                                        <div>
                                            <b>Drug manufacturer: </b> {drug.drugInstanceManufacturer}
                                        </div>
                                        <div>
                                            <b>Doctor: </b> {drug.staffName}
                                        </div>
                                        <div>
                                            <b>Date: </b> {new Date(drug.date).toDateString()}
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                 </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default ViewDrugRequestsModal;