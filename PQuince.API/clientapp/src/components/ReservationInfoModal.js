import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import CapsuleLogo from "../static/capsuleLogo.png";

class ReservationModalInfo extends Component {
  render() {
    return (
      <Modal
        show={this.props.show}
        dialogClassName="modal-80w-100h"
        aria-labelledby="contained-modal-title-vcenter"
        centered
        onHide={this.props.onCloseModal}
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            {this.props.header}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form className="ml-3">
            <div className="control-group">
              <div
                className="form-group controls"
                style={{ color: "#6c757d", opacity: 1 }}
              >
                <div className="form-row" width="130em">
                  <div className="form-col">
                    <img className="img-fluid" src={CapsuleLogo} width="90em" />
                  </div>
                  <div className="form-col">
                    <div>
                      <b>Name: </b> {this.props.drugName}
                    </div>
                    <div>
                      <b>Manufacturer: </b> {this.props.drugManufacturer}
                    </div>
                    <div>
                      <b>Quantity: </b> {this.props.drugQuantity} <b>mg</b>
                    </div>
                    <div>
                      <b>Price by peace: </b> {this.props.drugPrice} <b>din</b>
                    </div>
                  </div>
                </div>
              </div>
              <br />
              <div className="form-row mt-3">
                <div>
                  <b>Pharmacy name: </b> {this.props.pharmacyName}
                </div>
                <div>
                  <b>Pharmacy address: </b> {this.props.pharmacyAddress}
                </div>
              </div>
              <br />
              <div className="form-row">
                <div className="form-col">
                  <div>
                    <b>Reserved until: </b>{" "}
                    {new Date(this.props.endDate).toLocaleDateString("en-US", {
                      day: "2-digit",
                      month: "2-digit",
                      year: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                    })}{" "}
                  </div>
                </div>
              </div>
              <div className="form-row">
                <div className="form-col">
                  <div>
                    <b>Drug amount: </b> {this.props.drugAmount}
                  </div>
                </div>
              </div>
              <div className="form-row mt-3">
                <div className="form-col">
                  <div>
                    <b>Total price: </b>{" "}
                    {this.props.drugAmount * this.props.drugPrice} <b>din</b>
                  </div>
                </div>
              </div>
              <div className="form-row mt-3">
                <div className="form-col">
                  <div>
                    <b>Reservation status: </b> {this.props.reservationStatus}
                  </div>
                </div>
              </div>
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={this.props.onCloseModal}>Close</Button>
        </Modal.Footer>
      </Modal>
    );
  }
}

export default ReservationModalInfo;
