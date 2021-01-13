import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';

class PasswordChangeModal extends Component {
    state = {

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
                            <div className="control-group">
                                <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                    <label>Old password:</label>
                                    <input placeholder="Old password" class="form-control" type="password" onChange={this.handleNameChange} value={this.state.name} />
                                </div>
                                <div className="text-danger" style={{display:"none"}}>
                                    Old password must be entered.
                                </div>
                                <div className="text-danger" style={{display:"none"}}>
                                    Old password is not valid.
                                </div>
                            </div>
                            <div className="control-group">
                                <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                    <label>New password:</label>
                                    <input placeholder="New password" class="form-control" type="password" onChange={this.handleNameChange} value={this.state.name} />
                                </div>
                                <div className="text-danger" style={{display:"none"}}>
                                    New password must be entered.
                                </div>
                            </div>
                            <div className="control-group">
                                <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                    <label>Type again new password:</label>
                                    <input placeholder="Type again new password" class="form-control" type="password" onChange={this.handleNameChange} value={this.state.name} />
                                </div>
                                <div className="text-danger" style={{display:"none"}}>
                                    Passwords are not the same.
                                </div>
                            </div>
                    </Modal.Body>
                    <Modal.Footer>
                    <Button onClick={this.props.onCloseModal}>Close</Button>
                    </Modal.Footer>
                </Modal>
             );
    }
}
 
export default PasswordChangeModal;