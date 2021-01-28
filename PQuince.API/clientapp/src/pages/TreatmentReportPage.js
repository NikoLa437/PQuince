import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import CapsuleLogo from "../static/capsuleLogo.png";
import { BASE_URL } from "../constants.js";
import Axios from "axios";
import DrugsModal from "../components/DrugsModal";
import TherapyDrugModal from "../components/TherapyDrugModal";


class TreatmentReportPage extends Component {
	state = {
        anamnesis: "",
        diagnosis: "",
        drugs: [],
        openDrugsModal: false,
        openDrugDetailsModal: false,
        drug: {},
        name: "",
        manufacturer: "",
        quantity: ""
	};

    handleAnamnesisChange = (event) => {
		this.setState({ anamnesis: event.target.value });
	};

	handleDiagnosisChange = (event) => {
		this.setState({ diagnosis: event.target.value });
    };

    handleDrugsModalClose = () => {
		this.setState({ openDrugsModal: false });
    };

    handleDrugsModalOpen = () => {
		this.setState({ openDrugsModal: true });
    };
    
    handleDrugDetailsModalClose = () => {
		this.setState({ openDrugDetailsModal: false });
    };

    handleDrugDetailsModalOpen = () => {
		this.setState({ openDrugDetailsModal: true });
	};
    
    handleSubmit = (event) => {
		console.log(event);
    };

    handleDrugDetails = (drug) => {
        console.log(drug); 
        this.setState({
            drug: drug,
            name: drug.EntityDTO.name,
            quantity: drug.EntityDTO.drug,
            manufacturer: drug.EntityDTO.manufacturer.EntityDTO.name
        });
        this.handleDrugsModalClose(); 
        this.handleDrugDetailsModalOpen();
    };

    handleAddDrug = (drugAmount, selectedDate) => {
        console.log(this.state.drug); 
        this.handleDrugDetailsModalClose();
        this.state.drugs.push(this.state.drug);   
    };

	render() {
		return (
            <React.Fragment>
			<div hidden={this.props.hidden}>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Treatment report</h5>

                    <form>
                        <div class="form-group">
                            <label>Anamnesis</label>
                            <textarea class="form-control" value={this.state.anamnesis} onChange={this.handleAnamnesisChange} rows="5"></textarea>		
                        </div>
                        <div class="form-group">
                            <label>Diagnosis</label>
                            <textarea class="form-control" value={this.state.diagnosis} onChange={this.handleDiagnosisChange} rows="3"></textarea>
                        </div>
                    </form>

					<table
						className="table table-hover"
						style={{ width: "50%", marginTop: "3rem" }}
					>
						<tbody>
							{this.state.drugs.map((drug) => (
								<tr
									id={drug.Id}
									key={drug.Id}
									onClick={() => this.props.onDrugSelect(drug)}
									style={{ cursor: "pointer" }}
								>
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
                    <div className="form-group text-left">
									<button
										style={{ background: "#1977cc" }}
										onClick={this.handleDrugsModalOpen}
										className="btn btn-primary btn-1x"
										type="button"
									>
										Add therapy drug
									</button>
				    </div>
                    <div className="form-group text-center">
									<button
										style={{ background: "#1977cc", marginTop: "50px" }}
										onClick={this.handleSubmit}
										className="btn btn-primary btn-lg"
										type="button"
									>
										Submit treatment report
									</button>
				    </div>
				</div>
			</div>
            <DrugsModal            
            show={this.state.openDrugsModal}
            onCloseModal={this.handleDrugsModalClose}
            header="Pick therapy drug"
            subheader=""
            handleDrugDetails={this.handleDrugDetails}
            />
            <TherapyDrugModal 
            name={this.state.name}
            quantity={this.state.quantity}
            manufacturer={this.state.manufacturer}
            drug={this.state.drug}           
            show={this.state.openDrugDetailsModal}
            onCloseModal={this.handleDrugDetailsModalClose}
            header="Add therapy drug"
            subheader=""
            handleAddDrug={this.handleAddDrug}
            />
            </React.Fragment>
		);
	}
}

export default TreatmentReportPage;
