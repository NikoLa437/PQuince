import React, { Component } from "react";
import Header from "../../components/Header";
import TopBar from "../../components/TopBar";
import CapsuleLogo from "../../static/capsuleLogo.png";
import { BASE_URL } from "../../constants.js";
import Axios from "axios";
import DrugsModal from "../../components/DrugsModal";
import AlternativeDrugsModal from "../../components/AlternativeDrugsModal";
import TherapyDrugModal from "../../components/TherapyDrugModal";
import { withRouter } from "react-router";
import getAuthHeader from "../../GetHeader";
import { Redirect } from "react-router-dom";
import ModalDialog from "../../components/ModalDialog";

class TreatmentReportPage extends Component {
	state = {
        anamnesis: "",
        diagnosis: "",
        drugs: [],
        openDrugsModal: false,
        openDrugDetailsModal: false,
		drug: {},
		drugId: "",
        name: "",
        manufacturer: "",
		quantity: "",
		openModalSuccess: false,
		appointment: {},
		id: "",
		patientId: "",
		redirect: false,
		redirectUrl: '',
		drugsPatientIsNotAllergicTo: [],
		alternativeDrugs: [],
		openAlternativeDrugsModal: false,
	};

	handleModalSuccessClose = () => {
		this.setState({ openModalSuccess: false, redirectUrl: "/patient-profile/" + this.state.patientId, redirect: true });
	};

	fetchDrugsPatientIsNotAllergicTo = () => {
		Axios.get(BASE_URL + "/api/drug/not-allergic/" + this.state.patientId, {validateStatus: () => true, headers: { Authorization: getAuthHeader() }})
        .then((res) => {
            this.setState({ drugsPatientIsNotAllergicTo: res.data });
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        });
	}

	componentDidMount() {
		const id = this.props.match.params.id;
		this.setState({
			id:id
		});

		Axios.get(BASE_URL + "/api/appointment/" + id, 
			{validateStatus: () => true, headers: { Authorization: getAuthHeader() }}
		)
			.then((res) => {
				if (res.status === 401) {
					this.setState({
						redirect: true,
						redirectUrl: "/unauthorized"
					});
				} else {
					console.log(res.data)
					this.setState({ appointment: res.data, patientId: res.data.EntityDTO.patient.Id});
					this.fetchDrugsPatientIsNotAllergicTo();
				}
			})
			.catch((err) => {
				console.log(err);
			})
	}

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

	handleAlternativeDrugsModalClose = () => {
		this.setState({ openAlternativeDrugsModal: false });
    };

    handleAlternativeDrugsModalOpen = () => {

		this.setState({ openAlternativeDrugsModal: true });
    };
    
    handleSubmit = (event) => {
		let therapy = "drug : number of days" + "\n";
		this.state.drugs.forEach((value, index) => {
			therapy += value.drug.EntityDTO.manufacturer.EntityDTO.name + " " + value.drug.EntityDTO.name + " : " + value.amount + "\n";
		});
		console.log(therapy);

		Axios.post(BASE_URL + "/api/treatment-report",
			{anamnesis: this.state.anamnesis, diagnosis: this.state.diagnosis, therapy: therapy, appointmentId: this.state.id},
			{headers: { Authorization: getAuthHeader() }}
		)
			.then((res) => {
				this.setState({ openModalSuccess: true});
			})
			.catch((err) => {
				console.log(err);
			})

		Axios.put(BASE_URL + "/api/appointment/finish",
			{id: this.state.id},
			{headers: { Authorization: getAuthHeader() }}
		)
		.catch((err) => {
			console.log(err);
		})
		
		this.state.drugs.forEach((value, index) => {
			Axios.post(BASE_URL + "/api/drug/staff/reserve",
				{ patientId: this.state.patientId, drugInstanceId: value.drug.Id, amount: value.amount},
				{headers: { Authorization: getAuthHeader() }}
			)
			.catch((err) => {
				console.log(err);
			})

		});
    };

    handleDrugDetails = (drug) => {
        console.log(drug); 
        this.setState({
			drugId: drug.Id,
            drug: drug,
            name: drug.EntityDTO.drugInstanceName,
            quantity: drug.EntityDTO.quantity,
            manufacturer: drug.EntityDTO.manufacturer.EntityDTO.name
		});
		this.handleAlternativeDrugsModalClose(); 
        this.handleDrugsModalClose(); 
        this.handleDrugDetailsModalOpen();
    };

    handleAddDrug = (drugAmount) => {
        console.log(this.state.drug); 
		this.handleDrugDetailsModalClose();
		Axios.get(BASE_URL + "/api/drug/available/" + this.state.drug.Id + "/" + drugAmount, 
			{validateStatus: () => true, headers: { Authorization: getAuthHeader() }}
		)
			.then((res) => {
				if (res.status === 200) {
					this.state.drugs.push({drug: this.state.drug, amount: drugAmount});
					this.state.drugsPatientIsNotAllergicTo = this.state.drugsPatientIsNotAllergicTo.filter(drug => this.state.drug.Id != drug.Id);
					this.setState(this.state);		
				} else {
					console.log(res.data);
					this.setState({alternativeDrugs: []});
					this.state.drug.EntityDTO.replacingDrugs.forEach( x => this.addAlternativeDrug(x));
					this.setState(this.state);
					console.log(this.state.alternativeDrugs);
					this.handleAlternativeDrugsModalOpen();
				}
			})
			.catch((err) => {
				console.log(err);
			})
	};

	addAlternativeDrug = (x) => {
		let alternative = this.state.drugsPatientIsNotAllergicTo.filter(drug => x.Id == drug.Id);
		alternative.forEach(x => this.state.alternativeDrugs.push(x));
	};
	
	handleRemoveTherapyDrug = (e, drug, index) => {
		console.log(index)
		this.state.drugs.splice(index, 1);
		this.state.drugsPatientIsNotAllergicTo.push(drug)
		this.setState(this.state);
	};

	render() {

		if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;

		return (
            <React.Fragment>
			<div hidden={this.props.hidden}>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
					<h4 className=" text-center mb-0 mt-2 text-uppercase">Treatment report</h4>

                    <form>
                        <div class="form-group">
                            <h5>Anamnesis</h5>
                            <textarea class="form-control" value={this.state.anamnesis} onChange={this.handleAnamnesisChange} rows="5"></textarea>		
                        </div>
                        <div class="form-group">
                            <h5>Diagnosis</h5>
                            <textarea class="form-control" value={this.state.diagnosis} onChange={this.handleDiagnosisChange} rows="3"></textarea>
                        </div>
                    </form>
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
					<form style={{minHeight: "50px"}} className="border border-secondary">
					<table
						className="table table-hover m-0"
					>
						<tbody>
							{this.state.drugs.map((drug, index) => (
								<tr
									id={drug.Id}
									key={drug.Id}
									style={{ cursor: "pointer" }}
								>
									<td width="130em">
										<img className="img-fluid" src={CapsuleLogo} width="70em" />
									</td>
									<td>
										<div>
											<b>Name:</b> {drug.drug.EntityDTO.drugInstanceName}
										</div>
										<div>
											<b>Manufacturer:</b>{" "}
											{drug.drug.EntityDTO.manufacturer.EntityDTO.name}
										</div>
										<div>
											<b>Quantity:</b> {drug.drug.EntityDTO.quantity} <b>mg</b>
										</div>
										<div>
											<b>Number of days:</b> {drug.amount}
										</div>
									</td>
									<td>
									<button
										onClick={(e) => this.handleRemoveTherapyDrug(e, drug.drug, index)}
										className="btn btn-danger btn-1x"
										type="button"
										style={{float: "right"}}
									>
										Remove therapy drug
									</button>
									</td>
								</tr>
							))}
						</tbody>
					</table>
					</form>
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
            header="Pick drug you want to prescribe as therapy"
            subheader="You can only choose between drugs patient is not allergic to"
			handleDrugDetails={this.handleDrugDetails}
			drugsPatientIsNotAllergicTo={this.state.drugsPatientIsNotAllergicTo}
            />
			<AlternativeDrugsModal            
            show={this.state.openAlternativeDrugsModal}
            onCloseModal={this.handleAlternativeDrugsModalClose}
            header="No selected drug in stock, here are some alternatives"
            subheader="You can choose between alternative drugs"
			handleDrugDetails={this.handleDrugDetails}
			alternativeDrugs={this.state.alternativeDrugs}
            />
            <TherapyDrugModal 
            name={this.state.name}
            quantity={this.state.quantity}
            manufacturer={this.state.manufacturer}
            drug={this.state.drug}         
            show={this.state.openDrugDetailsModal}
            onCloseModal={this.handleDrugDetailsModalClose}
            header="Add drug to patients therapy"
            subheader=""
            handleAddDrug={this.handleAddDrug}
            />
			<ModalDialog
					show={this.state.openModalSuccess}
					onCloseModal={this.handleModalSuccessClose}
					header="Successfully submitted treatment report for patient"
					text="You can schedule another appointment for this patient."
				/>
            </React.Fragment>
		);
	}
}

export default withRouter(TreatmentReportPage);
