import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import CapsuleLogo from "../static/capsuleLogo.png";

class DrugsModal extends Component {
  state = {
    drugs: [],
    id: ""
  };

  componentDidMount() {
    Axios.get(BASE_URL + "/api/drug")
        .then((res) => {
            this.setState({ drugs: res.data });
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        });
    }

  render() {
    return (
      <Modal
        show={this.props.show}
        size="lg"
        dialogClassName="modal-80w-80h"
        aria-labelledby="contained-modal-title-vcenter"
        centered
        scrollable
        onHide={this.props.onCloseModal}
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            {this.props.header}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h4>{this.props.subheader}</h4>
          <table className="table table-hover" style={{ width: "100%" }}>
            <tbody>
              {this.state.drugs.map((drug) => (
                <tr id={drug.Id} key={drug.Id} style={{ cursor: "pointer"}} onClick={() => this.props.handleDrugDetails(drug)}>
                    <td width="130em">
						<img className="img-fluid" src={CapsuleLogo} width="70em" />
				    </td>
                    <td>
						<div>
							<b>Name:</b> {drug.EntityDTO.drugInstanceName}
						</div>
						<div>
							<b>Manufacturer:</b>{" "}
							{drug.EntityDTO.manufacturer.EntityDTO.name}
						</div>
						<div>
							<b>Quantity:</b> {drug.EntityDTO.quantity} <b>mg</b>
						</div>
					</td>					
                </tr>
              ))}
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

export default DrugsModal;
